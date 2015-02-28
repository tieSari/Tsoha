/*
 * Luokka toimii jäsenen tietojen muokkausnäkymän controllerina
 * kutsuu Kayttaja-malliluokan tietokantakyselymetodia
 * 
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
public class HaeKayttajat extends GeneralServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.processRequest(request, response);
        try {
            request.setAttribute("kayttajat", Kayttaja.getKayttajat());
            naytaJSP("jasenenMuokkaus.jsp", request,response);

        } catch (SQLException e) {
            Logger.getLogger(HaeKayttajat.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
