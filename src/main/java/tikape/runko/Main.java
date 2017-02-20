package tikape.runko;

import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KeskustelualueDao;
import tikape.runko.domain.Keskustelualue;
import tikape.runko.domain.Palsta;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("org.sqlite.JDBC", "jdbc:sqlite:tietokanta.db");
        KeskustelualueDao keskusteluAlueDao = new KeskustelualueDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Keskustelualue> alueet = keskusteluAlueDao.etsiKaikki();
            map.put("alueet", alueet);

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
    }
}
