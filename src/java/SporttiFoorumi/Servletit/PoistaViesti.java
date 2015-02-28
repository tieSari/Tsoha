/*
 * Luokan avulla poistetaan viesti kannasta.
 * Kutsutaan viestiketjun sivulta keskustelu.jsp, jonne myös  palataan poiston jälkeen
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author sariraut
 */
public class PoistaViesti extends GeneralServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.processRequest(request, response);
        try {
            Kayttaja kirjautunut = Kirjautunut(request);
            if (kirjautunut == null || !kirjautunut.getRooli().equals("yllapitaja")) {
                asetaVirhe("Kirjaudu palveluun ylläpitäjänä, jos haluat poistaa viestin.", request);
                naytaJSP("kirjautuminen.jsp", request, response);
                return;
            }
            Viesti.poistaViesti(Integer.parseInt(request.getParameter("tunnus")));
            HttpSession session = request.getSession();
            //välitetään keskusteluketjun pääviestin tunnus sessiossa
            session.setAttribute("tunnus", Integer.parseInt(request.getParameter("paaviesti")));
            response.sendRedirect("Keskustelu");

        } catch (SQLException e) {
            Logger.getLogger(PoistaViestiketju.class.getName()).log(Level.SEVERE, null, e);
        } catch (NamingException ex) {
            Logger.getLogger(PoistaViestiketju.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
