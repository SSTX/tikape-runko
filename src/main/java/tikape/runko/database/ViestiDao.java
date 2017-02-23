package tikape.runko.database;

import java.util.List;
import tikape.runko.collectors.ViestiKeraaja;
import tikape.runko.domain.Keskustelualue;
import tikape.runko.domain.Viesti;
import tikape.runko.domain.Viestiketju;

/**
 *
 * @author ttiira
 */
public class ViestiDao extends Dao<Viesti, Viestiketju> {

    public ViestiDao(Database db) {
        super(db);
    }

    @Override
    public Viesti etsiYksi(int key) {
        String kysely = "SELECT * FROM Viesti WHERE id = ?";
        List<Viesti> tulokset = this.database.kyselyTulokset(kysely, new ViestiKeraaja(), key);
        if (tulokset.isEmpty()) {
            return null;
        } else {
            return tulokset.get(0);
        }
    }

    @Override
    public List<Viesti> etsiKaikki() {
        String kysely = "SELECT * FROM Viesti;";
        return this.database.kyselyTulokset(kysely, new ViestiKeraaja());
    }

    @Override
    public List<Viesti> etsiTasmaavat(Viestiketju k) {
        if (k == null) {
            return null;
        }
        String kysely = "SELECT Viesti.* "
                + "FROM Viesti,Viestiketju "
                + "WHERE Viesti.viestiketju = Viestiketju.id "
                + "AND Viestiketju.id = ?;";
        return this.database.kyselyTulokset(kysely, new ViestiKeraaja(), k.getId());
    }

    public List<Viesti> etsiTasmaavat(Keskustelualue alue) {
        if (alue == null) {
            return null;
        }
        String kysely = "SELECT Viesti.* "
                + "FROM Viesti,Viestiketju,Keskustelualue "
                + "WHERE Viesti.viestiketju = Viestiketju.id "
                + "AND Viestiketju.keskustelualue = Keskustelualue.id "
                + "AND Keskustelualue.id = ?;";
        return this.database.kyselyTulokset(kysely, new ViestiKeraaja(), alue.getId());
    }
}
