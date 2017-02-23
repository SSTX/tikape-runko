/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.collectors;

import java.sql.ResultSet;
import java.sql.SQLException;

import tikape.runko.database.Keraaja;
import tikape.runko.domain.Aikaleima;
import tikape.runko.domain.Viesti;

/**
 *
 * @author ttiira
 */
public class ViestiKeraaja implements Keraaja<Viesti> {

    @Override
    public Viesti keraa(ResultSet rs) throws SQLException {
        Aikaleima aikaleima = new Aikaleima(rs.getString("aikaleima"));
        return new Viesti(rs.getInt("id"), rs.getString("sisalto"), rs.getString("nimimerkki"), aikaleima);
    }

}
