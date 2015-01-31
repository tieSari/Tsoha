package SporttiFoorumi.Tietokanta;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
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
        } catch (Exception e) {

        }
        return null;

    }
}
