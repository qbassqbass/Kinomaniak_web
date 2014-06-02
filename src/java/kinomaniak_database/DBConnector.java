/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kinomaniak_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import kinomaniak_objs.Movie;

/**
 *
 * @author Qbass
 */
public class DBConnector {
    private final static String DBURL = "jdbc:mysql://192.168.1.3:3306/test?characterEncoding=utf8";
    private final static String DBUSER = "root";
    private final static String DBPASS = "windowsshit";
    private final static String DBDRIVER = "com.mysql.jdbc.Driver";
    
    private Connection connection;
    private Statement statement;
    private String query;
    private final Parser parser;

    public DBConnector(){
        this.parser = new Parser();
    }
    
    public Connection getConnection(){
        return this.connection;
    }
    
    public void connect(){
        try {
            Class.forName(DBDRIVER).newInstance();
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void save(Object obj){
        query = this.parser.save(obj);
        
        try{
//            Class.forName(DBDRIVER).newInstance();
//            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            
            statement.close();
            connection.close();
        }catch(SQLException e){
            System.err.println("Exception: "+e);
        }
    }
    
    public ArrayList<Object> load(String type){
//        try {
            ArrayList<Object> arr = new ArrayList<Object>();
//            Class.forName(DBDRIVER).newInstance();
//            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            arr = this.parser.load(this.connection, type);
            return arr;
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
    }
}
