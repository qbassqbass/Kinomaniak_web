/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kinomaniak_database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import kinomaniak_objs.*;

/**
 *
 * @author Qbass
 */
public class Parser {
    
    public boolean save(Connection conn, Object obj){
        boolean done = false;
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(this.save(obj));            
            statement.close();
            done = true;
        } catch (SQLException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return done;
    }
    
    public ArrayList<Object> load(Connection conn, String type){
        return this.load(conn,type,-1);
    }
    /**
     *
     * @param conn
     * @param type
     * @param id
     * @return
     */
    public ArrayList<Object> load(Connection conn, String type, int id){
        ArrayList<Object> arr = new ArrayList<Object>();
        Object obj = null;
        ResultSet result;
        try {
            Statement statement = conn.createStatement();
            if(id==-1)
                result = statement.executeQuery(this.load(type));
            else
                result = statement.executeQuery(this.load(type, id));
            switch(type){
                case "Movie":
                    while(result.next()){
                        obj = new Movie(result.getInt("id"),result.getString("name"),result.getString("genere"),result.getString("rating"),result.getString("descr"));
                        arr.add(obj);
                    }
                    break;
                case "CRoom":
                    while(result.next()){
                        obj = new CRoom(result.getInt("id"));
                        arr.add(obj);
                    }
                    break;
                case "Attraction":
                    while(result.next()){
                        int idd = result.getInt("id");
                        String name = result.getString("name");
                        float price = result.getFloat("price");
                        obj = new Attraction(idd, name, price);
                        arr.add(obj);
                    }
                    break;
                case "GoldCard":
//                    cannot do anything... -.-
                    break;
                case "Product":
                    while(result.next()){
                        obj = new Product(result.getInt("id"), result.getString("name"), result.getInt("typ"), result.getFloat("price"), result.getInt("pcount"));
                        arr.add(obj);
                    }
                    break;
                case "Report":
//                    cannot do anything... -.-
                    break;
                case "Res":
                    while(result.next()){                        
                        int idres = result.getInt("id");
                        String nameres = result.getString("imienazwisko");
                        int showid = result.getInt("showid");
                        boolean checked = result.getBoolean("checked");
                        boolean ok = result.getBoolean("ok");
                        String seat = result.getString("seat");
//                        obj = new Res()
                        String seatString[] = seat.split(",");
                        int seats[][] = new int[seatString.length][2];
                        int i = 0;
                        for(String s : seatString){
                            seats[i][0] = Integer.valueOf(s.split(":")[0]);
                            seats[i][1] = Integer.valueOf(s.split(":")[1]);
                            i++;
                        }
                        obj = new Res(idres, nameres, showid, seatString.length, seats);
                        if(checked)((Res)obj).accept();
                        if(ok)((Res)obj).get();
                        arr.add(obj);
                    }
                    break;
                case "Show":
                    while(result.next()){
                        ArrayList<Object> movies = this.load(conn, "Movie");
                        ArrayList<Object> rooms = this.load(conn, "CRoom");
                        ArrayList<Object> times = this.load(conn, "Time");
                        int showid = result.getInt("id");
                        int movid = result.getInt("mov");
                        int roomid = result.getInt("room");
                        int timeid = result.getInt("timeid");
                        movies = this.load(conn, "Movie", movid);
                        rooms = this.load(conn, "CRoom", roomid);
                        times = this.load(conn, "Time", timeid);
                        obj = new Show(showid, (Movie)movies.get(0), (CRoom)rooms.get(0), (Time)times.get(0));
                        arr.add(obj);
//                        int i = 0;
//                        for(Object m : movies){
//                            Movie mm;
//                            if(m instanceof Movie) mm = (Movie)m;
//                            else return null;
//                            if(mm.getId() == movid){
//                                movid = i;
//                                break;
//                            }
//                            i++;
//                        }
//                        i = 0;
//                        for(Object r : rooms){
//                            CRoom cr;
//                            if(r instanceof CRoom) cr = (CRoom)r;
//                            else return null;
//                            if(cr.getID() == roomid){
//                                roomid = i;
//                                break;
//                            }
//                            i++;
//                        }
//                        i = 0;
//                        for(Object t : times){
//                            Time tm;
//                            if(t instanceof Time) tm = (Time)t;
//                            else return null;
////                            if(tm.get)
//                        }
//                        obj = new Show((Movie)movies.get(movid), (CRoom)rooms.get(roomid), result.getInt("timeid"));
                    }
                    break;
                case "Ticket":
//                    cannot do anything... -.-
                    break;
                case "User":
//                    cannot do anything... -.-
                    break;
                case "Time":
                    while(result.next()){
                        obj = new Time(result.getInt("id"), result.getInt("thour"), result.getInt("tminute"),
                                        result.getByte("tday"), result.getInt("tmonth"), result.getInt("tyear"));
                        arr.add(obj);
                    }
                    break;
                default:                    
//                    cannot do anything... -.-
            }
        } catch (SQLException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }
    
    public String save(Object obj){
        String query = "";
        if(obj instanceof Movie){
            Movie mov = (Movie) obj;
            query = "INSERT INTO Movie VALUES (NULL, '" + mov.getName() +"', '" + mov.getGenre() + "', '" + mov.getRating() + "', '" + mov.getDesc() + "');";
        }else if(obj instanceof CRoom){
            CRoom cr = (CRoom) obj;
            query = "INSERT INTO Croom VALUES (NULL, '" + cr.getID() + "');";
        }else if(obj instanceof Attraction){
            Attraction at = (Attraction) obj;
            query = "INSERT INTO Attraction VALUES (NULL, '" + at.getName() + "', '" + at.getPrice() + "');";
        }else if(obj instanceof Product){
            Product pr = (Product) obj;
            query = "INSERT INTO Product VALUES (NULL, '" + pr.getName() + "', '" + pr.getType() + "', '" + pr.getPrice() + "', '" + pr.getCount() + ",);";
        }else if(obj instanceof Report){
            Report rep = (Report) obj;
            
        }else if(obj instanceof Res){
            Res res = (Res) obj;
            query = "INSERT INTO Res VALUES (NULL, '" + res.getName() + "', '" + res.getShowID() + "', '" + res.formatSeatsSQL()+ "', '" + res.ischecked() + "', '" + res.isok() + "');";
        }else if(obj instanceof Show){
            Show sh = (Show) obj;
            query = "INSERT INTO Show VALUES (NULL, '" + sh.getID() + "', '" + sh.getMovie().getId() + "', '" + sh.getRoom().getID() + "', '" + sh.getFormatted() + "');";
        }else if(obj instanceof Ticket){
            Ticket tick = (Ticket) obj;
            
        }else if(obj instanceof Time){
            Time tim = (Time) obj;
            // will not be implemented
        }else if(obj instanceof User){
            User usr = (User) obj;
            query = "INSERT INTO User VALUES (NULL, '" + usr.getName() + "', '" + usr.getPass() + "', '" + usr.getUType() + "');";
        }else if(obj instanceof GoldCard){
            GoldCard gc = (GoldCard) obj;
            
        }
        return query;
    }
    
    public String load(String type, int id){
        String query = "";
        query = load(type)+" WHERE id="+id;
        return query;
    }
    
    public String load(String type){
        Object obj = null;
        String query = "";
        switch(type){
            case "Movie":
                query = "SELECT * FROM Movie";
                break;
            case "CRoom":
                query = "SELECT * FROM CRoom";
                break;
            case "Attraction":
                query = "SELECT * FROM Attraction";
                break;
            case "GoldCard":
                query = "SELECT * FROM Goldcard";
                break;
            case "Product":
                query = "SELECT * FROM Product";
                break;
            case "Report":
                query = "SELECT * FROM Report";
                break;
            case "Res":
                query = "SELECT * FROM Res";
                break;
            case "Show":
                query = "SELECT * FROM Shows";
                break;
            case "Ticket":
                query = "SELECT * FROM Ticket";
                break;
            case "User":
                query = "SELECT * FROM User";
                break;
            case "Time":
                query = "SELECT * FROM TimeDate";
                break;
            default:
                query = "SELECT * FROM Dummy";
        }
        return query;
    }
}
