
package tikape.runko.database;

import java.util.List;
import tikape.runko.collectors.ViestiketjuKeraaja;
import tikape.runko.domain.Viestiketju;
import tikape.runko.domain.Keskustelualue;

/**
 *
 * @author ttiira
 */
public class ViestiketjuDao extends Dao<Viestiketju, Keskustelualue> {
    
    public ViestiketjuDao(Database db) {
        super(db);
    }

    @Override
    public Viestiketju etsiYksi(int key) {
        String kysely = "SELECT * FROM Viestiketju WHERE id = ?";
        List<Viestiketju> tulokset = this.database.kyselyTulokset(kysely, new ViestiketjuKeraaja(), key);
        if (tulokset.isEmpty()) {
            return null;
        } else {
            return tulokset.get(0);
        }
    }

    @Override
    public List<Viestiketju> etsiKaikki() {
        String kysely = "SELECT * FROM Viestiketju;";
        return this.database.kyselyTulokset(kysely, new ViestiketjuKeraaja());
    }

    @Override
    public List<Viestiketju> etsiTasmaavat(Keskustelualue k) {
        String kysely = "SELECT * FROM Viestiketju,Keskustelualue "
                + "WHERE Viestiketju.keskustelualue = Keskustelualue.id "
                + "AND Keskustelualue.id = ?";
        return this.database.kyselyTulokset(kysely, new ViestiketjuKeraaja(), k.getId());
    }
    
}
