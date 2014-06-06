/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak_mgmt;
import kinomaniak.beans.*;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * @deprecated 
 * @author qbass
 */
public class MovieDBMgmt {
    private String date;
    private int num;
    private Show[] shows;
    private Movie[] movies;
    private CRoom[] crooms;
    private Time time;
    private User[] users;
    private Show[] showtmp;
    private Movie[] movietmp;
    private CRoom[] croomtmp;
    private User[] usertmp;
    
    /**
     *
     */
    public MovieDBMgmt(){
        
    }
    /**
     *
     * @param hrs
     * @param mins
     */
    public void setTime(int hrs, int mins){
        this.time = new Time(hrs,mins);
    }
    /**
     *
     */
    public void getData(){
        try{
            ObjectInputStream we = new ObjectInputStream(new FileInputStream("Shows.kin"));
            this.date = (String)we.readObject();
            this.num = (Integer)we.readObject();
            this.shows = new Show[this.num];
            this.shows = (Show[])we.readObject();
            we.close();
            we = new ObjectInputStream(new FileInputStream("Movies.kin"));
            this.movies = (Movie[])we.readObject();
            we.close();
            we = new ObjectInputStream(new FileInputStream("CRooms.kin"));
            this.crooms = (CRoom[])we.readObject();
            we.close();
            we = new ObjectInputStream(new FileInputStream("Users.kin"));
            this.users = (User[])we.readObject();
            we.close();
         }catch(IOException e){
            System.err.println("IO Error: "+e);
        }catch(ClassNotFoundException e){
            System.err.println("Class not found: "+e);
        }
    }
    /**
     *
     */
    public void saveData(){
        try{
            ObjectOutputStream wy = new ObjectOutputStream(new FileOutputStream("Movies.kin"));
            wy.writeObject(this.movies);
            wy.close();
            wy = new ObjectOutputStream(new FileOutputStream("CRooms.kin"));
            wy.writeObject(this.crooms);
            wy.close();
            wy = new ObjectOutputStream(new FileOutputStream("Shows.kin"));
            Date dateNow = new Date (); 
            SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
            StringBuilder sdt = new StringBuilder( dt.format( dateNow ) );
            String st = sdt.toString();
            wy.writeObject(st);
            wy.writeObject(this.shows.length);
            wy.writeObject(this.shows);
            wy.close();
            wy = new ObjectOutputStream(new FileOutputStream("Users.kin"));
            wy.writeObject(this.users);
            wy.close();
        }catch(IOException e){
            System.err.println("IO Error: "+e);
        }
    }
    /**
     *
     * @param name
     * @param genre
     * @param rating
     */
    public void addMovie(String name,String genre,String rating){
        int len = this.movies.length;
        System.out.println("len:"+len);
        this.movietmp = new Movie[len+1];
        System.out.println("len+1:"+(len+1));
        System.arraycopy(this.movies,0,this.movietmp,0,len);
        this.movietmp[len] = new Movie(name,genre,rating);
        this.movies = new Movie[len+1];
        System.arraycopy(this.movietmp,0,this.movies,0,len+1);
    }
    /**
     *
     * @param id
     */
    public void addCRoom(int id){
        int len = this.crooms.length;
        this.croomtmp = new CRoom[len+1];
        System.arraycopy(this.crooms,0,this.croomtmp,0,len);
        this.croomtmp[len] = new CRoom(id);
        this.crooms = new CRoom[len+1];
        System.arraycopy(this.croomtmp,0,this.crooms,0,len+1);
    }
    /**
     *
     * @param movie
     * @param time
     * @param croom
     */
    public void addShow(Movie movie, Time time, CRoom croom ){
        int len = this.shows.length;
        int prevID = this.shows[len-1].getID();
        this.showtmp = new Show[len+1];
        System.arraycopy(this.shows,0,this.showtmp,0,len);
        this.showtmp[len] = new Show(movie,croom,time);
        this.showtmp[len].setID(prevID+1);
        this.shows = new Show[len+1];
        System.arraycopy(this.showtmp,0,this.shows,0,len+1);
    }    
    /**
     *
     * @param name
     * @param password
     * @param utype
     */
    public void addUser(String name, String password,int utype){
        int len = this.users.length;
        this.usertmp = new User[len+1];
        System.arraycopy(this.users,0,this.usertmp,0,len);
        this.usertmp[len] = new User(name,password,utype);
        this.users = new User[len+1];
        System.arraycopy(this.usertmp,0,this.users,0,len+1);
    }
    /**
     *
     * @param n
     */
    public void delMovie(int n){
        int len = this.movies.length;
        this.movietmp = new Movie[len];
        System.arraycopy(this.movies,0,this.movietmp,0,len);
        for(int i = n;i<this.movietmp.length-1;i++){
            this.movietmp[i] = this.movietmp[i+1];
        }
        this.movies = new Movie[len-1];
        System.arraycopy(this.movietmp,0,this.movies,0,len-1);
    }
    /**
     *
     * @param n
     */
    public void delCRoom(int n){
        int len = this.crooms.length;
        this.croomtmp = new CRoom[len];
        System.arraycopy(this.crooms,0,this.croomtmp,0,len);
        for(int i = n;i<this.croomtmp.length-1;i++){
            this.croomtmp[i] = this.croomtmp[i+1];
        }
        this.crooms = new CRoom[len-1];
        System.arraycopy(this.croomtmp,0,this.crooms,0,len-1);
    }
    /**
     *
     * @param n
     */
    public void delShow(int n){
        int len = this.shows.length;
        this.showtmp = new Show[len];
        System.arraycopy(this.shows,0,this.showtmp,0,len);
        for(int i = n;i<this.showtmp.length-1;i++){
            this.showtmp[i] = this.showtmp[i+1];
        }
        this.shows = new Show[len-1];
        System.arraycopy(this.showtmp,0,this.shows,0,len-1);
    }
    /**
     *
     * @param n
     */
    public void delUser(int n){
        int len = this.users.length;
        this.usertmp = new User[len];
        System.arraycopy(this.users,0,this.usertmp,0,len);
        for(int i = n;i<this.usertmp.length-1;i++){
            this.usertmp[i] = this.usertmp[i+1];
        }
        this.users = new User[len-1];
        System.arraycopy(this.usertmp,0,this.users,0,len-1);
    }
    /**
     *
     * @return
     */
    public Movie[] getMovies(){
        return this.movies;
    }
    /**
     *
     * @return
     */
    public CRoom[] getCRooms(){
        return this.crooms;
    }
    /**
     *
     * @return
     */
    public Show[] getShows(){
        return this.shows;
    }
    /**
     *
     * @return
     */
    public User[] getUsers(){
        return this.users;
    }
    /**
     *
     */
    public void listMovies(){
        for(int i=0;i<this.movies.length;i++){
            System.out.println(i+". "+this.movies[i].getName()+" Gen:"+this.movies[i].getGenre()+" Rat:"+this.movies[i].getRating());
        }
    }
    /**
     *
     */
    public void listCRooms(){
        for(int i=0;i<this.crooms.length;i++){
            System.out.println(i+". "+this.crooms[i].getID());
        }
    }
    /**
     *
     */
    public void listShows(){
        for(int i=0;i<this.shows.length;i++){
            System.out.println(i+". "+this.shows[i].getMovie().getName()+" Room: "+this.shows[i].getRoom().getID()+" Time: "+this.shows[i].getCTime().getHour()+':'+this.shows[i].getCTime().getMinute());
        }
    }
    /**
     *
     */
    public void listUsers(){
        for(int i=0;i<this.users.length;i++){
            System.out.println(i+". "+this.users[i].getName()+"  Type: "+this.users[i].getUType());
        }
    }
    /**
     *
     * @return
     */
    public Time getTime(){
        return time;
    }
}
