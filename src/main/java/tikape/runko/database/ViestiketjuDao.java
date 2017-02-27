
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
    
    public List<Viestiketju> viimeisimmatKetjutRajoin(Keskustelualue alue, int alaraja, int ylaraja){
        String kysely = "SELECT Viestiketju.* FROM Viestiketju,Viesti,Keskustelualue "
                + "WHERE Viestiketju.id = Viesti.viestiketju "
                + "AND Viestiketju.keskustelualue = Keskustelualue.id "
                + "AND Viestiketju.keskustelualue = ? "
                + "ORDER BY Viesti.aikaleima DESC "
                + "LIMIT ? OFFSET ?";
        return this.database.kyselyTulokset(kysely, new ViestiketjuKeraaja(), alue.getId(), ylaraja, alaraja);
    }
    
    public List<Viestiketju> viimeisimmatKymmenen(Keskustelualue alue) {
        return this.viimeisimmatKetjutRajoin(alue, 0, 10);
    }
    
    public Viestiketju uusin() {
        String kysely = "SELECT * FROM Viestiketju "
                + "ORDER BY id DESC "
                + "LIMIT 1";
        List<Viestiketju> tulokset = this.database.kyselyTulokset(kysely, new ViestiketjuKeraaja());
        return tulokset.stream().findFirst().orElse(null);
        
    }
    
    public void lisaa(Keskustelualue alue, String nimi) {
        String kysely = "INSERT INTO Viestiketju (keskustelualue, aihe) "
                + "VALUES (?, ?)";
        this.database.update(kysely, alue.getId(), nimi);
    }
}
