/*
* Luokan avulla poistetaan viestiketju kannasta.
* Kutsutaan etusivun listaussivulta, jonne myös  palataan poiston jälkeen
* 
*/
package SporttiFoorumi.Servletit;

import SporttiFoorumi.mallit.Kayttaja;
import SporttiFoorumi.mallit.Viesti;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sariraut
 */
public class PoistaViestiketju extends GeneralServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.processRequest(request, response);
        try {
            Kayttaja kirjautunut = Kirjautunut(request);
            if (kirjautunut == null || !kirjautunut.getRooli().equals("yllapitaja")) {
                asetaVirhe("Kirjaudu palveluun ylläpitäjänä, jos haluat poistaa viestiketjun.", request);
                naytaJSP("kirjautuminen.jsp", request, response);
                return;
            }
            Viesti.poistaViestiketju(Integer.parseInt(request.getParameter("tunnus")));
            response.sendRedirect("Listaus");

        } catch (SQLException e) {
            Logger.getLogger(PoistaViestiketju.class.getName()).log(Level.SEVERE, null, e);
        } catch (NamingException ex) {
            Logger.getLogger(PoistaViestiketju.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
