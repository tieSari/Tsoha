package SporttiFoorumi.mallit;

import SporttiFoorumi.Tietokanta.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Ryhma {

    private int tunnus;
    private String nimi;
    private String kuvaus;
    private List<Viesti> viestit;

    public Ryhma() {

    }

    public Ryhma(int tunnus, String nimi, String kuvaus, ArrayList<Viesti> viestit) {
        this.tunnus = tunnus;
        this.nimi = nimi;
        this.kuvaus = kuvaus;
        this.viestit = viestit;
    }

    public String getKuvaus() {
        return kuvaus;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public int getTunnus() {
        return tunnus;
    }

    public void setTunnus(int tunnus) {
        this.tunnus = tunnus;
    }

    public List<Viesti> getViestit() {
        return viestit;
    }

    public void setViestit(List<Viesti> viestit) {
        this.viestit = viestit;
    }

    public static List<Ryhma> getRyhmatJaViestit(int kayttajaId) throws SQLException {

        ArrayList<Ryhma> ryhmat = getRyhmat();
        for (Ryhma ryhma : ryhmat) {
            ryhma.setViestit(Viesti.etsiRyhmanViestit(ryhma.getTunnus(), kayttajaId));
        }
        return ryhmat;

    }

    public static List<Ryhma> filterRyhmatJaViestit(List<Ryhma> ryhmat, String filter) throws SQLException {

        for (Ryhma ryhma : ryhmat) {
            for (Viesti viesti : ryhma.getViestit()) {
                if (!viesti.getKirjoittajaNimi().contains(filter)
                        && !viesti.getOtsikko().contains(filter)
                        && !viesti.getKirjoituspvm().toString().contains(filter)) {
                    ryhma.getViestit().remove(viesti);
                }
                if (ryhma.getViestit().isEmpty()) {
                    ryhmat.remove(ryhma);
                }
            }
        }
        return ryhmat;

    }

    public static ArrayList<Ryhma> getRyhmat() throws SQLException {
        String sql = "SELECT tunnus, nimi, kuvaus from ryhma";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Ryhma> ryhmat = new ArrayList();
        while (tulokset.next()) {
            Ryhma k = new Ryhma();
            k.setTunnus(tulokset.getInt("tunnus"));
            k.setNimi(tulokset.getString("nimi"));
            k.setKuvaus(tulokset.getString("kuvaus"));

            ryhmat.add(k);
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

        return ryhmat;
    }
}
