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
public class Palsta {
    private List<Keskustelualue> keskustelualueet;
    
    public Palsta(List<Keskustelualue> keskustelualueet) {
        this.keskustelualueet = keskustelualueet;
    }
    
    public Palsta() {
        this(new ArrayList<>());
    }

    public List<Keskustelualue> getKeskustelualueet() {
        return keskustelualueet;
    }

    public void setKeskustelualueet(List<Keskustelualue> keskustelualueet) {
        this.keskustelualueet = keskustelualueet;
    }
}
