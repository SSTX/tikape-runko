/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author ttiira
 */
public class Viesti implements Comparable<Viesti> {
    private int id;
    private String nimimerkki;
    private String sisalto;
    private Aikaleima aikaleima;
    
    public Viesti(int id, String sisalto, String nimimerkki, Aikaleima aikaleima) {
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

    public Aikaleima getAikaleima() {
        return aikaleima;
    }

    public void setNimimerkki(String nimimerkki) {
        this.nimimerkki = nimimerkki;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }

    public void setAikaleima(Aikaleima aikaleima) {
        this.aikaleima = aikaleima;
    }
    
    public int compareTo(Viesti v) {
        return this.aikaleima.compareTo(v.getAikaleima());
    }
    
    
}
