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
public class LisaaKayttaja extends GeneralServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.processRequest(request, response);
        Kayttaja kirjautunut = Kirjautunut(request);
        if (kirjautunut == null || !"yllapitaja".equals(kirjautunut.getRooli())) {
            asetaVirhe("Kirjaudu palveluun ylläpitäjänä, jos haluat lisätä käyttäjän.", request);
            naytaJSP("kirjautuminen.jsp", request, response);
            return;
        }
        try {
            Kayttaja kayttaja = new Kayttaja();
            kayttaja.setTunnus(request.getParameter("tunnus"));
            kayttaja.setEtunimi(request.getParameter("etunimi"));
            kayttaja.setSukunimi(request.getParameter("sukunimi"));
            kayttaja.setSalasana(request.getParameter("salasana"));
            kayttaja.setRooli(request.getParameter("rooli"));

            String validoi = kayttaja.onkoKelvollinen(kayttaja);
            if (validoi != null) {
                asetaVirhe(validoi, request);
                request.setAttribute("kayttaja", kayttaja);

                naytaJSP("jasenenLisays.jsp", request, response);
                return;
            }

            if (kayttaja.lisaaKayttaja() != 0) {

                String[] ryhmat = request.getParameterValues("ryhmat");
                int[] tunnukset = null;
                if (ryhmat != null) {
                    tunnukset = new int[ryhmat.length];
                    for (int i = 0; i < ryhmat.length; i++) {
                        tunnukset[i] = Integer.parseInt(ryhmat[i]);
                    }
                }
                if (tunnukset != null) {
                    Ryhma.lisaaJasenenRyhmat(tunnukset, kayttaja.getId());
                }

                request.setAttribute("kayttaja", kayttaja);
                asetaInfo("Käyttäjä " + kayttaja.getEtunimi() + " lisätty onnistuneesti.", request);
                response.sendRedirect("HaeKayttajat");
                //naytaJSP("jasenenMuokkaus.jsp", request, response);
            } else {
                asetaVirhe("Käyttäjän lisäys ei onnistunut.", request);
                naytaJSP("jasenenLisays.jsp", request, response);
            }
            //response.sendRedirect("HaeKayttajanTiedot");

        } catch (SQLException e) {
            Logger.getLogger(LisaaKayttaja.class.getName()).log(Level.SEVERE, null, e);
        } catch (ServletException e) {
            Logger.getLogger(LisaaKayttaja.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            Logger.getLogger(LisaaKayttaja.class.getName()).log(Level.SEVERE, null, e);
        } catch (NumberFormatException e) {
            Logger.getLogger(LisaaKayttaja.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
