/*
 * Luokka toimii ryhmän tietojen muokkausnäkymän controllerina
 * kutsuu Ryhma-malliluokan poistometodia. Kantaan päivityksen jälkeen
 * näyttää ryhmävalikon ja tiedon poiston onnistumisesta.
 */
package SporttiFoorumi.Servletit;

import SporttiFoorumi.mallit.Kayttaja;
import SporttiFoorumi.mallit.Ryhma;
import SporttiFoorumi.mallit.Viesti;
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
public class PoistaRyhma extends GeneralServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.processRequest(request, response);
        String id = request.getParameter("tunnus");
        Kayttaja kirjautunut = Kirjautunut(request);
        if (kirjautunut == null || !"yllapitaja".equals(kirjautunut.getRooli())) {
            asetaVirhe("Kirjaudu palveluun ylläpitäjänä, jos haluat poistaa ryhmän tiedot.", request);
            naytaJSP("kirjautuminen.jsp", request, response);
            return;
        }
        try {
            if (id != null) {
                Ryhma.poistaRyhmanJasenet(Integer.parseInt(id));
                Ryhma.poistaRyhmanViestit(Integer.parseInt(id));
                Ryhma.poistaRyhma(Integer.parseInt(id));
                asetaInfo("Ryhmän tiedot poistettu onnistuneesti.", request);
            } else {
                asetaVirhe("Ryhmän poistaminen ei onnistunut.", request);
            }
            response.sendRedirect("HaeRyhmat");
        } catch (SQLException e) {
            Logger.getLogger(PoistaRyhma.class.getName()).log(Level.SEVERE, null, e);
            asetaVirhe("Ryhmän poistaminen ei onnistunut.", request);
            // response.sendRedirect("HaeKayttajat");
            naytaJSP("ryhmanMuokkaus.jsp", request, response);
        } catch (IOException e) {
            Logger.getLogger(PoistaRyhma.class.getName()).log(Level.SEVERE, null, e);
            asetaVirhe("Ryhmän poistaminen ei onnistunut.", request);
            // response.sendRedirect("HaeKayttajat");
            naytaJSP("ryhmanMuokkaus.jsp", request, response);
        }

    }
}
