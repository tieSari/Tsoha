package SporttiFoorumi.mallit;

import SporttiFoorumi.Tietokanta.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.naming.NamingException;

public class Viesti {

    private int tunnus;
    private String otsikko;
    private String teksti;
    private Date kirjoituspvm;
    private int kirjoittaja;
    private String kirjoittajaNimi;
    private int ryhma;
    private int paaviesti;

    public Viesti() {

    }

    public Viesti(int tunnus, String otsikko, String teksti, Date kirjoituspvm, int kirjoittaja, int ryhma, int paaviesti) {
        this.tunnus = tunnus;
        this.otsikko = otsikko;
        this.teksti = teksti;
        this.kirjoituspvm = kirjoituspvm;
        this.kirjoittaja = kirjoittaja;
        this.ryhma = ryhma;
        this.paaviesti = paaviesti;
    }

    public int getTunnus() {
        return tunnus;
    }

    public void setTunnus(int tunnus) {
        this.tunnus = tunnus;
    }

    public int getKirjoittaja() {
        return kirjoittaja;
    }

    public void setKirjoittaja(int kirjoittaja) {
        this.kirjoittaja = kirjoittaja;
    }

    public Date getKirjoituspvm() {
        return kirjoituspvm;
    }

    public void setKirjoituspvm(Date kirjoituspvm) {
        this.kirjoituspvm = kirjoituspvm;
    }

    public String getKirjoittajaNimi() {
        return kirjoittajaNimi;
    }

    public void setKirjoittajaNimi(String kirjoittajaNimi) {
        this.kirjoittajaNimi = kirjoittajaNimi;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

    public int getPaaviesti() {
        return paaviesti;
    }

    public void setPaaviesti(int paaviesti) {
        this.paaviesti = paaviesti;
    }

    public int getRyhma() {
        return ryhma;
    }

    public void setRyhma(int ryhma) {
        this.ryhma = ryhma;
    }

    public String getTeksti() {
        return teksti;
    }

    public void setTeksti(String teksti) {
        this.teksti = teksti;
    }

    public static List<Viesti> etsiRyhmanViestit(int ryhmaTunnus) throws SQLException {
        String sql = "SELECT v.tunnus, otsikko, teksti, kirjoituspvm, kirjoittaja,"
                + "etunimi,sukunimi, v.ryhma, paaviesti from Viesti v, jasen j"
                + " where v.ryhma = ? and v.kirjoittaja= j.tunnus";
        Connection yhteys = Yhteys.getYhteys();
        if (yhteys == null) {
            return null;
        }
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, ryhmaTunnus);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Viesti> viestit = new ArrayList();;

        while (tulokset.next()) {
            Viesti v = new Viesti();
            v.setKirjoittaja(tulokset.getInt("kirjoittaja"));
            v.setKirjoituspvm(tulokset.getDate("kirjoituspvm"));
            v.setOtsikko(tulokset.getString("otsikko"));
            v.setPaaviesti(tulokset.getInt("paaviesti"));
            v.setRyhma(tulokset.getInt("ryhma"));
            v.setTeksti(tulokset.getString("teksti"));
            v.setTunnus(tulokset.getInt("tunnus"));
            v.setKirjoittajaNimi(tulokset.getString("etunimi") + " " + tulokset.getString("sukunimi"));
            viestit.add(v);
        }
        try {
            tulokset.close();
        } catch (SQLException e) {
        }
        try {
            kysely.close();
        } catch (SQLException e) {
        }
        try {
            yhteys.close();
        } catch (SQLException e) {
        }
        return viestit;
    }

    public void lisaaViestiKantaan() throws NamingException, SQLException {
        String sql = "INSERT INTO Viesti(otsikko, teksti, paaviesti,ryhma, kirjoittaja) VALUES(?,?,?,?,?) RETURNING tunnus";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);

        kysely.setString(1, this.getOtsikko());
        kysely.setString(2, this.getTeksti());
        kysely.setInt(3, this.getPaaviesti());
        kysely.setInt(4, this.getRyhma());
        kysely.setInt(5, this.getKirjoittaja());

        ResultSet ids = kysely.executeQuery();
        ids.next();

        this.tunnus = ids.getInt(1);

        try {
            ids.close();
        } catch (SQLException e) {
        }
        try {
            kysely.close();
        } catch (SQLException e) {
        }
        try {
            yhteys.close();
        } catch (SQLException e) {
        }

    }

    public static Viesti etsiViesti(int tunnus) throws SQLException {
        String sql = "SELECT v.tunnus, otsikko, teksti, kirjoituspvm, kirjoittaja,"
                + "etunimi,sukunimi, v.ryhma, paaviesti from Viesti v, jasen j"
                + " where v.tunnus = ? and v.kirjoittaja= j.tunnus";
        Connection yhteys = Yhteys.getYhteys();
        if (yhteys == null) {
            return null;
        }
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, tunnus);
        ResultSet tulokset = kysely.executeQuery();

        Viesti v = null;

        while (tulokset.next()) {
            v = new Viesti();
            v.setKirjoittaja(tulokset.getInt("kirjoittaja"));
            v.setKirjoituspvm(tulokset.getDate("kirjoituspvm"));
            v.setOtsikko(tulokset.getString("otsikko"));
            v.setPaaviesti(tulokset.getInt("paaviesti"));
            v.setRyhma(tulokset.getInt("ryhma"));
            v.setTeksti(tulokset.getString("teksti"));
            v.setTunnus(tulokset.getInt("tunnus"));
            v.setKirjoittajaNimi(tulokset.getString("etunimi") + " " + tulokset.getString("sukunimi"));
        }
        try {
            tulokset.close();
        } catch (SQLException e) {
        }
        try {
            kysely.close();
        } catch (SQLException e) {
        }
        try {
            yhteys.close();
        } catch (SQLException e) {
        }
        return v;
    }

    public static void poistaViestiketju(int tunnus) throws NamingException, SQLException {
        if (Viesti.etsiViesti(tunnus) == null) {
            return;
        }
        String sql = "DELETE FROM viesti WHERE tunnus = ? or paaviesti = ?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);

        kysely.setInt(1, tunnus);
        kysely.setInt(2, tunnus);
        int tulos = kysely.executeUpdate();

        try {
            kysely.close();
        } catch (SQLException e) {
        }
        try {
            yhteys.close();
        } catch (SQLException e) {
        }

    }
}
