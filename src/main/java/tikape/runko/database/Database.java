/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ttiira
 */
public class Database {

    private Connection yhteys;

    public Database(String driver, String address) throws Exception {
        Class.forName(driver);
        this.yhteys = DriverManager.getConnection(address);
    }

    public <T> List<T> kyselyTulokset(String kysely, Keraaja<T> keraaja, Object... parametrit) {

        List<T> rivit = new ArrayList<>();
        try (PreparedStatement lause = yhteys.prepareStatement(kysely)) {
            for (int i = 0; i < parametrit.length; i++) {
                lause.setObject(i + 1, parametrit[i]);
            }
            ResultSet rs = lause.executeQuery();
            while (rs.next()) {
                rivit.add(keraaja.keraa(rs));
            }
            rs.close();
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return rivit;
    }

    public int update(String kysely, Object... parametrit) {
        int muutoksia;
        try (PreparedStatement lause = yhteys.prepareStatement(kysely)) {
            for (int i = 0; i < parametrit.length; i++) {
                lause.setObject(i + 1, parametrit[i]);
            }
            muutoksia = lause.executeUpdate();
            lause.close();
        } catch (Exception e) {
            muutoksia = 0;
        }
        return muutoksia;
    }
}
