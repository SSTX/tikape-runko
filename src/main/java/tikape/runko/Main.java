package tikape.runko;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.*;
import tikape.runko.domain.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("org.sqlite.JDBC", "jdbc:sqlite:tietokanta.db");
        KeskustelualueDao keskustelualueDao = new KeskustelualueDao(database);
        ViestiketjuDao viestiketjuDao = new ViestiketjuDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Keskustelualue> alueet = keskustelualueDao.etsiKaikki();
            List<List<Viesti>> alueidenViestit = alueet.stream().map((alue)
                    -> viestiDao.etsiTasmaavat(alue)).collect(Collectors.toList());
            List<Viesti> viimeisimmat = alueidenViestit.stream().map((viestit) 
                    -> viestit.stream().max(Viesti::compareTo).orElse(null))
                    .collect(Collectors.toList());
            map.put("alueet", alueet);
            map.put("viimeisimmatViestit", viimeisimmat);

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/alue/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Keskustelualue alue = keskustelualueDao.etsiYksi(Integer.parseInt(req.params(":id")));
            List<Viestiketju> ketjut = viestiketjuDao.etsiTasmaavat(alue);
            map.put("ketjut", ketjut);

            return new ModelAndView(map, "keskustelualue");
        }, new ThymeleafTemplateEngine());

        get("/ketju/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Viestiketju ketju = viestiketjuDao.etsiYksi(Integer.parseInt(req.params(":id")));
            List<Viesti> viestit = viestiDao.etsiTasmaavat(ketju);
            map.put("viestit", viestit);
            return new ModelAndView(map, "viestiketju");
        }, new ThymeleafTemplateEngine());
    }
}
