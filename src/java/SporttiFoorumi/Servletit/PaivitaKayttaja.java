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
public class PaivitaKayttaja extends GeneralServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
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
            naytaJSP("jasenenTiedot.jsp", request,response);
            //response.sendRedirect("HaeKayttajanTiedot");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
