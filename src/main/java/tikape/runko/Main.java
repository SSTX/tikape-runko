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

    static final int KOHTEITA_SIVULLA = 10;

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
            alueet.sort(Keskustelualue::compareTo);
            List<Viesti> viimeisimmat = viestiDao.alueidenViimeisimmat(alueet);
            List<Integer> viestimaarat = viestiDao.alueidenViestimaarat(alueet);
            map.put("alueet", alueet);
            map.put("viimeisimmatViestit", viimeisimmat);
            map.put("viestimaarat", viestimaarat);

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/alue/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            System.out.println(req.params(":id"));
            Keskustelualue alue = keskustelualueDao.etsiYksi(Integer.parseInt(req.params(":id")));
            int sivu = Integer.parseInt(req.queryParams("sivu"));
            int ketjujenMaara = viestiketjuDao.etsiTasmaavat(alue).size();
            List<Viestiketju> ketjut = viestiketjuDao.etsiRajoin(alue, sivu * KOHTEITA_SIVULLA, KOHTEITA_SIVULLA*(sivu + 1));
            List<Viesti> viimeisimmat = viestiDao.ketjujenViimeisimmat(ketjut);
            List<Integer> viestimaarat = viestiDao.ketjujenViestimaarat(ketjut);
            String AlueenNimi = alue.getNimi();
            
            map.put("AlueenNimi",AlueenNimi);
            map.put("alue", alue);
            map.put("ketjut", ketjut);
            map.put("ketjujenMaara", ketjujenMaara);
            map.put("viimeisimmatViestit", viimeisimmat);
            map.put("viestimaarat", viestimaarat);

            return new ModelAndView(map, "keskustelualue");
        }, new ThymeleafTemplateEngine());

        get("/ketju/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Viestiketju ketju = viestiketjuDao.etsiYksi(Integer.parseInt(req.params(":id")));
            int sivu = Integer.parseInt(req.queryParams("sivu"));
            int viestimaara = viestiDao.etsiTasmaavat(ketju).size();
            List<Viesti> viestit = viestiDao.etsiRajoin(ketju, KOHTEITA_SIVULLA*sivu, KOHTEITA_SIVULLA*(sivu + 1));
            String ketjunNimi = ketju.getAihe();
            Keskustelualue alue = keskustelualueDao.ketjunAlue(ketju);
            map.put("alue", alue);
            map.put("ketjunNimi", ketjunNimi);
            map.put("viestimaara", viestimaara);
            map.put("viestit", viestit);
            map.put("ketju", ketju);
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
            Aikaleima aikaleima = new Aikaleima(new Date(System.currentTimeMillis()));
            Keskustelualue alue = keskustelualueDao.etsiYksi(Integer.parseInt(req.params(":id")));
            viestiketjuDao.lisaa(alue, aihe);
            Viestiketju lisatty = viestiketjuDao.uusin();
            viestiDao.lisaa(lisatty, sisalto, nimimerkki, aikaleima);
            res.redirect("/ketju/" + lisatty.getId() + "?sivu=" + req.queryParams("sivu"));
            return "";
        });
        
        post("/ketju/:id", (req, res) -> {
            Viestiketju ketju = viestiketjuDao.etsiYksi(Integer.parseInt(req.params(":id")));
            String sisalto = req.queryParams("sisalto");
            String nimimerkki = req.queryParams("nimimerkki");
            Aikaleima aikaleima = new Aikaleima(new Date(System.currentTimeMillis()));
            viestiDao.lisaa(ketju, sisalto, nimimerkki, aikaleima);
            res.redirect("/ketju/" + req.params(":id") + "?sivu=" + req.queryParams("sivu"));
            return "";
        });
    }
}
