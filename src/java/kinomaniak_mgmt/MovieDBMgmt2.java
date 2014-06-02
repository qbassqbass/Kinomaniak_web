/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak_mgmt;
import kinomaniak_objs.*;
import kinomaniak_database.DBConnector;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * Klasa funkcjonalna dla Menadżera projektu Kinomaniak.
 * @author qbass
 */
public class MovieDBMgmt2 {
    private String date;
    private int num;
    private Time time;
    private List<Show> shows;
    private List<Movie> movies;
    private List<CRoom> crooms;
    private List<User> users;
    private List<Res> ress;
    private List<Product> prods;
    private List<Attraction> attrs;
    
    /**
     * Konstruktor klasy MovieDBMgmt tworzący listy obiektów projektu Kinomaniak.
     */
    public MovieDBMgmt2(){        
            this.shows = new ArrayList<Show>();
            this.movies = new ArrayList<Movie>();
            this.crooms = new ArrayList<CRoom>();
            this.users = new ArrayList<User>();
            this.prods = new ArrayList<Product>();
            this.attrs = new ArrayList<Attraction>();
    }
    /**
     * @deprecated 
     * Nieużywany konstruktor klasy MovieDBMgmt
     * @param showFile ścieżka pliku zawierającego listę Seansów
     * @param movieFile ścieżka pliku zawierającego listę Filmów
     * @param croomFile świeżka pliku zawierającego listę Sal kinowych
     * @param userFile ścieżka pliku zawierającego listę użytkowników
     */
    public MovieDBMgmt2(String showFile,String movieFile,String croomFile,String userFile){
            this.shows = new ArrayList<Show>();
            this.movies = new ArrayList<Movie>();
            this.crooms = new ArrayList<CRoom>();
            this.users = new ArrayList<User>();
            File f = new File(showFile);
    }
    /**
     * Metoda ustawiająca czas dla danego seansu
     * @param hrs godzina
     * @param mins minuty
     * @param day dzień
     * @param month miesiąc
     * @param year rok
     */
    public void setTime(int hrs, int mins,int day,int month,int year){
        this.time = new Time(hrs,mins,day,month,year);
    }
    /**
     * Metoda wczytująca dane z plików binarnych .kin.
     */
    @SuppressWarnings("unchecked")
    public void getData(){
        try{
            ObjectInputStream we = new ObjectInputStream(new FileInputStream("Shows.kin"));
            this.date = (String)we.readObject();
//            this.num = (Integer)we.readObject();
            //this.shows = (ArrayList<Show>)Arrays.asList((Show[])we.readObject()); -- if Arrays are in files
            this.shows = (ArrayList<Show>)we.readObject();
            we.close();
            we = new ObjectInputStream(new FileInputStream("Movies.kin"));
            this.movies = (ArrayList<Movie>)we.readObject();
            we.close();
            we = new ObjectInputStream(new FileInputStream("CRooms.kin"));
            this.crooms = (ArrayList<CRoom>)we.readObject();
            we.close();
            we = new ObjectInputStream(new FileInputStream("Users.kin"));
            this.users = (ArrayList<User>)we.readObject();
            we.close();
            we = new ObjectInputStream(new FileInputStream("Res.kin"));
            this.ress = (ArrayList<Res>)we.readObject();
            we.close();
            we = new ObjectInputStream(new FileInputStream("Product.kin"));
            this.prods = (ArrayList<Product>)we.readObject();
            we.close();
            we = new ObjectInputStream(new FileInputStream("Attraction.kin"));
            this.attrs = (ArrayList<Attraction>)we.readObject();
            we.close();
         }catch(IOException e){
            System.err.println("IO Error: "+e);
        }catch(ClassNotFoundException e){
            System.err.println("Class not found: "+e);
        }
    }
    
