/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SporttiFoorumi.Servletit;

import SporttiFoorumi.mallit.Kayttaja;
import SporttiFoorumi.mallit.Ryhma;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sariraut
 */
public class HaeKayttajanTiedot extends GeneralServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("jasen"));
            request.setAttribute("kayttaja", Kayttaja.getKayttaja(id));
            request.setAttribute("ryhmat", Ryhma.getRyhmat());
            naytaJSP("jasenenTiedot.jsp", request,response);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}