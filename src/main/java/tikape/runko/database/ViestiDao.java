package tikape.runko.database;

import java.util.List;
import java.util.stream.Collectors;
import tikape.runko.collectors.ViestiKeraaja;
import tikape.runko.domain.Aikaleima;
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
    
    public void lisaa(Viestiketju ketju, String sisalto, String nimimerkki, Aikaleima aikaleima) {
        String kysely = "INSERT INTO Viesti (viestiketju, sisalto, nimimerkki, aikaleima) "
                + "VALUES (?, ?, ?, ?)";
        this.database.update(kysely, ketju.getId(), sisalto, nimimerkki, aikaleima.toString());
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

    public List<Viesti> alueidenViimeisimmat(List<Keskustelualue> alueet) {
        return alueet.stream()
                .map((alue) -> this.etsiTasmaavat(alue))
                .map((viestit) -> viestit.stream()
                        .max(Viesti::compareTo).orElse(null))
                .collect(Collectors.toList());
    }

    public List<Integer> alueidenViestimaarat(List<Keskustelualue> alueet) {
        return alueet.stream()
                .map((alue) -> this.etsiTasmaavat(alue))
                .map((viestit) -> viestit.size())
                .collect(Collectors.toList());
    }

    public List<Viesti> ketjujenViimeisimmat(List<Viestiketju> ketjut) {
        return ketjut.stream()
                .map((ketju) -> this.etsiTasmaavat(ketju))
                .map((viestit) -> viestit.stream()
                        .max(Viesti::compareTo).orElse(null))
                .collect(Collectors.toList());
    }

    public List<Integer> ketjujenViestimaarat(List<Viestiketju> ketjut) {
        return ketjut.stream()
                .map((ketju) -> this.etsiTasmaavat(ketju))
                .map((viestit) -> viestit.size())
                .collect(Collectors.toList());
    }
}


