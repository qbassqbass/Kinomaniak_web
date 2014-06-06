/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kinomaniak.beans;

import kinomaniak_objs.*;

/**
 *
 * @author Qbass
 */



public class GCFactory {
    private int lastId;
    private GCFactory instance = null;
    
    
    
    private GCFactory(){
        //get the last Card ID from DataBase
    }
    
    private int getLastId(){
        return this.lastId;
    }
    
    public GCFactory getInstance(){
        return (this.instance == null) ? (instance = new GCFactory()) : instance;
    }
    
    public GC createNewGC(int owner, float discount){        
        return (GC)new GoldCard(this.getLastId(), owner, discount);
    }
    
}
