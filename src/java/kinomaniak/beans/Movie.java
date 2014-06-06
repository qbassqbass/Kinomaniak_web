/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak.beans;

import java.io.Serializable;
import org.jdom2.Element;

/**
 * Klasa reprezentująca film
 * @author qbass
 */
public class Movie implements Serializable{

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name;
    private String genre;
    private String rating;
    private String desc;
    
    public int getId(){
        return this.id;
    }
    
    public Movie(){
        
    }
    
    public Element toXML(){
        Element res = new Element("Movie");
        res.setAttribute("id", String.valueOf(this.id));
        res.addContent(new Element("name").setText(String.valueOf(this.name)));
        res.addContent(new Element("genre").setText(String.valueOf(this.genre)));
        res.addContent(new Element("rating").setText(String.valueOf(this.rating)));
        res.addContent(new Element("desc").setText(String.valueOf(this.desc)));
        return res;
    }
    
    public Movie(Element node){
        if(!node.getName().equals("Movie")){
//            throw new RuntimeException("Wrong element type");
            System.out.println("Wrong element type: Movie, got: "+node.getName());
        }
        
        this.name = node.getChildText("name");
        this.genre = node.getChildText("genre");
        this.rating = node.getChildText("rating");
        this.desc = node.getChildText("desc");
    }
    
    /**
     * Konstruktor klasy filmowej bez opisu
     * @param name tytuł filmu
     * @param genre rodzaj filmu
     * @param rating klasyfikacja filmu
     */
    public Movie(String name,String genre,String rating){
        this.name = name;
        this.genre = genre;
        this.rating = rating;
        this.desc = null;
    }
    /**
     * Konstruktor klasy filmowej z opisem
     * @param name tytuł filmu
     * @param genre rodzaj filmu
     * @param rating klasyfikacja filmu
     * @param desc opis filmu
     */
    public Movie(String name,String genre,String rating,String desc){
        this.name = name;
        this.genre = genre;
        this.rating = rating;
        this.desc = desc;
    }
    
    public Movie(int id, String name,String genre,String rating){
        this.name = name;
        this.genre = genre;
        this.rating = rating;
        this.desc = null;
        this.id = id;
    }
    /**
     * Konstruktor klasy filmowej z opisem
     * @param name tytuł filmu
     * @param genre rodzaj filmu
     * @param rating klasyfikacja filmu
     * @param desc opis filmu
     */
    public Movie(int id, String name,String genre,String rating,String desc){
        this.name = name;
        this.genre = genre;
        this.rating = rating;
        this.desc = desc;
        this.id = id;
    }
    /**
     * Metoda zwracająca tytuł filmu
     * @return tytuł filmu
     */
    public String getName(){
        return this.name;
    }
    /**
     * Metoda zwracająca rodzaj filmu
     * @return rodzaj filmu
     */
    public String getGenre(){
        return this.genre;
    }
    /**
     * Metoda zwracająca klasyfikację filmu
     * @return klasyfikacja filmu
     */
    public String getRating(){
        return this.rating;
    }
    /**
     * Metoda zwracająca krótki opis filmu
     * @return opis filmu
     */
    public String getDesc(){
        return this.desc;
    }
    
    @Override
    public String toString(){
        return this.id+"|"+this.name+"|"+this.genre+"|"+this.rating+"|"+this.desc;
    }
}
