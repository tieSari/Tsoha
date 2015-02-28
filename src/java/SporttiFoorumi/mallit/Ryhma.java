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
    private List<Kayttaja> kayttajat;

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

    public List<Kayttaja> getKayttajat() {
        return kayttajat;
    }

    public void setKayttajat(List<Kayttaja> kayttajat) {
        this.kayttajat = kayttajat;
    }

    public String onkoKelvollinen(Ryhma ryhma) {
        if (ryhma.getNimi().trim().equals("")) {
            return "Anna ryhmän nimi";
        }
        if (ryhma.getNimi().length() > 50) {
            return "Kentän nimi max. pituus 50 merkkiä";
        }
        if (ryhma.getKuvaus().length() > 250) {
            return "Kentän kuvaus max. pituus 250 merkkiä";
        }
        return null;
    }

    public static List<Ryhma> getRyhmatJaViestit(int kayttajaId) throws SQLException {

        //ArrayList<Ryhma> ryhmat = getRyhmat();
        ArrayList<Ryhma> ryhmat = Ryhma.etsiKayttajanRyhmat(kayttajaId);
        for (Ryhma ryhma : ryhmat) {
            ryhma.setViestit(Viesti.etsiRyhmanViestit(ryhma.getTunnus(), kayttajaId));
        }
        return ryhmat;

    }

//    public static List<Ryhma> getRyhmatJaKayttajat(int kayttajaId) throws SQLException {
//
//        ArrayList<Ryhma> ryhmat = getRyhmat();
//        for (Ryhma ryhma : ryhmat) {
//            ryhma.setKayttajat(Kayttaja.etsiRyhmanKayttajat(ryhma.getTunnus()));
//        }
//        return ryhmat;
//
//    }
    public static List<Ryhma> filterRyhmatJaViestit(List<Ryhma> ryhmat, String filter) {
        filter = filter.toLowerCase();

        for (int j = 0; j < ryhmat.size(); j++) {
            List<Viesti> viestit = ryhmat.get(j).getViestit();
            int tunnus = 0;
            for (int i = 0; i < viestit.size(); i++) {
                if (viestit.get(i).getPaaviesti() == 0) {
                    tunnus = viestit.get(i).getTunnus();
                    if (!viestit.get(i).getKirjoittajaNimi().toLowerCase().contains(filter)
                            && !viestit.get(i).getOtsikko().toLowerCase().contains(filter)
                            && !viestit.get(i).getKirjoituspvm().toString().toLowerCase().contains(filter)) {
                        viestit.remove(viestit.get(i));
                        i = -1;
                        for (int k = 0; k < viestit.size(); k++) {
                            if (viestit.get(k).getPaaviesti() == tunnus) {
                                viestit.remove(viestit.get(k));
                                k = -1;
                            }
                        }
                    }
                }
            }
            if (ryhmat.get(j).getViestit().isEmpty()) {
                ryhmat.remove(ryhmat.get(j));
                j = -1;
            }
        }
        return ryhmat;

    }

    public static Ryhma getRyhma(int tunnus) throws SQLException {
        String sql = "SELECT tunnus, nimi, kuvaus from ryhma "
                + "where tunnus=?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, tunnus);
        ResultSet tulokset = kysely.executeQuery();

        Ryhma r = null;
        if (tulokset.next()) {

            r = new Ryhma();
            r.setTunnus(tulokset.getInt("tunnus"));
            r.setNimi(tulokset.getString("nimi"));
            r.setKuvaus(tulokset.getString("kuvaus"));
            r.setKayttajat(Kayttaja.etsiRyhmanKayttajat(r.getTunnus()));
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

        return r;
    }

    public static ArrayList<Ryhma> getRyhmat() throws SQLException {
        String sql = "SELECT tunnus, nimi, kuvaus from ryhma order by nimi";
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

    public static void paivitaRyhma(Ryhma ryhma, int[] jasenet) throws SQLException {
        String sql = "UPDATE ryhma SET nimi = ?, kuvaus = ? WHERE tunnus = ?";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, ryhma.getNimi());
        kysely.setString(2, ryhma.getKuvaus());
        kysely.setInt(3, ryhma.getTunnus());

        int tulos = kysely.executeUpdate();
        if (tulos == 1) {
            Ryhma.poistaRyhmanJasenet(ryhma.getTunnus());
            if (jasenet != null) {
                ryhma.lisaaRyhmanJasenet(jasenet);
            }
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

    public int lisaaRyhma() throws SQLException {
        String sql = "INSERT into ryhma(nimi, kuvaus) values(?,?) RETURNING tunnus ";
        Connection yhteys = Yhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, this.getNimi());
        kysely.setString(2, this.getKuvaus());

        ResultSet ids = kysely.executeQuery();
        ids.next();

        this.setTunnus(ids.getInt(1));

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
        return this.getTunnus();
    }

    public static void poistaRyhma(int tunnus) throws SQLException {
        poistaRyhmanJasenet(tunnus);
        String sql = "DELETE from ryhma WHERE tunnus = ?";
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

    public static void poistaRyhmanJasenet(int tunnus) throws SQLException {
        String sql = "DELETE from ryhmanjasenet WHERE ryhmatunnus = ?";
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
    public static void poistaRyhmanViestit(int tunnus) throws SQLException {
        String sql = "DELETE from viesti WHERE ryhma = ?";
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

    public void lisaaRyhmanJasenet(int[] jasenet) throws SQLException {
        for (int id : jasenet) {

            String sql = "INSERT into ryhmanjasenet(ryhmatunnus, jasentunnus) values(?,?) RETURNING ryhmatunnus";
            Connection yhteys = Yhteys.getYhteys();
            PreparedStatement kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, this.getTunnus());
            kysely.setInt(2, id);

            ResultSet ids = kysely.executeQuery();

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
    }

    public static ArrayList<Ryhma> etsiKayttajanRyhmat(int id) throws SQLException {
        String sql = "SELECT tunnus,nimi from ryhma j, ryhmanjasenet r"
                + " where r.jasentunnus = ? and j.tunnus=r.ryhmatunnus order by nimi asc";
        Connection yhteys = Yhteys.getYhteys();
        if (yhteys == null) {
            return null;
        }
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Ryhma> ryhmat = new ArrayList();

        while (tulokset.next()) {
            Ryhma k = new Ryhma();
            k.setNimi(tulokset.getString("nimi"));
            k.setTunnus(tulokset.getInt("tunnus"));
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

    public static void poistaJasenenRyhmat(int tunnus) throws SQLException {
        String sql = "DELETE from ryhmanjasenet WHERE jasentunnus = ?";
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

    public static void lisaaJasenenRyhmat(int[] ryhmat, int kayttaja) throws SQLException {
        for (int id : ryhmat) {

            String sql = "INSERT into ryhmanjasenet(jasentunnus, ryhmatunnus) values(?,?) RETURNING ryhmatunnus";
            Connection yhteys = Yhteys.getYhteys();
            PreparedStatement kysely = yhteys.prepareStatement(sql);
            kysely.setInt(1, kayttaja);
            kysely.setInt(2, id);

            ResultSet ids = kysely.executeQuery();

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
    }
}
