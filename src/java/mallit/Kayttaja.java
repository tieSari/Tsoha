package mallit;

import Tietokanta.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Kayttaja {

    private int id;
    private String tunnus;
    private String salasana;
    
    public Kayttaja()
    {
        
    }

    public Kayttaja(int id, String tunnus, String salasana) {
        this.id = id;
        this.tunnus = tunnus;
        this.salasana = salasana;
    }

    public String getSalasana() {
        return salasana;
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

    
public static List<Kayttaja> getKayttajat() throws SQLException {
  String sql = "SELECT tunnus, kayttajatunnus, salasana from jasen";
  Connection yhteys = Yhteys.getYhteys();
  PreparedStatement kysely = yhteys.prepareStatement(sql);
  ResultSet tulokset = kysely.executeQuery();

  ArrayList<Kayttaja> kayttajat = new ArrayList<>();
  while (tulokset.next()) {
    //Luodaan tuloksia vastaava olio ja palautetaan olio:
    Kayttaja k = new Kayttaja();
    k.setId(tulokset.getInt("tunnus"));
    k.setTunnus(tulokset.getString("kayttajatunnus"));
    k.setSalasana(tulokset.getString("salasana"));

    kayttajat.add(k);
  }   
  //Suljetaan kaikki resutuloksetsit:
  try { tulokset.close(); } catch (SQLException e) {}
  try { kysely.close(); } catch (SQLException e) {}
  try { yhteys.close(); } catch (SQLException e) {}

  return kayttajat;
}
}
