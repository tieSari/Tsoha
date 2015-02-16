package SporttiFoorumi.Tietokanta;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Yhteys {

    //Haetaan context-xml-tiedostosta tietokannan yhteystiedot
//HUOM! Tämä esimerkki ei toimi sellaisenaan ilman Tomcat-palvelinta!
    public static Connection getYhteys() {
        try {
            InitialContext cxt = new InitialContext();
            DataSource yhteysVarasto = (DataSource) cxt.lookup("java:/comp/env/jdbc/tietokanta");
            return yhteysVarasto.getConnection();

        } catch (SQLException e) {
            Logger.getLogger(Yhteys.class.getName()).log(Level.SEVERE, null, e);

        } catch (NamingException e) {
            Logger.getLogger(Yhteys.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;

    }
}
