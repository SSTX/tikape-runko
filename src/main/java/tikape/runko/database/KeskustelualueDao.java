/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.util.List;
import tikape.runko.collectors.KeskustelualueKeraaja;
import tikape.runko.domain.Keskustelualue;
import tikape.runko.domain.Viestiketju;

/**
 *
 * @author ttiira
 */
public class KeskustelualueDao extends Dao<Keskustelualue, Keskustelualue> {
    
    public KeskustelualueDao(Database db) {
        super(db);
    }

    @Override
    public List<Keskustelualue> etsiKaikki() {
        String kysely = "SELECT * FROM Keskustelualue;";
        return this.database.kyselyTulokset(kysely, new KeskustelualueKeraaja());
    }

    @Override
    public List<Keskustelualue> etsiTasmaavat(Keskustelualue k) {
        return this.etsiKaikki();
    }
    
}
