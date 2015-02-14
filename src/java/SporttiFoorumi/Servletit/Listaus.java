/*
 * Luokka toimii etusivun listausnäkymän controllerina,
 * kutsuu Ryhma- malliluokan tietokantakyselymetodia, joka palauttaa asiaryhmät
 * ja niihin liittyvät viestit.
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
public class Listaus extends GeneralServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Kayttaja kirjautunut = Kirjautunut(request);
            if (kirjautunut == null) {
                asetaVirhe("Kirjaudu palveluun, jos haluat lukea viestejä.", request);
                naytaJSP("kirjautuminen.jsp", request, response);
                return;
            }
            request.setAttribute("ryhmat", Ryhma.getRyhmatJaViestit(kirjautunut.getId()));
            naytaJSP("index.jsp", request, response);

        } catch (SQLException e) {
            Logger.getLogger(Listaus.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
