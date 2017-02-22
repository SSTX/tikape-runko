/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.collectors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import tikape.runko.database.Keraaja;
import tikape.runko.domain.Viestiketju;

/**
 *
 * @author ttiira
 */
public class ViestiketjuKeraaja implements Keraaja<Viestiketju> {

    @Override
    public Viestiketju keraa(ResultSet rs) throws SQLException {
        return new Viestiketju(rs.getInt("id"), rs.getString("aihe"), new ArrayList<>());
    }
    
}
