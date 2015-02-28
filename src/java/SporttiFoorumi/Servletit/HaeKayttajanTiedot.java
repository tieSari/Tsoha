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
        super.processRequest(request, response);
        try {
            if (request.getParameter("lisays") == null || !request.getParameter("lisays").equals("true")) {
                int id;
                if (request.getSession().getAttribute("jasen") == null) {
                    if (request.getParameter("jasen") == null) {
                        response.sendRedirect("HaeKayttajat");
                        return;
                    }
                    id = Integer.parseInt(request.getParameter("jasen"));
                } else {
                    id = (Integer) request.getSession().getAttribute("jasen");
                }
                request.setAttribute("kayttaja", Kayttaja.getKayttaja(id));
                request.setAttribute("ryhmat", Ryhma.getRyhmat());
                naytaJSP("jasenenTiedot.jsp", request, response);
            } else {
                request.getSession().removeAttribute("jasen");
                request.setAttribute("ryhmat", Ryhma.getRyhmat());

                Kayttaja kayttaja = new Kayttaja();
                kayttaja.setEtunimi(request.getParameter("etunimi"));
                kayttaja.setEtunimi(request.getParameter("sukunimi"));
                kayttaja.setEtunimi(request.getParameter("rooli"));
                kayttaja.setEtunimi(request.getParameter("tunnus"));
                kayttaja.setEtunimi(request.getParameter("salasana"));
                request.setAttribute("kayttaja", kayttaja);

                naytaJSP("jasenenLisays.jsp", request, response);
            }

        } catch (SQLException e) {
            Logger.getLogger(HaeKayttajanTiedot.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            Logger.getLogger(HaeKayttajanTiedot.class.getName()).log(Level.SEVERE, null, e);
        } catch (NumberFormatException e) {
            Logger.getLogger(HaeKayttajanTiedot.class.getName()).log(Level.SEVERE, null, e);
        } catch (ServletException e) {
            Logger.getLogger(HaeKayttajanTiedot.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
