/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak.beans;
import java.io.Serializable;
import java.util.Formatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;
import org.jdom2.Element;


/**
 * Klasa reprezentująca użytkownika
 * @author qbass
 */
public class User implements Serializable{
    private static final long serialVersionUID = 2L;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUtype(int utype) {
        this.utype = utype;
    }

    public void setAvailcmds(int[] availcmds) {
        this.availcmds = availcmds;
    }

    public User() {
    }
    private String name;
    private String password;
    private int utype;
    private int[] availcmds;
    
    public Element toXML(){
        Element res = new Element("User");
        res.setAttribute("id", String.valueOf(this.id));
        res.addContent(new Element("name").setText(String.valueOf(this.name)));
        res.addContent(new Element("password").setText(String.valueOf(this.getPass())));
        res.addContent(new Element("utype").setText(String.valueOf(this.utype)));
        return res;
    }
    
    public int getId(){
        return this.id;
    }
    
    public User(Element node){
        if(!node.getName().equals("User")){
//            throw new RuntimeException("Wrong element type");
            System.out.println("Wrong element type: User, got: "+node.getName());
        }
        
        this.name = node.getChildText("name");
        this.password = node.getChildText("password");
        this.utype = Integer.valueOf(node.getChildText("utype"));
    }
    
    /**
     * Utworzenie instancji klasy użytkownika o podanych parametrach i ustawiająca dostępne komendy
     * @param name nazwa użytkownika
     * @param password hasło w czystej postaci
     * @param utype type(0-klient,1-kasjer)
     */
    public User(String name, String password,int utype){
        this.name = name;
        String tmp = password;
        this.password = tmp;//toSHA1(tmp.getBytes());        
        this.utype = utype;
        this.availcmds = new int[12];
        this.setCmds();
    }
    
    public User(int id,String name, String password,int utype){
        this.name = name;
        String tmp = password;
        this.password = tmp;//toSHA1(tmp.getBytes());        
        this.utype = utype;
        this.availcmds = new int[12];
        this.setCmds();
        this.id = id;
    }
    /**
     * Utworzenie instancji klasy użytkownika na potrzeby logowania klienta
     * @param name nazwa użytkownika
     * @param password hasło w czystej postaci
     */
    public User(String name, String password){
        this.name = name;
        String tmp = password;
        this.password = tmp;//toSHA1(tmp.getBytes());      
    }
    /**
     * Przesłoniona metoda equals klasy Object, dla pominięcia sprawdzania typu użytkownika i jego uprawnień
     * Wymagane przy sprawdzaniu poprawności logowania przez Serwer
     * @param obj Obiekt klasy User do porównania
     * @return true jeśli obiekt jest taki sam, false jeśli nie
     */
    @Override
    public boolean equals(Object obj){
        boolean isEqual = false;
        if (this.getClass() == obj.getClass())
        {
            User res = (User) obj;
            if ((res.name).equals(this.name) && (res.password).equals(this.password)) {
                System.out.println("equals");
                isEqual = true;
            }
        } 
        return isEqual;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.password);
        return hash;
    }
    
    /**
     * Metoda zwracająca dostępne komendy
     * @return tablica int[] z dostępnymi komendami dla danego użytkownika
     */
    public int[] getACmds(){
        return this.availcmds;
    }    
    /**
     * Metoda zwracająca nazwę użytkownika
     * @return nazwa użytkownika
     */
    public String getName(){
        return this.name;
    }
    /**
     * Metoda zwracająca hasło zahashowane algorytmem SHA1
     * @return ciąg SHA1 
     */
    public String getPass(){
//        return toSHA1(this.password.getBytes());
        return this.password;
    }
    /**
     * Metoda zwracająca typ użytkownika
     * @return typ użytkownika
     */
    public int getUType(){
        return this.utype;
    }
    /**
     * Metoda ustawiająca tablicę z dostępnymi komendami dla danego użytkownika.
     */
    private void setCmds(){
        for(int i=0;i<12;i++){
            this.availcmds[i] = 0;
        }
        switch(this.utype){
            case 0 : { //Client
                this.availcmds[0] = 5;
                break;
            }
            case 1 : { //User
                this.availcmds[0] = 5;
                this.availcmds[1] = 6;
                this.availcmds[2] = 7;
                this.availcmds[3] = 8;
                this.availcmds[4] = 9;
                this.availcmds[5] = 10;
                this.availcmds[6] = 4;
                this.availcmds[7] = 11;
                this.availcmds[8] = 12;
                this.availcmds[9] = 13;
                this.availcmds[10] = 14;
                break;
            }
            case 2 : { //SuperUser/Admin
                this.availcmds[0] = 1;
                this.availcmds[1] = 2;
                this.availcmds[2] = 3;
                this.availcmds[3] = 4;
                this.availcmds[4] = 5;
                this.availcmds[5] = 6;
                this.availcmds[6] = 7;
                this.availcmds[7] = 8;
                this.availcmds[8] = 9;
                this.availcmds[9] = 10;
                this.availcmds[10] = 666;
                this.availcmds[11] = 667;
                break;
            }
        }
    }
    /**
     * Metoda hashująca hasło algorytmem SHA1
     * @param pass hasło w czystej postaci
     * @return hasło w SHA1
     */
    private String toSHA1(byte[] pass) {
        MessageDigest md;
        try{
            md = MessageDigest.getInstance("SHA-1");
        }catch(NoSuchAlgorithmException e){
            System.err.println("No Such Algorithm Exception: "+e);
            return null;
        }
        return byteToHex(md.digest(pass));
    }
    /**
     * Metoda przekształcająca hash SHA1 z postaci Binarnej na Hexadecymalną
     * @param hash hash w postaci binarnej
     * @return hash w postaci hexadecymalnej
     */
    private String byteToHex(byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    
    public String checkUser(ArrayList<User> usrs){
        for(User u : usrs){
            if(u.equals(this)){
                this.id = u.getId();
                this.utype = u.getUType();
                return "OK";
            }            
        }
        this.logout();
        return "ERR";
    }

    public String getPassword() {
        return password;
    }

    public int getUtype() {
        return utype;
    }
    
    public void logout(){
        this.name = null;
        this.password = null;
        this.id = -1;
        this.utype = -1;
    }
    
}
