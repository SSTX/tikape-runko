/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ttiira
 */
public class Keskustelualue {
    private int id;
    private List<Viestiketju> viestiketjut;
    private String nimi;
    
    public Keskustelualue(int id, String nimi, List<Viestiketju> viestiketjut) {
        this.id = id;
        this.nimi = nimi;
        this.viestiketjut = viestiketjut;
    }
    
    public Keskustelualue(int id, String nimi) {
        this(id, nimi, new ArrayList<>());
    }

    public List<Viestiketju> getViestiketjut() {
        return viestiketjut;
    }

    public void setViestiketjut(List<Viestiketju> viestiketjut) {
        this.viestiketjut = viestiketjut;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    @Override
    public String toString() {
        return this.nimi;
    }
    
}