package SporttiFoorumi.mallit;

import SporttiFoorumi.Tietokanta.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Kayttaja {

    private int id;
    private String tunnus;
    private String salasana;
    private String rooli;

    public Kayttaja() {

    }

    public Kayttaja(int id, String tunnus, String salasana, String rooli) {
        this.id = id;
        this.tunnus = tunnus;
        this.salasana = salasana;
        this.rooli = rooli;
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

    public static Kayttaja etsiKayttajaTunnuksilla(String kayttaja, String salasana) throws SQLException {
        String sql = "SELECT tunnus,kayttajatunnus, salasana, rooli from Jasen where kayttajatunnus = ? AND salasana = ?";
        Connection yhteys = Yhteys.getYhteys();
        if(yhteys==null) return null;
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
        String sql = "SELECT tunnus, kayttajatunnus, salasana from jasen";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Kayttaja> kayttajat = new ArrayList();
        while (tulokset.next()) {
            Kayttaja k = new Kayttaja();
            k.setId(tulokset.getInt("tunnus"));
            k.setTunnus(tulokset.getString("kayttajatunnus"));
            k.setSalasana(tulokset.getString("salasana"));

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
}
