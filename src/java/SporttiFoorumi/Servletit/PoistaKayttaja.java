/*
 * Luokka toimii jäsenen tietojen muokkausnäkymän controllerina
 * kutsuu Kayttaja-malliluokan poistometodia. Kantaan päivityksen jälkeen
 * näyttää jäsenvalikon ja tiedon poiston onnistumisesta.
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
public class PoistaKayttaja extends GeneralServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.processRequest(request, response);
        String id = request.getParameter("id");
        Kayttaja kirjautunut = Kirjautunut(request);
        if (kirjautunut == null || !"yllapitaja".equals(kirjautunut.getRooli())) {
            asetaVirhe("Kirjaudu palveluun ylläpitäjänä, jos haluat poistaa jäsenen tiedot.", request);
            naytaJSP("kirjautuminen.jsp", request, response);
            return;
        }
        try {
            if (id != null) {
                Kayttaja.poistaKayttaja(Integer.parseInt(id));
                asetaInfo("Käyttäjän tiedot poistettu onnistuneesti.", request);
            } else {
                asetaVirhe("Käyttäjän poistaminen ei onnistunut.", request);
            }
            response.sendRedirect("HaeKayttajat");
        } catch (SQLException e) {
            Logger.getLogger(PoistaKayttaja.class.getName()).log(Level.SEVERE, null, e);
            asetaVirhe("Käyttäjän poistaminen ei onnistunut.", request);
            naytaJSP("jasenenMuokkaus.jsp", request, response);
        }
    }
}
