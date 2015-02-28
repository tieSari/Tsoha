/*
 * Luokka toimii jäsenen tietojen lisäysnäkymän controllerina
 * kutsuu Kayttaja-malliluokan lisäysmetodia. Kantaan päivityksen jälkeen
 * näyttää jäsenvalikon ja viestin lisäyksen onnistumisesta.
 */
package SporttiFoorumi.Servletit;

import SporttiFoorumi.mallit.Kayttaja;
import SporttiFoorumi.mallit.Ryhma;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sariraut
 */
public class LisaaRyhma extends GeneralServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.processRequest(request, response);
        Kayttaja kirjautunut = Kirjautunut(request);
        if (kirjautunut == null || !"yllapitaja".equals(kirjautunut.getRooli())) {
            asetaVirhe("Kirjaudu palveluun ylläpitäjänä, jos haluat lisätä ryhmän.", request);
            naytaJSP("kirjautuminen.jsp", request, response);
            return;
        }
        try {
            Ryhma ryhma = new Ryhma();
            ryhma.setNimi(request.getParameter("nimi"));
            ryhma.setKuvaus(request.getParameter("kuvaus"));

            String validoi = ryhma.onkoKelvollinen(ryhma);
            if (validoi != null) {
                asetaVirhe(validoi, request);
                request.setAttribute("ryhma", ryhma);
                request.setAttribute("kayttajat", Kayttaja.getKayttajat());

                naytaJSP("ryhmanLisays.jsp", request, response);
                return;
            }

            if (ryhma.lisaaRyhma() != 0) {
                String[] kayttajat = request.getParameterValues("kayttajat");
                if (kayttajat != null) {
                    int[] tunnukset = new int[kayttajat.length];
                    for (int i = 0; i < kayttajat.length; i++) {
                        tunnukset[i] = Integer.parseInt(kayttajat[i]);
                    }
                    ryhma.lisaaRyhmanJasenet(tunnukset);
                }

                request.setAttribute("ryhma", ryhma);
                asetaInfo("Ryhmä " + ryhma.getNimi() + " lisätty onnistuneesti.", request);
                response.sendRedirect("HaeRyhmat");
            } else {
                asetaVirhe("Ryhmän lisäys ei onnistunut.", request);
                response.sendRedirect("HaeRyhmat");
            }

        } catch (SQLException e) {
            Logger.getLogger(LisaaRyhma.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
