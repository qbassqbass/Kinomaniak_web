/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak.beans;

import kinomaniak_objs.*;
import java.io.Serializable;
import org.jdom2.Element;
import org.jdom2.Attribute;
import javax.faces.bean.ManagedBean;

/**
 * Klasa reprezentująca salę kinową
 * @author qbass
 */
@ManagedBean
public class CRoom implements Serializable {

    public CRoom() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public boolean[][] getSeats() {
        return seats;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSeats(boolean[][] seats) {
        this.seats = seats;
    }
    
    private static final long serialVersionUID = 1L;
    private int id;
    private boolean[][] seats;
    
    public Element toXML(){
        Element res = new Element("CRoom");
        res.addContent(new Element("id").setText(String.valueOf(this.id)));
        return res;
    }
    
    public CRoom(Element node){
        if(!node.getName().equals("CRoom")){
//            throw new RuntimeException("Wrong element type");
            System.out.println("Wrong element type: CRoom, got: "+node.getName());
        }
        
        this.id = Integer.valueOf(node.getChildText("id"));
    }
    
    /**
     * Konstruktor klasy Sali kinowej
     * @param id identyfikator sali kinowej
     */
    public CRoom(int id){
        this.id = id;
        seats = new boolean[10][10];
    }
    /**
     * Metoda zwracająca ID sali kinowej
     * @return identyfikator sali kinowej
     */
    public int getID(){
        return this.id;
    }
}
