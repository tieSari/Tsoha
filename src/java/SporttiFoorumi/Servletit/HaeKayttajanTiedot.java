/*
 * Luokka toimii jäsenen tiedot esittävän näkymän controllerina
 * kutsuu Ryhma- ja Kayttaja-malliluokkien tietokantakyselymetodeita
 * 
 */
package SporttiFoorumi.Servletit;

import SporttiFoorumi.mallit.Kayttaja;
import SporttiFoorumi.mallit.Ryhma;
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
            Logger.getLogger(HaeKayttajanTiedot.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
