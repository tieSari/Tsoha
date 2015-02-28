/*
 * Luokka toimii kirjautumisnäkymän controllerina.
 * Tätä servlettiä kutsutaan, kun mennään sovelluksen osoitteeseen SporttiFoorumi.
 * 
 */
package SporttiFoorumi.Servletit;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sariraut
 */
public class NaytaLoginSivu extends GeneralServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.processRequest(request, response);
         naytaJSP("kirjautuminen.jsp", request, response);
    }
}
