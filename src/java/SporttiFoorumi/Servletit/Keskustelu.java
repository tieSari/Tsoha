/*
 * Keskustelu-servettissä haetaan viestiketjun viestit 
 * Samalla ne merkitään luetuiksi eli lisätään rivi JasenenLukematViestit -tauluun
 * Servlettiä kutsutaan etusivun viestiketjun linkistä parametrina pääviestin tunnus.
 */
package SporttiFoorumi.Servletit;

import SporttiFoorumi.mallit.Kayttaja;
import SporttiFoorumi.mallit.Viesti;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sariraut
 */
public class Keskustelu extends GeneralServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.processRequest(request, response);
        try {
            Kayttaja kirjautunut = Kirjautunut(request);
            if (kirjautunut == null) {
                asetaVirhe("Kirjaudu palveluun, jos haluat lukea viestejä.", request);
                naytaJSP("kirjautuminen.jsp", request, response);
                return;
            }
            int tunnus;
            if (request.getParameter("tunnus") != null) {
                tunnus = Integer.parseInt(request.getParameter("tunnus"));
            } else {
                HttpSession session = request.getSession();
                tunnus = (Integer) (session.getAttribute("tunnus"));
            }
            List<Viesti> viestiketju = Viesti.etsiKetjunViestit(tunnus, kirjautunut.getId());

            if (viestiketju != null) {
                for (Viesti viesti : viestiketju) {
                    Viesti.lisaaViestiLuettuhin(viesti.getTunnus(), kirjautunut.getId());
                    viesti.setLuettu(true);
                }
                request.setAttribute("viestiketju", viestiketju);
            }

            naytaJSP("keskustelu.jsp", request, response);

        } catch (SQLException e) {
            Logger.getLogger(Keskustelu.class.getName()).log(Level.SEVERE, null, e);
        } catch (NamingException ex) {
            Logger.getLogger(Keskustelu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
