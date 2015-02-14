package SporttiFoorumi.mallit;

import SporttiFoorumi.Tietokanta.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.*;
import javax.naming.NamingException;

public class Viesti {

    private int tunnus;
    private String otsikko;
    private String teksti;
    private Date kirjoituspvm;
    private Time kirjoitusaika;
    private int kirjoittaja;
    private String kirjoittajaNimi;
    private int ryhma;
    private int paaviesti;
    private boolean luettu;

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

    public Time getKirjoitusaika() {
        return kirjoitusaika;
    }

    public void setKirjoitusaika(Time kirjoitusaika) {
        this.kirjoitusaika = kirjoitusaika;
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

    public boolean getLuettu() {
        return luettu;
    }

    public void setLuettu(boolean luettu) {
        this.luettu = luettu;
    }

    public static ArrayList<Viesti> etsiRyhmanViestit(int ryhmaTunnus, int kayttajaId) throws SQLException {
        String sql = "SELECT v.tunnus, otsikko, teksti, kirjoituspvm, kirjoittaja,"
                + "etunimi,sukunimi, v.ryhma, paaviesti from Viesti v, jasen j"
                + " where v.ryhma = ? and v.kirjoittaja= j.tunnus "
                + "order by kirjoituspvm asc";
        Connection yhteys = Yhteys.getYhteys();
        if (yhteys == null) {
            return null;
        }
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, ryhmaTunnus);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Viesti> viestit = new ArrayList();

        while (tulokset.next()) {
            Viesti v = new Viesti();
            v.setKirjoittaja(tulokset.getInt("kirjoittaja"));
            v.setKirjoituspvm(tulokset.getDate("kirjoituspvm"));
            v.setKirjoitusaika(tulokset.getTime("kirjoituspvm"));
            v.setOtsikko(tulokset.getString("otsikko"));
            v.setPaaviesti(tulokset.getInt("paaviesti"));
            v.setRyhma(tulokset.getInt("ryhma"));
            v.setTeksti(tulokset.getString("teksti"));
            v.setTunnus(tulokset.getInt("tunnus"));
            v.setLuettu(onkoViestiLuettu(v.getTunnus(), kayttajaId));
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
        viestit = onkoKetjussaLukemattomiaViesteja(viestit);
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

    public static void lisaaViestiLuettuhin(int viestitunnus, int jasentunnus) throws NamingException, SQLException {
        if (!onkoViestiLuettu(viestitunnus, jasentunnus)) {
            String sql = "INSERT INTO JasenenLukematViestit(viestitunnus, jasentunnus) VALUES(?,?)";
            Connection yhteys = Yhteys.getYhteys();
            PreparedStatement kysely = yhteys.prepareStatement(sql);

            kysely.setInt(1, viestitunnus);
            kysely.setInt(2, jasentunnus);

            int value = kysely.executeUpdate();
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

    public static ArrayList<Viesti> etsiKetjunViestit(int tunnus, int kayttajaId) throws SQLException {
        String sql = "SELECT v.tunnus, otsikko, teksti, kirjoituspvm, kirjoittaja,"
                + "etunimi,sukunimi, v.ryhma, paaviesti from Viesti v, jasen j"
                + " where (v.tunnus = ? or v.paaviesti = ?) and v.kirjoittaja= j.tunnus"
                + " order by kirjoituspvm";
        Connection yhteys = Yhteys.getYhteys();
        if (yhteys == null) {
            return null;
        }
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, tunnus);
        kysely.setInt(2, tunnus);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Viesti> viestit = new ArrayList<Viesti>();

        while (tulokset.next()) {
            Viesti v = new Viesti();
            v.setKirjoittaja(tulokset.getInt("kirjoittaja"));
            v.setKirjoituspvm(tulokset.getDate("kirjoituspvm"));
            v.setKirjoitusaika(tulokset.getTime("kirjoituspvm"));
            v.setOtsikko(tulokset.getString("otsikko"));
            v.setPaaviesti(tulokset.getInt("paaviesti"));
            v.setRyhma(tulokset.getInt("ryhma"));
            v.setTeksti(tulokset.getString("teksti"));
            v.setTunnus(tulokset.getInt("tunnus"));
            v.setKirjoittajaNimi(tulokset.getString("etunimi") + " " + tulokset.getString("sukunimi"));
            v.setLuettu(onkoViestiLuettu(v.getTunnus(), kayttajaId));
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
//jos ketjussa on yksikin lukematon viesti, pääviesti laitetaan lukemattomaksi

    private static ArrayList<Viesti> onkoKetjussaLukemattomiaViesteja(ArrayList<Viesti> viestit) {
        for (Viesti viesti : viestit) {
            if (viesti.getPaaviesti() == 0) {
                for (Viesti viesti2 : viestit) {
                    if (viesti.getTunnus() == viesti2.getPaaviesti() && !viesti2.getLuettu()) {
                        viesti.setLuettu(false);
                    }
                }
            }
        }
        return viestit;
    }

    public static boolean onkoViestiLuettu(int viestitunnus, int jasentunnus) throws SQLException {
        String sql = "SELECT viestitunnus, jasentunnus"
                + " from JasenenLukematViestit"
                + " where viestitunnus = ? and jasentunnus = ?";
        Connection yhteys = Yhteys.getYhteys();
        if (yhteys == null) {
            return false;
        }
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, viestitunnus);
        kysely.setInt(2, jasentunnus);
        ResultSet tulokset = kysely.executeQuery();
        if(!tulokset.next()) return false;
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

        return true;
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

        if (tulokset.next()) {
            v = new Viesti();
            v.setKirjoittaja(tulokset.getInt("kirjoittaja"));
            v.setKirjoituspvm(tulokset.getDate("kirjoituspvm"));
            v.setKirjoitusaika(tulokset.getTime("kirjoituspvm"));
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
    public static void poistaViesti(int tunnus) throws NamingException, SQLException {
        if (Viesti.etsiViesti(tunnus) == null) {
            return;
        }
        String sql = "DELETE FROM viesti WHERE tunnus = ?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);

        kysely.setInt(1, tunnus);
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
