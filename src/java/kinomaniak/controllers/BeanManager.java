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
    ArrayList<Movie> movies = new ArrayList<Movie>();
    ArrayList<Attraction> attractions = new ArrayList<Attraction>();
    ArrayList<Product> products = new ArrayList<Product>();
    ArrayList<Report> reports = new ArrayList<Report>();
    ArrayList<Res> res = new ArrayList<Res>();
    ArrayList<Show> shows = new ArrayList<Show>();
    ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    ArrayList<User> users = new ArrayList<User>();
    ArrayList<CRoom> rooms = new ArrayList<CRoom>();
    
    Integer id = -1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
//    ArrayList<String> strings = new ArrayList<String>();
    /**
     * Creates a new instance of BeanManager
     */
    public BeanManager() {
        db = new DBConnector();
        db.connect();
//        initMovies();
//        this.strings.add("Hello");
//        this.strings.add("World");
    }

//    public ArrayList<String> getStrings() {
//        return strings;
//    }
    
//    public ArrayList<Movie> getMovies(){
//        initMovies();
//        return movies;
//    }
//
//    public void initMovies(){
//        this.initMovies(-1);
//    }
    
    public void buyProduct(int id){
        
    }
    
    public ArrayList<Movie> getMovies(){
//        ArrayList<Movie> arr = new ArrayList<Movie>();
        ArrayList<Object> a;
        if(id == -1){
            a = db.parser.load(db.getConnection(), "Movie");
        }else{
            a = db.parser.load(db.getConnection(), "Movie", id);
        }
        for(Object obj : a){
//            arr.add((Movie)obj);
            this.movies.add((Movie)obj);
        }        
        return this.movies;
    }
    
    public ArrayList<Attraction> getAttractions() {
        ArrayList<Object> a;
        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "Attraction") : db.parser.load(db.getConnection(), "Attraction", id)){
            attractions.add((Attraction)obj);
        }
        return attractions;
    }
    
//    public ArrayList<Attraction> getAttractions(int id){
//        ArrayList<Attraction> arr = new ArrayList<Attraction>();
//        ArrayList<Object> a;
//        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "Attraction") : db.parser.load(db.getConnection(), "Attraction", id)){
//            arr.add((Attraction)obj);
//        }
//        return arr;
//    }
    
    public ArrayList<CRoom> getRooms(){
        ArrayList<Object> a;
        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "CRoom") : db.parser.load(db.getConnection(), "CRoom", id)){
            rooms.add((CRoom)obj);
        }
        return rooms;
    }
    
//    public ArrayList<CRoom> getRooms(int id){
//        ArrayList<CRoom> arr = new ArrayList<CRoom>();
//        ArrayList<Object> a;
//        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "CRoom") : db.parser.load(db.getConnection(), "CRoom", id)){
//            arr.add((CRoom)obj);
//        }
//        return arr;
//    }
    
    public ArrayList<Product> getProducts(){
        ArrayList<Object> a;
        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "Product") : db.parser.load(db.getConnection(), "Product", id)){
            products.add((Product)obj);
        }
        return products;
    }
    
//    public ArrayList<Product> getProducts(int id){
//        ArrayList<Product> arr = new ArrayList<Product>();
//        ArrayList<Object> a;
//        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "Product") : db.parser.load(db.getConnection(), "Product", id)){
//            arr.add((Product)obj);
//        }
//        return arr;
//    }
    
    public ArrayList<Report> getReports(){
        ArrayList<Object> a;
        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "Report") : db.parser.load(db.getConnection(), "Report", id)){
            reports.add((Report)obj);
        }
        return reports;
    }
    
//    public ArrayList<Report> getReports(int id){
//        ArrayList<Report> arr = new ArrayList<Report>();
//        ArrayList<Object> a;
//        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "Report") : db.parser.load(db.getConnection(), "Report", id)){
//            arr.add((Report)obj);
//        }
//        return arr;
//    }
    
    public ArrayList<Res> getRes(){
        ArrayList<Object> a;
        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "Res") : db.parser.load(db.getConnection(), "Res", id)){
            res.add((Res)obj);
        }
        return res;
    }
    
//    public ArrayList<Res> getRes(int id){
//        ArrayList<Res> arr = new ArrayList<Res>();
//        ArrayList<Object> a;
//        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "Res") : db.parser.load(db.getConnection(), "Res", id)){
//            arr.add((Res)obj);
//        }
//        return arr;
//    }
    
    public ArrayList<Show> getShows(){
        ArrayList<Object> a;
        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "Show") : db.parser.load(db.getConnection(), "Show", id)){
            shows.add((Show)obj);
        }
        return shows;
    }
    
//    public ArrayList<Show> getShows(int id){
//        ArrayList<Show> arr = new ArrayList<Show>();
//        ArrayList<Object> a;
//        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "Show") : db.parser.load(db.getConnection(), "Show", id)){
//            arr.add((Show)obj);
//        }
//        return arr;
//    }
    
    public ArrayList<Ticket> getTickets(){
        ArrayList<Object> a;
        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "Ticket") : db.parser.load(db.getConnection(), "Ticket", id)){
            tickets.add((Ticket)obj);
        }
        return tickets;
    }
    
//    public ArrayList<Ticket> getTickets(int id){
//        ArrayList<Ticket> arr = new ArrayList<Ticket>();
//        ArrayList<Object> a;
//        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "Ticket") : db.parser.load(db.getConnection(), "Ticket", id)){
//            arr.add((Ticket)obj);
//        }
//        return arr;
//    }
    
    public ArrayList<User> getUsers(){
        ArrayList<Object> a;
        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "User") : db.parser.load(db.getConnection(), "User", id)){
            users.add((User)obj);
        }
        return users;
    }
    
//    public ArrayList<User> getUsers(int id){
//        ArrayList<User> arr = new ArrayList<User>();
//        ArrayList<Object> a;
//        for(Object obj : a = (id == -1) ? db.parser.load(db.getConnection(), "User") : db.parser.load(db.getConnection(), "User", id)){
//            arr.add((User)obj);
//        }
//        return arr;
//    }
    
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