    public void getDataFromServer(){
        
    }
    /**
     * Metoda zapisująca dane do plików XML
     */
    public void saveXML(){
        Element res = new Element("Kinomaniak");
        for(Movie movie : this.movies){
            res.addContent(movie.toXML());
        }
        for(CRoom croom : this.crooms){
            res.addContent(croom.toXML());
        }
        for(Show show : this.shows){
            res.addContent(show.toXML());
        }
        for(User user : this.users){
            res.addContent(user.toXML());
        }
        for (Res r : this.ress){
            res.addContent(r.toXML());
        }
        for(Product p : this.prods){
            res.addContent(p.toXML());
        }
        
        Document doc = new Document(res);
        XMLOutputter xmlOutput = new XMLOutputter();
        
        xmlOutput.setFormat(Format.getPrettyFormat());
        try{
            FileOutputStream output = new FileOutputStream("data.xml");
            xmlOutput.output(doc, System.out);
            xmlOutput.output(doc, output);
        }catch(IOException e){
            System.err.println("IOExc: "+e);
        }
    }
    /** 
     * Metoda wczytująca dane z plików XML
     */
    public void loadXML(){
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File("data.xml");
        
        List<Show> s = new ArrayList<Show>();
        List<Movie> m = new ArrayList<Movie>();
        List<CRoom> c = new ArrayList<CRoom>();
        List<User> u = new ArrayList<User>();
        List<Res> r = new ArrayList<Res>();
        List<Product> p = new ArrayList<Product>();
        List<Attraction> a = new ArrayList<Attraction>();
        
        try{
            Document doc = (Document) builder.build(xmlFile);
            Element rootNode = doc.getRootElement();
            List list = rootNode.getChildren("Movie");
            for(int i = 0; i < list.size(); i++){
                Element node = (Element) list.get(i);
                Movie mv = new Movie(node); //add Constructor for this
                m.add(mv);
            }
            list = rootNode.getChildren("Show");
            for(int i = 0; i < list.size(); i++){
                Element node = (Element) list.get(i);
                Show sh = new Show(node); //add Constructor for this
                s.add(sh);
            }
            list = rootNode.getChildren("CRoom");
            for(int i = 0; i < list.size(); i++){
                Element node = (Element) list.get(i);
                CRoom cr = new CRoom(node); //add Constructor for this
                c.add(cr);
            }
            list = rootNode.getChildren("User");
            for(int i = 0; i < list.size(); i++){
                Element node = (Element) list.get(i);
                User us = new User(node); //add Constructor for this
                u.add(us);
            }
            list = rootNode.getChildren("Res");
            for(int i = 0; i < list.size(); i++){
                Element node = (Element) list.get(i);
                Res re = new Res(node); //add Constructor for this
                r.add(re);
            }
            list = rootNode.getChildren("Product");
            for(int i = 0; i < list.size(); i++){
                Element node = (Element) list.get(i);
                Product pr = new Product(node);
                p.add(pr);
            }
            
            
            this.users = u;
            this.shows = s;
            this.crooms = c;
            this.movies = m;
            this.ress = r;
            this.prods = p;
            this.attrs = a;
            
        }catch(IOException e){
            System.err.println("IOEx: "+e);
        }catch(JDOMException e){
            System.err.println("JDOMEx: "+e);
        }
    }
    /**
     * Metoda zapisująca dane do plików binarnych .kin.
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
//            wy.writeObject(this.shows.length);
            wy.writeObject(this.shows);
            wy.close();
            wy = new ObjectOutputStream(new FileOutputStream("Users.kin"));
            wy.writeObject(this.users);
            wy.close();
            wy = new ObjectOutputStream(new FileOutputStream("Res.kin"));
            wy.writeObject(this.ress);
            wy.close();
            wy = new ObjectOutputStream(new FileOutputStream("Product.kin"));
            wy.writeObject(this.prods);
            wy.close();
            wy = new ObjectOutputStream(new FileOutputStream("Attraction.kin"));
            wy.writeObject(this.attrs);
            wy.close();
        }catch(IOException e){
            System.err.println("IO Error: "+e);
        }
    }
    /**
     * Metoda dodająca film do listy filmów(z opisem).
     * @param name tytuł filmu
     * @param genre rodzaj
     * @param rating klasyfikacja
     * @param desc krótki opis filmu
     */
    public void addMovie(String name,String genre,String rating,String desc){        
        this.movies.add(new Movie(name,genre,rating,desc));
    }
    /**
     * Metoda dodająca film do listy filmów(bez opisu).
     * @param name tytuł filmu
     * @param genre rodzaj
     * @param rating klasyfikacja
     */
    public void addMovie(String name,String genre,String rating){        
        this.movies.add(new Movie(name,genre,rating));
    }
    /**
     * Metoda dodająca salę kinową do listy
     * @param id identyfikator sali
     */
    public void addCRoom(int id){
        this.crooms.add(new CRoom(id));
    }
    /**
     * Metoda dodająca seans do listy
     * @param movie obiekt klasy Movie
     * @param time obiekt klasy Time (dokładny czas rozpoczęcia)
     * @param croom obiekt klasy CRoom (sala kinowa)
     */
    public void addShow(Movie movie, Time time, CRoom croom ){
        int prevID;
        if(this.shows.size() == 0) prevID = 0;
        else prevID = this.shows.get(this.shows.size()-1).getID();
        this.shows.add(new Show(movie,croom,time));
        this.shows.get(this.shows.size()-1).setID(prevID+1);
    }    
    /**
     * Metoda dodająca użytkownika
     * @param name nazwa użytkownika
     * @param password hasło
     * @param utype typ(0 - zwykły klient, 1 - użytkownik kasy)
     */
    public void addUser(String name, String password,int utype){
        this.users.add(new User(name,password,utype));
    }
    /**
     * Metoda dodająca produkt
     * @param name przyjazna nazwa
     * @param type typ produktu(jedzenie/picie/zabawki/..)
     * @param price cena produktu
     * @param count ilość dostępnych
     */
    public void addProd(String name, int type, float price, int count){
        //Product(String name, int type, float price, int count)
        int prevID;
        if(this.prods.isEmpty()) prevID = 0;
        else prevID = this.prods.get(this.prods.size()-1).getId();
        this.prods.add(new Product(name,type,price,count));
        this.prods.get(this.prods.size() - 1).setId(prevID + 1);
    }
    /**
     * Metoda dodająca atrakcje
     * @param name przyjazna nazwa
     * @param price cena
     */
    public void addAttr(String name, float price){
        int prevID;
        if(this.attrs.isEmpty()) prevID = 0;
        else prevID = this.attrs.get(this.attrs.size()-1).getId();
        this.attrs.add(new Attraction(name, price));
        this.attrs.get(this.attrs.size() - 1).setId(prevID + 1);
    }
    /**
     * Metoda usuwająca film o danym identyfikatorze
     * @param n identyfikator filmu
     * @return -1 gdy nie ma filmu o danym ID, 0 - gdy usunięto pomyślnie
     */
    public int delMovie(int n){
        if(n>=this.movies.size()) return -1;
        this.movies.remove(n);
        return 0;
    }
    /**
     * Metoda usuwająca salę kinową o danym identyfikatorze
     * @param n identyfikator sali
     * @return -1 gdy nie ma sali o danym ID, 0 - gdy usunięto pomyślnie
     */
    public int delCRoom(int n){
        if(n>=this.crooms.size()) return -1;
        this.crooms.remove(n);
        return 0;
    }
    /**
     * Metoda usuwająca seans o danym identyfikatorze
     * @param n identyfikator seansu
     * @return -1 gdy nie ma seansu o danym ID, 0 - gdy usunięto pomyślnie
     */
    public int delShow(int n){
        if(n>=this.shows.size()) return -1;
        this.shows.remove(n);
        return 0;
    }
    /**
     * Metoda usuwająca użytkownia o danym identyfikatorze
     * @param n identyfikator użytkownika
     * @return -1 gdy nie ma użytkownika o danym ID, 0 - gdy usunięto pomyślnie
     */
    public int delUser(int n){
        if(n>=this.users.size()) return -1;
        this.users.remove(n);
        return 0;
    }
    
