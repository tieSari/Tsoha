/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class PoistaViestiketju extends GeneralServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Kayttaja kirjautunut = (Kayttaja) (session.getAttribute("kirjautunut"));
            if (kirjautunut == null || !kirjautunut.getRooli().equals("yllapitaja")) {
                asetaVirhe("Kirjaudu palveluun ylläpitäjänä, jos haluat lisätä viestin.", request);
                naytaJSP("kirjautuminen.jsp", request, response);
                return;
            }
            Viesti.poistaViestiketju(Integer.parseInt(request.getParameter("tunnus")));
            response.sendRedirect("Listaus");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (NamingException ex) {
            Logger.getLogger(PoistaViestiketju.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
