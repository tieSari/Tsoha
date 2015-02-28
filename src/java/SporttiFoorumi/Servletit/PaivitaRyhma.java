/*
 * Luokka toimii ryhmän tietojen muokkausnäkymän controllerina
 * kutsuu Ryhma-malliluokan päivitysmetodia. Kantaan päivityksen jälkeen
 * näyttää ryhman tietosivun, jolta alkuperäinen kutsu tuli.
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author sariraut
 */
public class PaivitaRyhma extends GeneralServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.processRequest(request, response);

        String id = request.getParameter("tunnus");
        Kayttaja kirjautunut = Kirjautunut(request);
        HttpSession session = request.getSession();
        if (kirjautunut == null || !"yllapitaja".equals(kirjautunut.getRooli())) {
            asetaVirhe("Kirjaudu palveluun ylläpitäjänä, jos haluat päivittää ryhman tiedot.", request);
            naytaJSP("kirjautuminen.jsp", request, response);
            return;
        }
        try {
            Ryhma ryhma = new Ryhma();
            ryhma.setTunnus(Integer.parseInt(id));
            ryhma.setNimi(request.getParameter("nimi"));
            ryhma.setKuvaus(request.getParameter("kuvaus"));

            String validoi = ryhma.onkoKelvollinen(ryhma);
            if (validoi != null) {
                asetaVirhe(validoi, request);
                request.setAttribute("ryhma", ryhma);

                naytaJSP("ryhmanTiedot.jsp", request, response);
                return;
            }

            String[] kayttajat = request.getParameterValues("kayttajat");
            int[] tunnukset = null;
            if (kayttajat != null) {
                tunnukset = new int[kayttajat.length];
                for (int i = 0; i < kayttajat.length; i++) {
                    tunnukset[i] = Integer.parseInt(kayttajat[i]);
                }
            }
            Ryhma.paivitaRyhma(ryhma, tunnukset);

            session.setAttribute("ryhma", ryhma.getTunnus());
            asetaInfo("Ryhman tiedot päivitetty onnistuneesti.", request);
            response.sendRedirect("HaeRyhmanTiedot");

        } catch (SQLException e) {
            Logger.getLogger(PaivitaRyhma.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
