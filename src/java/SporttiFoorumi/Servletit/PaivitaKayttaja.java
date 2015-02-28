/*
 * Luokka toimii jäsenen tietojen muokkausnäkymän controllerina
 * kutsuu Kayttaja-malliluokan päivitysmetodia. Kantaan päivityksen jälkeen
 * näyttää jäsenen tietosivun, jolta alkuperäinen kutsu tuli.
 */
package SporttiFoorumi.Servletit;

import SporttiFoorumi.mallit.Kayttaja;
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
public class PaivitaKayttaja extends GeneralServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.processRequest(request, response);
        String id = request.getParameter("id");
        Kayttaja kirjautunut = Kirjautunut(request);
        if (kirjautunut == null || !"yllapitaja".equals(kirjautunut.getRooli())) {
            asetaVirhe("Kirjaudu palveluun ylläpitäjänä, jos haluat päivittää jäsenen tiedot.", request);
            naytaJSP("kirjautuminen.jsp", request, response);
            return;
        }
        try {
            Kayttaja kayttaja = new Kayttaja();
            kayttaja.setId(Integer.parseInt(request.getParameter("id")));
            kayttaja.setTunnus(request.getParameter("tunnus"));
            kayttaja.setEtunimi(request.getParameter("etunimi"));
            kayttaja.setSukunimi(request.getParameter("sukunimi"));
            kayttaja.setSalasana(request.getParameter("salasana"));
            kayttaja.setRooli(request.getParameter("rooli"));

            String validoi = kayttaja.onkoKelvollinen(kayttaja);
            if (validoi != null) {
                asetaVirhe(validoi, request);
                request.setAttribute("kayttaja", kayttaja);

                naytaJSP("jasenenTiedot.jsp", request, response);
                return;
            }

            String[] ryhmat = request.getParameterValues("ryhmat");
            int[] tunnukset = null;
            if (ryhmat != null) {
                tunnukset = new int[ryhmat.length];
                for (int i = 0; i < ryhmat.length; i++) {
                    tunnukset[i] = Integer.parseInt(ryhmat[i]);
                }
            }

            Kayttaja.paivitaKayttaja(kayttaja, tunnukset);
            request.setAttribute("kayttaja", kayttaja);
            request.getSession().setAttribute("jasen", kayttaja.getId());
            asetaInfo("Käyttäjän tiedot päivitetty onnistuneesti.", request);
            //naytaJSP("jasenenTiedot.jsp", request, response);
            response.sendRedirect("HaeKayttajanTiedot");

        } catch (SQLException e) {
            Logger.getLogger(PaivitaKayttaja.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
