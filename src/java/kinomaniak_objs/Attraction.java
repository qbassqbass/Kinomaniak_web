/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kinomaniak_objs;

import java.io.Serializable;
import org.jdom2.Element;

/**
 *
 * @author Qbass
 */
public class Attraction implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name;
    private float price;

    public Element toXML(){
        Element res = new Element("Attraction");
        res.setAttribute("id", String.valueOf(this.id));
        res.addContent(new Element("name").setText(String.valueOf(this.name)));
        res.addContent(new Element("price").setText(String.valueOf(this.price)));        
        return res;
    }
    
    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }
    
    public int getId(){
        return this.id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public Attraction(String name, float price){
        this.name = name;
        this.price = price;
        this.id = this.getLastId() + 1;
    }
    
    public Attraction(int id, String name, float price){
        this.name = name;
        this.price = price;
        this.id = id;
        
    }
    
    private int getLastId(){
        int tmp = -1;
        
        return tmp;
    }
}
