/*
 * Luokka viestiketjun näyttävän näkymän controllerina, jonka kautta voi 
 * lisätä ketjuun uuden viestin. Uutta viestiketjua luotaessa luokkaa kutsutaan myös.
 * 
 */
package SporttiFoorumi.Servletit;

import SporttiFoorumi.mallit.Kayttaja;
import SporttiFoorumi.mallit.Viesti;
import java.io.IOException;
import java.sql.SQLException;
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
public class TallennaViesti extends GeneralServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.processRequest(request, response);
        request.getSession().removeAttribute("teksti");
        try {
            Kayttaja kirjautunut = Kirjautunut(request);
            if (kirjautunut == null) {
                asetaVirhe("Kirjaudu palveluun, jos haluat lisätä viestin.", request);
                naytaJSP("kirjautuminen.jsp", request, response);
                return;
            }
            Viesti viesti = new Viesti();
            viesti.setOtsikko(request.getParameter("otsikko"));
            viesti.setTeksti(request.getParameter("teksti"));
            viesti.setKirjoittaja(kirjautunut.getId());
            viesti.setRyhma(Integer.parseInt(request.getParameter("ryhma")));
            viesti.setPaaviesti(Integer.parseInt(request.getParameter("paaviesti")));

            String validoi = null;
            if (viesti.getPaaviesti() == 0) {
                validoi = viesti.onkoKelvollinen(viesti);
            } else {
                if (viesti.getTeksti().length() > 250) {
                    validoi = "Kentän teksti max. pituus 250 merkkiä";
                }
            }
            if (validoi != null) {
                asetaVirhe(validoi, request);
                request.setAttribute("viesti", viesti);

                if (viesti.getPaaviesti() == 0) {
                    naytaJSP("uusiViestiKetju.jsp", request, response);
                    return;
                } else {
                    request.getSession().setAttribute("tunnus", viesti.getPaaviesti());
                    request.getSession().setAttribute("teksti", viesti.getTeksti());
                    response.sendRedirect("Keskustelu");
                    return;
                }
            }

            viesti.lisaaViestiKantaan();
            Viesti.lisaaViestiLuettuhin(viesti.getTunnus(), kirjautunut.getId());
            viesti.setLuettu(true);

            //jos viesti ei ole ketjun aloitusviesti, siirrytään keskusteluun, johon viesti
            //on lisätty. Muussa tapauksessa siirrytään etusivun listaukseen.
            if (viesti.getPaaviesti() != 0) {
                HttpSession session = request.getSession();
                session.setAttribute("tunnus", viesti.getPaaviesti());
                response.sendRedirect("Keskustelu");
            } else {
                response.sendRedirect("Listaus");
            }

        } catch (SQLException e) {
            Logger.getLogger(TallennaViesti.class.getName()).log(Level.SEVERE, null, e);
        } catch (NamingException e) {
            Logger.getLogger(TallennaViesti.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
