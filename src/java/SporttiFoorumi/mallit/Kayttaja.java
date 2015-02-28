package SporttiFoorumi.mallit;

import SporttiFoorumi.Tietokanta.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Kayttaja implements Comparable<Kayttaja> {

    private int id;
    private String tunnus;
    private String salasana;
    private String rooli;
    private String etunimi;
    private String sukunimi;
    private boolean selected;

    public Kayttaja() {

    }

    public Kayttaja(int id, String tunnus, String salasana, String rooli, String etunimi, String sukunimi) {
        this.id = id;
        this.tunnus = tunnus;
        this.salasana = salasana;
        this.rooli = rooli;
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
    }

    public String getSalasana() {
        return salasana;
    }

    public void setRooli(String rooli) {
        this.rooli = rooli;
    }

    public String getRooli() {
        return rooli;
    }

    public String getTunnus() {
        return tunnus;
    }

    public void setSalasana(String salasana) {
        this.salasana = salasana;
    }

    public void setTunnus(String tunnus) {
        this.tunnus = tunnus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEtunimi() {
        return etunimi;
    }

    public void setEtunimi(String etunimi) {
        this.etunimi = etunimi;
    }

    public String getSukunimi() {
        return sukunimi;
    }

    public void setSukunimi(String sukunimi) {
        this.sukunimi = sukunimi;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String onkoKelvollinen(Kayttaja kayttaja) {
        if (kayttaja.getEtunimi().trim().equals("")) {
            return "Anna käyttäjän etunimi";
        }
        if (kayttaja.getSukunimi().trim().equals("")) {
            return "Anna käyttäjän sukunimi";
        }
        if (kayttaja.getTunnus().trim().equals("")) {
            return "Anna käyttäjän tunnus";
        }
        if (kayttaja.getSalasana().trim().equals("")) {
            return "Anna käyttäjän salasana";
        }
        if (kayttaja.getSalasana().trim().length() < 6) {
            return "Salasanan on oltava vähintään 6 merkkiä pitkä";
        }
        if (!kayttaja.rooli.equals("kayttaja") && !kayttaja.rooli.equals("yllapitaja")) {
            return "Anna käyttäjän rooli: kayttaja tai yllapitaja";
        }
        return null;
    }

    public static Kayttaja etsiKayttajaTunnuksilla(String kayttaja, String salasana) throws SQLException {
        String sql = "SELECT tunnus,kayttajatunnus, salasana, rooli, etunimi, sukunimi from Jasen where kayttajatunnus = ? AND salasana = ?";
        Connection yhteys = Yhteys.getYhteys();
        if (yhteys == null) {
            return null;
        }
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, kayttaja);
        kysely.setString(2, salasana);
        ResultSet rs = kysely.executeQuery();

        Kayttaja kirjautunut = null;

        if (rs.next()) {
            kirjautunut = new Kayttaja();
            kirjautunut.setId(rs.getInt("tunnus"));
            kirjautunut.setTunnus(rs.getString("kayttajatunnus"));
            kirjautunut.setSalasana(rs.getString("salasana"));
            kirjautunut.setRooli(rs.getString("rooli"));
            kirjautunut.setEtunimi(rs.getString("etunimi"));
            kirjautunut.setSukunimi(rs.getString("sukunimi"));
        }
        try {
            rs.close();
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
        return kirjautunut;
    }

    public static List<Kayttaja> getKayttajat() throws SQLException {
        String sql = "SELECT tunnus,kayttajatunnus, salasana, rooli, etunimi, sukunimi from jasen order by sukunimi";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Kayttaja> kayttajat = new ArrayList();
        while (tulokset.next()) {
            Kayttaja k = new Kayttaja();
            k.setId(tulokset.getInt("tunnus"));
            k.setTunnus(tulokset.getString("kayttajatunnus"));
            k.setSalasana(tulokset.getString("salasana"));
            k.setRooli(tulokset.getString("rooli"));
            k.setEtunimi(tulokset.getString("etunimi"));
            k.setSukunimi(tulokset.getString("sukunimi"));

            kayttajat.add(k);
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

        return kayttajat;
    }

    public static Kayttaja getKayttaja(int id) throws SQLException {
        String sql = "SELECT tunnus, kayttajatunnus, salasana, etunimi, sukunimi, rooli from jasen "
                + "where tunnus=?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        ResultSet tulokset = kysely.executeQuery();

        Kayttaja k = null;
        tulokset.next();

        k = new Kayttaja();
        k.setId(tulokset.getInt("tunnus"));
        k.setTunnus(tulokset.getString("kayttajatunnus"));
        k.setSalasana(tulokset.getString("salasana"));
        k.setRooli(tulokset.getString("rooli"));
        k.setEtunimi(tulokset.getString("etunimi"));
        k.setSukunimi(tulokset.getString("sukunimi"));

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

        return k;
    }

    public static void paivitaKayttaja(Kayttaja kayttaja, int[] ryhmat) throws SQLException {
        String sql = "UPDATE jasen SET etunimi = ?, sukunimi = ?, salasana = ? , rooli = ?"
                + " , kayttajatunnus = ? WHERE tunnus = ?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, kayttaja.etunimi);
        kysely.setString(2, kayttaja.sukunimi);
        kysely.setString(3, kayttaja.salasana);
        kysely.setString(4, kayttaja.rooli);
        kysely.setString(5, kayttaja.tunnus);
        kysely.setInt(6, kayttaja.id);

        int tulos = kysely.executeUpdate();

        if (tulos == 1 && ryhmat != null) {
            Ryhma.poistaJasenenRyhmat(kayttaja.getId());
            Ryhma.lisaaJasenenRyhmat(ryhmat, kayttaja.getId());
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

    public int lisaaKayttaja() throws SQLException {
        String sql = "INSERT into jasen(etunimi, sukunimi, salasana,rooli, kayttajatunnus) values(?,?,?,?,?) RETURNING tunnus ";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, this.getEtunimi());
        kysely.setString(2, this.getSukunimi());
        kysely.setString(3, this.getSalasana());
        kysely.setString(4, this.getRooli());
        kysely.setString(5, this.getTunnus());

        ResultSet ids = kysely.executeQuery();
        ids.next();

        this.setId(ids.getInt(1));

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
        return this.getId();
    }

    public static void poistaKayttaja(int tunnus) throws SQLException {
        Ryhma.poistaJasenenRyhmat(tunnus);
        String sql = "DELETE from jasen WHERE tunnus = ?";
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

    public static ArrayList<Kayttaja> etsiRyhmanKayttajat(int ryhmaTunnus) throws SQLException {
        String sql = "SELECT tunnus,etunimi,sukunimi from jasen j, ryhmanjasenet r"
                + " where r.ryhmatunnus = ? and j.tunnus=r.jasentunnus order by sukunimi asc";
        Connection yhteys = Yhteys.getYhteys();
        if (yhteys == null) {
            return null;
        }
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, ryhmaTunnus);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Kayttaja> kayttajat = new ArrayList();

        while (tulokset.next()) {
            Kayttaja k = new Kayttaja();
            k.setEtunimi(tulokset.getString("etunimi"));
            k.setSukunimi(tulokset.getString("sukunimi"));
            k.setId(tulokset.getInt("tunnus"));
            kayttajat.add(k);
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
        return kayttajat;
    }

    @Override
    public int compareTo(Kayttaja o) {
        return this.getId() - o.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if ((Kayttaja) obj != null) {
            return this.getId() == ((Kayttaja) obj).getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.id;
        return hash;
    }

}
