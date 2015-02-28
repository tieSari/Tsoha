/*
 * Luokka toimii ryhmän tietojen muokkausnäkymän controllerina
 * kutsuu Ryhma-malliluokan tietokantakyselymetodia
 * 
 */
package SporttiFoorumi.Servletit;

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
public class HaeRyhmat extends GeneralServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.processRequest(request, response);
        try {
            request.setAttribute("ryhmat", Ryhma.getRyhmat());
            naytaJSP("ryhmanMuokkaus.jsp", request,response);

        } catch (SQLException e) {
            Logger.getLogger(HaeRyhmat.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
