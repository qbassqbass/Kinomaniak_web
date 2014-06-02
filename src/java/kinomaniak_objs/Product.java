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
public class Product implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name;
    private int type;
    private int count;
    private float price;
    
    public Element toXML(){
        Element res = new Element("Product");
        res.setAttribute("id", String.valueOf(this.id));
        res.addContent(new Element("name").setText(String.valueOf(this.name)));
        res.addContent(new Element("type").setText(String.valueOf(this.type)));
        res.addContent(new Element("count").setText(String.valueOf(this.count)));
        res.addContent(new Element("price").setText(String.valueOf(this.price)));        
        return res;
    }
    
    public Product(Element node){
        if(!node.getName().equals("Product")){
//            throw new RuntimeException("Wrong element type");
            System.out.println("Wrong element type: Product, got: "+node.getName());
        }
        
        this.name = node.getChildText("name");
        this.type = Integer.valueOf(node.getChildText("type"));
        this.count = Integer.valueOf(node.getChildText("count"));
        this.price = Float.valueOf(node.getChildText("price"));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public float getPrice() {
        return price;
    }
    
    public int getCount(){
        return this.count;
    }
    
    public Product(String name, int type, float price){
        this.name = name;
        this.type = type;
        this.price = price;
        this.count = 1;
        this.id = this.getLastId() + 1;
    }
    
    public Product(String name, int type, float price, int count){
        this.name = name;
        this.type = type;
        this.price = price;
        this.count = count;
        this.id = this.getLastId() + 1;
    }
    
    public boolean buy(){
        if(this.count > 0){
            this.count--;
            return true;
        }else return false;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    
    private int getLastId(){
        int tmp = -1;
        
        return tmp;
    }
}
