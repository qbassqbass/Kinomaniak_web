/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kinomaniak.controllers;

import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import kinomaniak.beans.*;
import kinomaniak.database.DBConnector;

/**
 *
 * @author Jakub
 */
//@Named(value = "beanManager")
@Dependent
@ManagedBean
public class BeanManager {
    DBConnector db;
    ArrayList<Movie> movies;
    ArrayList<String> strings = new ArrayList<String>();
    /**
     * Creates a new instance of BeanManager
     */
    public BeanManager() {
        db = new DBConnector();
        db.connect();
        this.strings.add("Hello");
        this.strings.add("World");
    }

    public ArrayList<String> getStrings() {
        return strings;
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
    
    public ArrayList<Attraction> getAttractions(){
        return this.getAttractions(-1);
    }
    
    public ArrayList<Attraction> getAttractions(int id){
        ArrayList<Attraction> arr = new ArrayList<Attraction>();
        ArrayList<Object> a;
        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "Attraction") : db.parser.load(db.getConnection(), "Attraction", id)){
            arr.add((Attraction)obj);
        }
        return arr;
    }
    
    public ArrayList<CRoom> getRooms(){
        return this.getRooms(-1);
    }
    
    public ArrayList<CRoom> getRooms(int id){
        ArrayList<CRoom> arr = new ArrayList<CRoom>();
        ArrayList<Object> a;
        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "CRoom") : db.parser.load(db.getConnection(), "CRoom", id)){
            arr.add((CRoom)obj);
        }
        return arr;
    }
    
    public ArrayList<Product> getProducts(){
        return this.getProducts(-1);
    }
    
    public ArrayList<Product> getProducts(int id){
        ArrayList<Product> arr = new ArrayList<Product>();
        ArrayList<Object> a;
        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "Product") : db.parser.load(db.getConnection(), "Product", id)){
            arr.add((Product)obj);
        }
        return arr;
    }
    
    public ArrayList<Report> getReports(){
        return this.getReports(-1);
    }
    
    public ArrayList<Report> getReports(int id){
        ArrayList<Report> arr = new ArrayList<Report>();
        ArrayList<Object> a;
        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "Report") : db.parser.load(db.getConnection(), "Report", id)){
            arr.add((Report)obj);
        }
        return arr;
    }
    
    public ArrayList<Res> getRes(){
        return this.getRes(-1);
    }
    
    public ArrayList<Res> getRes(int id){
        ArrayList<Res> arr = new ArrayList<Res>();
        ArrayList<Object> a;
        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "Res") : db.parser.load(db.getConnection(), "Res", id)){
            arr.add((Res)obj);
        }
        return arr;
    }
    
    public ArrayList<Show> getShows(){
        return this.getShows(-1);
    }
    
    public ArrayList<Show> getShows(int id){
        ArrayList<Show> arr = new ArrayList<Show>();
        ArrayList<Object> a;
        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "Show") : db.parser.load(db.getConnection(), "Show", id)){
            arr.add((Show)obj);
        }
        return arr;
    }
    
    public ArrayList<Ticket> getTickets(){
        return this.getTickets(-1);
    }
    
    public ArrayList<Ticket> getTickets(int id){
        ArrayList<Ticket> arr = new ArrayList<Ticket>();
        ArrayList<Object> a;
        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "Ticket") : db.parser.load(db.getConnection(), "Ticket", id)){
            arr.add((Ticket)obj);
        }
        return arr;
    }
    
    public ArrayList<User> getUsers(){
        return this.getUsers(-1);
    }
    
    public ArrayList<User> getUsers(int id){
        ArrayList<User> arr = new ArrayList<User>();
        ArrayList<Object> a;
        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "User") : db.parser.load(db.getConnection(), "User", id)){
            arr.add((User)obj);
        }
        return arr;
    }
    
//    public ArrayList<Blaaa> getRooms(){
//        return this.getRooms(-1);
//    }
//    
//    public ArrayList<Blaaa> getRooms(int id){
//        ArrayList<Blaaa> arr = new ArrayList<Blaaa>();
//        ArrayList<Object> a;
//        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "Blaaa") : db.parser.load(db.getConnection(), "Blaaa", id)){
//            arr.add((Blaaa)obj);
//        }
//        return arr;
//    }
    
}
