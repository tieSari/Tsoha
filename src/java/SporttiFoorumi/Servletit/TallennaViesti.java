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
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sariraut
 */
public class TallennaViesti extends GeneralServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Kayttaja kirjautunut = (Kayttaja) (session.getAttribute("kirjautunut"));
            if (kirjautunut == null) {
                asetaVirhe("Kirjaudu palveluun, jos haluat lisätä viestin.", request);
                naytaJSP("kirjautuminen.jsp", request, response);
                return;
            }
            Viesti viesti = new Viesti();
            viesti.setOtsikko(request.getParameter("otsikko"));
            viesti.setTeksti(request.getParameter("teksti"));
            viesti.setKirjoittaja(kirjautunut.getId());
            viesti.setRyhma(Integer.parseInt(request.getParameter("ryhma")));
            viesti.lisaaViestiKantaan();
            
            response.sendRedirect("Listaus");

        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        catch (NamingException e ) {
            System.out.println(e.getMessage());
        }
    }
}

