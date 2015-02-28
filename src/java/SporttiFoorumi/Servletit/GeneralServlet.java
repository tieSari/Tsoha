/*
 * muut servletit periytetään tästä luokasta
 * luokka sisältää yhteisiä apumetodeita
 * sekä toteuttaa HTTPServletiltä perityt metodit
 */
package SporttiFoorumi.Servletit;

import SporttiFoorumi.mallit.Kayttaja;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sariraut
 */
public class GeneralServlet extends HttpServlet {

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

        HttpSession session = request.getSession();
        if (session != null) {
            Object virheViesti = session.getAttribute("virheViesti");

            if (virheViesti != null) {
                session.removeAttribute("virheViesti");
                request.setAttribute("virheViesti", virheViesti);
            }
            Object infoViesti = session.getAttribute("infoViesti");

            if (infoViesti != null) {
                session.removeAttribute("infoViesti");
                request.setAttribute("infoViesti", infoViesti);
            }
        }
    }

    public void naytaJSP(String jspName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(jspName);
        dispatcher.forward(request, response);
    }

    public void asetaVirhe(String virhe, HttpServletRequest request) {
        request.setAttribute("virheViesti", virhe);
        request.getSession().setAttribute("virheViesti", virhe);
    }

    public void asetaInfo(String ilmoitus, HttpServletRequest request) {
        request.setAttribute("infoViesti", ilmoitus);
        request.getSession().setAttribute("infoViesti", ilmoitus);
    }

    public Kayttaja Kirjautunut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Kayttaja kirjautunut = (Kayttaja) session.getAttribute("kirjautunut");
        return (kirjautunut);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
