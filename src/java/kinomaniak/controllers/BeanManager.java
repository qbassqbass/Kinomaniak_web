/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kinomaniak.controllers;

import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import kinomaniak.beans.Movie;
import kinomaniak.database.DBConnector;

/**
 *
 * @author Jakub
 */
@Named(value = "beanManager")
@Dependent
public class BeanManager {
    DBConnector db;
    /**
     * Creates a new instance of BeanManager
     */
    public BeanManager() {
        db = new DBConnector();
        db.connect();
    }
    
    public ArrayList<Movie> getMovies(){
        return this.getMovies(-1);
    }
    
    public ArrayList<Movie> getMovies(int id){
        ArrayList<Movie> arr = new ArrayList<Movie>();
        ArrayList<Object> a;
        if(id == -1){
            a = db.parser.load(db.getConnection(), "Movie");
        }else{
            a = db.parser.load(db.getConnection(), "Movie", id);
        }
        for(Object obj : a){
            arr.add((Movie)obj);
        }
        
        return arr;
    }
    
}
