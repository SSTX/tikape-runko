/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.collectors;

import java.sql.ResultSet;
import java.sql.SQLException;
import tikape.runko.database.Keraaja;
import tikape.runko.domain.Keskustelualue;

/**
 *
 * @author ttiira
 */
public class KeskustelualueKeraaja implements Keraaja<Keskustelualue> {

    @Override
    public Keskustelualue keraa(ResultSet rs) throws SQLException {
        return new Keskustelualue(rs.getInt("id"), rs.getString("nimi"));
    }
    
}
