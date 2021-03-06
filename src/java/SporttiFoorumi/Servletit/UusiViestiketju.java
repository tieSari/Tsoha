/*
 * Luokkaa kutsutaan etusivun "aloita uusi keskustelu" -linkin kautta.
 * näyttää uuden ketjun luontinäkymän.
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
public class UusiViestiketju extends GeneralServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.processRequest(request, response);
        try {
            request.setAttribute("ryhmat", Ryhma.getRyhmat());
            naytaJSP("uusiViestiKetju.jsp", request,response);

        } catch (SQLException e) {
            Logger.getLogger(UusiViestiketju.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
