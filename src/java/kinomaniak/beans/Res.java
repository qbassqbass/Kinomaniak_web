/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak.beans;

import kinomaniak_objs.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.jdom2.Element;
import javax.faces.bean.ManagedBean;

/**
 * Klasa reprezentująca rezerwację seansu
 * @author qbass
 */
@ManagedBean
public class Res implements Serializable{
    
    private static final long serialVersionUID = 4L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getImienazwisko() {
        return imienazwisko;
    }

    public int getShowid() {
        return showid;
    }

    public int[][] getSeat() {
        return seat;
    }

    public boolean isChecked() {
        return checked;
    }

    public boolean isOk() {
        return ok;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImienazwisko(String imienazwisko) {
        this.imienazwisko = imienazwisko;
    }

    public void setShowid(int showid) {
        this.showid = showid;
    }

    public void setSeat(int[][] seat) {
        this.seat = seat;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public Res() {
    }
   
    private int id;
    private String imienazwisko;
    private int showid;
    private int[][] seat;
    private boolean checked; // potwierdzenie rezerwacji
    private boolean ok; // odebrana rezerwacja
    
    public int getId(){
        return this.id;
    }
    
    public Element toXML(){
        Element res = new Element("Res");
        res.addContent(new Element("imienazwisko").setText(String.valueOf(this.imienazwisko)));
        res.addContent(new Element("showid").setText(String.valueOf(this.showid)));
        res.addContent(new Element("checked").setText(String.valueOf(this.checked)));
        res.addContent(new Element("ok").setText(String.valueOf(this.ok)));
        Element seats = new Element("seats");
        seats.setAttribute("count", String.valueOf(this.seat.length));
        for (int[] s : this.seat) {
            Element r = new Element("seat");
            seats.addContent(r);
            r.addContent(new Element("row").setText(String.valueOf(s[0])));
            r.addContent(new Element("col").setText(String.valueOf(s[1])));
        }
        res.addContent(seats);
        return res;
    }
    
    public Res(Element node){
        if(!node.getName().equals("Res")){
//            throw new RuntimeException("Wrong element type");
            System.out.println("Wrong element type: Res, got: "+node.getName());
        }
        
        this.imienazwisko = node.getChildText("imienazwisko");
        this.showid = Integer.valueOf(node.getChildText("showid"));
        this.checked = Boolean.valueOf(node.getChildText("checked"));
        Element ss = node.getChild("seats");
        
        int c = Integer.valueOf(ss.getAttribute("count").getValue());
        this.seat = new int[c][2];
        for(int i = 0; i < c; i++){
//            this.seat[i][0] = 
        }
        int i = 0;
        
//        System.out.println("Seats"+ss.getChildren("seat").size());
        for(Element el : (List<Element>) ss.getChildren("seat")){
            this.seat[i][0] = Integer.valueOf(el.getChildText("row"));
            this.seat[i][1] = Integer.valueOf(el.getChildText("col"));
            i++;
        }
    }
    
    /**
     * Konstruktor klasy Rezerwacji danego seansu dla pojedyńczego miejsca
     * @param nazwa nazwa/nazwisko klienta, który rezerwuje dany seans
     * @param id identyfikator rezerwowanego seansu
     * @param seat tablica dwóch liczb identyfikujących rząd oraz miejsce w danym rzędzie do zarezerwowania
     */
    public Res(String nazwa,int id, int[] seat){
        this.seat = new int[1][2];
        this.imienazwisko = nazwa;
        this.showid = id;
        this.seat[0] = seat;
        this.checked = false;
        this.ok = false;
    }
    /**
     * Konstruktor klasy Rezerwacji danego seansu dla kilku miejsc
     * @param nazwa nazwa/nazwisko klienta, który rezerwuje dany seans
     * @param id identyfikator rezerwowanego seansu
     * @param ilosc ilość miejsc do rezerwacji
     * @param seat tablica dwóch liczb identyfikujących rząd oraz miejsce w danym rzędzie do zarezerwowania dla każdego z 'ilosc' miejsc
     */
    public Res(String nazwa,int id,int ilosc, int[][] seat){
        this.seat = new int[ilosc][2];
        this.imienazwisko = nazwa;
        this.showid = id;
        this.seat = seat;
        this.checked = false;
        this.ok = false;
    }
    
    public Res(int id, String nazwa,int sid, int[] seat){
        this.seat = new int[1][2];
        this.imienazwisko = nazwa;
        this.showid = sid;
        this.seat[0] = seat;
        this.checked = false;
        this.ok = false;
        this.id = id;
    }
    /**
     * Konstruktor klasy Rezerwacji danego seansu dla kilku miejsc
     * @param id identyfikator rezerwacji
     * @param nazwa nazwa/nazwisko klienta, który rezerwuje dany seans
     * @param sid identyfikator rezerwowanego seansu
     * @param ilosc ilość miejsc do rezerwacji
     * @param seat tablica dwóch liczb identyfikujących rząd oraz miejsce w danym rzędzie do zarezerwowania dla każdego z 'ilosc' miejsc
     */
    public Res(int id, String nazwa,int sid,int ilosc, int[][] seat){
        this.seat = new int[ilosc][2];
        this.imienazwisko = nazwa;
        this.showid = sid;
        this.seat = seat;
        this.checked = false;
        this.ok = false;
        this.id = id;
    }
    
    @Override
    public boolean equals(Object obj){
        boolean isEqual = false;
        if (this.getClass() == obj.getClass())
        {
            Res res = (Res) obj;
            if ((res.imienazwisko).equals(this.imienazwisko) && Arrays.deepEquals(this.seat, res.seat) && res.showid == this.showid) {
                System.out.println("equals");
                isEqual = true;
            }
        } 
        return isEqual;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.imienazwisko);
        hash = 59 * hash + this.showid;
        hash = 59 * hash + Arrays.hashCode(this.seat);
        return hash;
    }

    /**
     * akceptacja rezerwacji
     */
    public void accept(){
        this.checked = true;
    }
    /**
     * sprawdzenie czy dana rezerwacja została potwierdzona przez użytkownika
     * @return true jeśli potwierdzona
     */
    public boolean ischecked(){
        return this.checked;
    }
    /**
     * odbiór rezerwacji
     */
    public void get(){
        this.ok = true;
    }
    /**
     * sprawdzenie czy rezerwacja jest już odebrana
     * @return true jeśli rezerwacja została odebrana
     */
    public boolean isok(){
        return this.ok;
    }
    /**
     * Metoda zwracająca nazwę/nazwisko osoby rezerwującej dany seans
     * @return nazwa/nazwisko rezerwującego
     */
    public String getName(){
        return this.imienazwisko;
    }
    /**
     * Metoda zwracająca identyfikator zarezerwowanego seansu
     * @return id seansu
     */
    public int getShowID(){
        return this.showid;
    }
    /**
     * Metoda zwracająca tablicę identyfikującą dokładne miejsca rezerwacji
     * @return int[][] 
     */
    public int[][] getSeats(){
        return this.seat;
    }
    public String formatSeats(){
        String tmp = "";
        for(int s[] : this.getSeats()){
            tmp += "Rząd: "+s[0]+" Miejsce: "+s[1]+"\n";
        }
        return tmp;
    }
    
    public String formatSeatsSQL(){
        String tmp = "";
        for(int s[] : this.getSeats()){
            tmp += s[0] + ":" + s[1] + ",";
        }
//        StringBuilder sb = new StringBuilder(tmp);
//        sb.deleteCharAt(tmp.length()-1);
//        tmp = sb.toString();
        tmp = tmp.substring(0, tmp.length()-1);
        return tmp;
    }
    
}
