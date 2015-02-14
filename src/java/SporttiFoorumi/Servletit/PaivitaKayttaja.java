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
            //vielä lisättävä käyttäjän ryhmien päivitys
            Kayttaja.paivitaKayttaja(kayttaja);
            request.setAttribute("kayttaja", kayttaja);
            asetaInfo("Käyttäjän tiedot päivitetty onnistuneesti.", request);
            naytaJSP("jasenenTiedot.jsp", request, response);
            //response.sendRedirect("HaeKayttajanTiedot");

        } catch (SQLException e) {
            Logger.getLogger(PaivitaKayttaja.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
