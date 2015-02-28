/*
 * Luokka toimii ryhmän tiedot esittävän näkymän controllerina
 * kutsuu Ryhma- ja Kayttaja-malliluokkien tietokantakyselymetodeita
 * 
 */
package SporttiFoorumi.Servletit;

import SporttiFoorumi.mallit.Kayttaja;
import SporttiFoorumi.mallit.Ryhma;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sariraut
 */
public class HaeRyhmanTiedot extends GeneralServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.processRequest(request, response);
        try {
            List<Kayttaja> kayttajat = Kayttaja.getKayttajat();
            request.setAttribute("kayttajat", kayttajat);

            if (request.getParameter("lisays") == null || !request.getParameter("lisays").equals("true")) {
                int id;
                if (request.getSession().getAttribute("ryhma") == null) {
                    if (request.getParameter("ryhma") == null) {
                        response.sendRedirect("HaeRyhmat");
                        return;
                    }
                    id = Integer.parseInt(request.getParameter("ryhma"));
                } else {
                    id = (Integer) request.getSession().getAttribute("ryhma");
                }

                Ryhma ryhma = Ryhma.getRyhma(id);
                if (ryhma.getKayttajat() != null) {
                    for (Kayttaja kayttaja : kayttajat) {
                        if (ryhma.getKayttajat().contains(kayttaja)) {
                            kayttaja.setSelected(true);
                        }
                    }
                }

                request.setAttribute("ryhma", ryhma);
                request.getSession().removeAttribute("ryhma");
                naytaJSP("ryhmanTiedot.jsp", request, response);
            } else {
                request.getSession().removeAttribute("ryhma");
                naytaJSP("ryhmanLisays.jsp", request, response);
            }

        } catch (SQLException e) {
            Logger.getLogger(HaeRyhmanTiedot.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
