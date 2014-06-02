/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kinomaniak_database;

import java.util.ArrayList;
import kinomaniak_objs.Movie;

/**
 *
 * @author Jakub
 */
public class DBTester {
    
    
    public static void main(String[] args){
        DBConnector connector = new DBConnector();
        connector.connect();
        ArrayList<Object> movs = connector.load("Movie");
        if(movs.get(0) instanceof Movie){
            Movie m = (Movie)movs.get(0);
            System.out.println(m.getName());
            System.out.println(m.getGenre());
            System.out.println(m.getRating());
            System.out.println(m.getDesc());
            
        }else{
            System.out.println("Not a movie");
        }
        
    }
}
