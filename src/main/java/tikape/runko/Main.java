package tikape.runko;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.*;
import tikape.runko.domain.*;

public class Main {

    public static void main(String[] args) throws Exception {
        
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
        
        Database database = new Database("org.sqlite.JDBC", "jdbc:sqlite:tietokanta.db");
        KeskustelualueDao keskustelualueDao = new KeskustelualueDao(database);
        ViestiketjuDao viestiketjuDao = new ViestiketjuDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Keskustelualue> alueet = keskustelualueDao.etsiKaikki();
            List<Viesti> viimeisimmat = viestiDao.alueidenViimeisimmat(alueet);
            List<Integer> viestimaarat = viestiDao.alueidenViestimaarat(alueet);
            map.put("alueet", alueet);
            map.put("viimeisimmatViestit", viimeisimmat);
            map.put("viestimaarat", viestimaarat);

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/alue/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Keskustelualue alue = keskustelualueDao.etsiYksi(Integer.parseInt(req.params(":id")));
            List<Viestiketju> ketjut = viestiketjuDao.viimeisimmatKymmenen();
            List<Viesti> viimeisimmat = viestiDao.ketjujenViimeisimmat(ketjut);
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

        post("/", (req, res) -> {
            String nimi = req.queryParams("nimi");
            keskustelualueDao.lisaa(nimi);
            res.redirect("/");
            return "";
        });

        post("/alue/:id", (req, res) -> {
            String nimimerkki = req.queryParams("nimimerkki");
            String sisalto = req.queryParams("sisalto");
            String aihe = req.queryParams("aihe");
            viestiketjuDao.lisaa(aihe);
            Viestiketju lisatty = viestiketjuDao.uusin();
            viestiDao.lisaa(lisatty, sisalto, nimimerkki, new Aikaleima(new Date(System.currentTimeMillis())));
            res.redirect("/ketju/" + lisatty.getId());
            return "";
        });
    }
}
