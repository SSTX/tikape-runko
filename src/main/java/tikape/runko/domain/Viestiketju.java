/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.util.List;

/**
 *
 * @author ttiira
 */
public class Viestiketju {
    private int id;
    private String aihe;
    private List<Viesti> viestit;
    
    public Viestiketju(int id, String aihe, List<Viesti> viestit) {
        this.id = id;
        this.aihe = aihe;
        this.viestit = viestit;
    }

    public int getId() {
        return this.id;
    }

    public String getAihe() {
        return aihe;
    }

    public void setAihe(String aihe) {
        this.aihe = aihe;
    }

    public List<Viesti> getViestit() {
        return viestit;
    }

    public void setViestit(List<Viesti> viestit) {
        this.viestit = viestit;
    }
}
