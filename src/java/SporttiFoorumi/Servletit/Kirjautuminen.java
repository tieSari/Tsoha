/*
 * Luokka vastaa kirjautumistoiminnoista.
 * Jos kirjautuminen onnistui, kutsuu etusivun näyttävää Listaus-servlettiä.
 * Kirjautumisen epäonnistuessa näyttää uudelleen kirjautumissivun ja virheviestin.
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author sariraut
 */
public class Kirjautuminen extends GeneralServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.processRequest(request, response);
        try {
            //tähän lisään myöhemmin salasanan hashayksen (salausmetodit
            //tiedostossa PasswordHash.java
            
            String salasana = request.getParameter("password");
            String kayttaja = request.getParameter("username");
            
            if (kayttaja == null && salasana == null) {
                naytaJSP("kirjautuminen.jsp", request, response);
                return;
            }
            if (kayttaja == null || kayttaja.equals("")) {
                asetaVirhe("Kirjautuminen epäonnistui! Et antanut käyttäjätunnusta.", request);
                naytaJSP("kirjautuminen.jsp", request, response);
                return;
            }
            request.setAttribute("kayttaja", kayttaja);
            
            if (salasana == null || salasana.equals("")) {
                asetaVirhe("Kirjautuminen epäonnistui! Et antanut salasanaa.", request);
                naytaJSP("kirjautuminen.jsp", request, response);
                return;
            }
            HttpSession session = request.getSession();
            Kayttaja jasen = Kayttaja.etsiKayttajaTunnuksilla(kayttaja, salasana);
            if (jasen != null) {
                session.setAttribute("kirjautunut", jasen);
                request.setAttribute("kirjautunut", jasen);
                response.sendRedirect("Listaus");
            } else {
                asetaVirhe("Kirjautuminen epäonnistui! Antamasi tunnus tai salasana on väärä.", request);
                naytaJSP("kirjautuminen.jsp", request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Kirjautuminen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