    public int delProduct(int n){
        if(n>=this.prods.size()) return -1;
        this.prods.remove(n);
        return 0;
    }
    
    public int delAttr(int n){
        if(n>=this.attrs.size()) return -1;
        this.attrs.remove(n);
        return 0;
    }
    /**
     * Metoda zwracająca listę filmów w postaci tablicy
     * @return tablica filmów Movie[]
     */
    public Movie[] getMovies(){
        //Integer[] ints = list.toArray(new Integer[]{});
        Movie[] movs = this.movies.toArray(new Movie[]{});
        return movs;
    }
    /**
     * Metoda zwracająca listę sal kinowych w postaci tablicy
     * @return tablica sal kinowych CRoom[]
     */
    public CRoom[] getCRooms(){
        CRoom[] crs = this.crooms.toArray(new CRoom[]{});
        return crs;
    }
    /**
     * Metoda zwracająca listę seansów w postaci tablicy
     * @return tablica seansów Show[]
     */
    public Show[] getShows(){
        Show[] shs = this.shows.toArray(new Show[]{});
        return shs;
    }
    /**
     * Metoda zwracająca listę użytkowników w postaci tablicy
     * @return tablica użytkowników User[]
     */
    public User[] getUsers(){
        User[] usrs = this.users.toArray(new User[]{});
        return usrs;
    }
    /**
     * Metoda wypisująca na standardowe wyjście listę filmów.
     */
    public void listMovies(){
        for(int i=0;i<this.movies.size();i++){
            System.out.println(i+". "+this.movies.get(i).getName()+" Gen:"+this.movies.get(i).getGenre()+" Rat:"+this.movies.get(i).getRating());
        }
    }
    /**
     * Metoda wypisująca na standardowe wyjście listę sal kinowych.
     */
    public void listCRooms(){
        for(int i=0;i<this.crooms.size();i++){
            System.out.println(i+". "+this.crooms.get(i).getID());
        }
    }
    /**
     * Metoda wypisująca na standardowe wyjście listę seansów.
     */
    public void listShows(){
        int i = 0;
        for(Show s : shows){
            System.out.println(i+". "+s.getMovie().getName()+" Room: "+s.getRoom().getID()+" Time: "+s.getFormatted());
            i++;
        }
    }
    /**
     * Metoda wypisująca na standardowe wyjście listę użytkowników.
     */
    public void listUsers(){
        for(int i=0;i<this.users.size();i++){
            System.out.println(i+". "+this.users.get(i).getName()+"  Type: "+this.users.get(i).getUType());
        }
    }
    /**
     * Metoda wypisująca na standardowe wyjście listę aktualnych rezerwacji.
     */
    public void listRess(){
        try{
            int i = 0;
            for(Res r : ress){
                System.out.println(i+". "+r.getName()+" ShowID:"+r.getShowID()+" Seats: ");
                for(int[] s : r.getSeats()){
                    System.out.println("*"+s[0]+"-"+s[1]);
                }
                System.out.println("isok: "+r.isok());
                i++;
            }
        }catch(NullPointerException e){
            System.out.println("No reservations!");
        }
    }
    
    public String getFormattedRes(){
        String res = "";
        try{
            int i = 0;
            for(Res r : ress){
                res += (i+". Name: "+r.getName()+" ShowID:"+r.getShowID()+" Seats: \n");
                for(int[] s : r.getSeats()){
                    res += ("*"+s[0]+"-"+s[1]+"\n");
                }
                res += ("isok: "+r.isok()+"\n");
                i++;
            }
        }catch(NullPointerException e){
            res += ("No reservations!\n");
        }
        return res;
    }
    /**
     * Metoda zwracająca obiekt klasy Time z czasem danego filmu
     * @return obiekt klasy Time
     */
    public Time getTime(){
        return time;
    }
    
    public Product[] getProds(){
        return this.prods.toArray(new Product[]{});
    }
    
    public Attraction[] getAttrs(){
        return this.attrs.toArray(new Attraction[]{});
    }
    
    public void saveMovToSQL(int sel){
        Movie m = this.movies.get(sel);
        new DBConnector().save(m);
    }
}
