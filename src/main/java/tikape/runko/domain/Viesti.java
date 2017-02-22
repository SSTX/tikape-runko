/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.util.Date;

/**
 *
 * @author ttiira
 */
public class Viesti {
    private int id;
    private String nimimerkki;
    private String sisalto;
    private String aikaleima;
    
    public Viesti(int id, String sisalto, String nimimerkki, String aikaleima) {
        this.id = id;
        this.nimimerkki = nimimerkki;
        this.sisalto = sisalto;
        this.aikaleima = aikaleima;
    }

    public int getId() {
        return this.id;
    }

    public String getNimimerkki() {
        return nimimerkki;
    }

    public String getSisalto() {
        return sisalto;
    }

    public String getAikaleima() {
        return aikaleima;
    }

    public void setNimimerkki(String nimimerkki) {
        this.nimimerkki = nimimerkki;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }

    public void setAikaleima(String aikaleima) {
        this.aikaleima = aikaleima;
    }
    
    
}
