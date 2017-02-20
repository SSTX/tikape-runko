/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.util.List;


public abstract class Dao<T, K> {
    protected Database database;
    
    public Dao(Database database) {
        this.database = database;
    }
    
    public abstract List<T> etsiKaikki();
    
    public abstract List<T> etsiTasmaavat(K ehto);
    
    
}
