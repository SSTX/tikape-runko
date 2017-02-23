/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author ttiira
 */
public class Aikaleima implements Comparable<Aikaleima> {

    private DateFormat form;
    private Date date;

    public Aikaleima(String aika) {
        this.form = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        this.form.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            this.date = form.parse(aika);
        } catch (ParseException e) {
            this.date = null;
        }
    }
    
    @Override
    public String toString() {
        return this.form.format(this.date);
    }
    
    public Date getDate() {
        return this.date;
    }

    @Override
    public int compareTo(Aikaleima aikaleima) {
        return this.date.compareTo(aikaleima.getDate());
    }
}
