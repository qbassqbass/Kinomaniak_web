/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak_server;
import kinomaniak_objs.*;
import java.net.Socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author qbass
 */
public class Server  implements Runnable{
    private Socket sockfd;
    private boolean logged;
    private User luser;
     private PrintWriter out;  //out for data
     private ObjectOutputStream oout; // output for objects
     private BufferedReader in; //in for data
     private ObjectInputStream oin; //input for objects
     private Log logger;
     private String threadName;
    
     /**
      * Konstruktor wątku serwera
     * @param sockfd deskryptor gniazda dla podłączonego klienta
     */
    public Server(Socket sockfd){
        this.sockfd = sockfd;
        try{
            this.out = new PrintWriter(this.sockfd.getOutputStream(),true);  //out for data
            this.oout = new ObjectOutputStream(this.sockfd.getOutputStream()); // output for objects
            this.in = new BufferedReader(new InputStreamReader(this.sockfd.getInputStream())); //in for data
            this.oin = new ObjectInputStream(this.sockfd.getInputStream()); //input for objects
            this.logged = true;
            this.logger = new Log();
            
        }catch(IOException e){
            System.err.println("IOError from "+sockfd.getInetAddress().getHostAddress()+": "+e);
        }
    }
    /**
     * Metoda kończąca dany wątek. Zamyka wszelkie połączenia z klientem.
     * 
     */
    private void endThread(){
        try{
            in.close();
            oin.close();
            out.close();
            oout.close();
            sockfd.close();
            Thread.currentThread().interrupt();
        }catch(IOException e){
            
        }
    }
    /**
     * Główna metoda działania uruchomionego dla klienta wątku.
     */
    @Override
    public void run(){ //todo!
        this.threadName = Thread.currentThread().getName();
        while(!Thread.currentThread().isInterrupted()){
            boolean cmdAvail = false;
            String tmp;
            try{
                this.oout.writeObject((String)"!OK!");
                this.luser = (User)oin.readObject(); // odczyt obiektu użytkownika od klienta        
                boolean uok = checkUser(); // sprawdzenie użytkownika
                if(uok) this.oout.writeObject((String)"!UOK!");
                else{
                    this.logged = false;
                    this.oout.writeObject((String)"!ERROR!");
                    logger.doLog(0,this.threadName+": User login error from "+sockfd.getInetAddress().getHostAddress());
                    endThread();
                    break;
                }
                this.threadName += " as "+this.luser.getName();
                System.out.println(this.threadName+": "+sockfd.getInetAddress().getHostAddress());
                logger.doLog(0,this.threadName+": "+sockfd.getInetAddress().getHostAddress());
                tmp = (String)oin.readObject();
                ObjectInputStream we = new ObjectInputStream(new FileInputStream("Shows.kin"));
                switch (tmp) {
                    case "!GETMOV!":
                        this.oout.writeObject((String)"!OK!");
                        String date = (String)we.readObject();
                        System.out.println("Debug pre");
                        List<Show> ssstmp = (ArrayList<Show>)we.readObject(); 
                        this.oout.writeObject(ssstmp);
                        break;
                    case "!GETMOVDT!":
                        tmp = (String)oin.readObject();
                        String dbDate = (String)we.readObject();
                        if(tmp.equals(dbDate)) this.oout.writeObject((String)"!MOVOK!");
                        else { this.oout.writeObject((String)"!MOVUPD!"); this.oout.writeObject(we); }
                        break;
                }
                while(!tmp.equals("!RDY!")){
                    tmp = (String)oin.readObject();
                }
                this.oout.writeObject((String)"!RDY!");            
                while (this.logged){
                        String data = (String)oin.readObject();
                        switch (data) {
                            case "!CMD!":
//                                jeśli klient wysyła komendę
                                this.oout.writeObject((String)"!OK!");
                                cmdAvail = true;
                                break;
                            case "!OK!":
                                break;
                            default:
                                this.oout.writeObject((String)"!NAVAIL!"); //not available
                                break;
                        }
                        if(cmdAvail){
                                int cmd = (Integer)oin.readObject();
                                if(this.checkGrants(cmd)){
                                    this.processCmd(cmd);
                                }
                                else this.oout.writeObject((String)"!NGRANT!"); //not granted
                        }

                        cmdAvail = false;
                }
                this.endThread();
            }catch(SocketException e){
                System.err.println("Client Disconnected: "+sockfd.getInetAddress().getHostAddress());
                logger.doLog(0,this.threadName+": Client disconnected: "+sockfd.getInetAddress().getHostAddress());
                endThread();
            }catch(EOFException e){
                 System.err.println("Connection closed: "+sockfd.getInetAddress().getHostAddress());
                 logger.doLog(0,this.threadName+": Connection closed: "+sockfd.getInetAddress().getHostAddress());
                 this.endThread();
            }catch(IOException e){
                System.err.println("IOError from "+sockfd.getInetAddress().getHostAddress()+": "+e);
                logger.doLog(0,this.threadName+": IOError from "+sockfd.getInetAddress().getHostAddress()+": "+e);
                this.endThread();
            }catch(ClassNotFoundException e){
                System.err.println("Class not found from "+sockfd.getInetAddress().getHostAddress()+": "+e);
                logger.doLog(0,this.threadName+": Class not found from "+sockfd.getInetAddress().getHostAddress()+": "+e);
            }
        }
    }
    
    /**
     * Metoda sprawdzająca czy zalogowany użytkownik ma prawo do wykonania danej komendy
     * @param cmd identyfikator komendy 
     * @return true - jeśli dany użytkownik ma uprawnienia do wykonania, false - jeśli nie
     */
    public boolean checkGrants(int cmd){
        if(cmd < 0) return true;
        for(int i=0;i<12;i++){
            if(cmd == luser.getACmds()[i]){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Metoda wykonująca komendę podaną przez użytkownika
     * @param cmd identyfikator komendy
     */
    public void processCmd(int cmd){
        
                System.err.println("DEBUG::CMD:"+cmd);
        switch(cmd){
            case -1 : {
                this.logged = false;
                break;
            }
            case 1 :{
                
                break;
            }
            case 2:{
                
                break;
            }
            case 3 :{
                
                break;
            }
            case 4:{ // pobierz liste rezerwacji
                try{
                    this.oout.writeObject((String)"!GDATA!");
                    String tmp = (String)oin.readObject();           
                    List<Res> reslist = new ArrayList<Res>();
                    if(tmp.equals("!OK!")){
                        File r = new File("Res.kin");
                            if(!r.exists()){
                                this.oout.writeObject((String)"!NORES!");
                                break;
                            }else{
                                this.oout.writeObject((String)"!OKRES!");
                            }
                         synchronized(this){
                            ObjectInputStream we = new ObjectInputStream(new FileInputStream("Res.kin"));
                            reslist = (ArrayList<Res>)we.readObject();
                            we.close();
                         }
                         Res restmp[] = new Res[reslist.size()];
                         restmp = reslist.toArray(restmp);
                         this.oout.writeObject((Res[])restmp);
                        logger.doLog(1, "SendRes to "+this.threadName);
                    }
                }catch(SocketException e){
                    System.err.println("Client Disconnected: "+sockfd.getInetAddress().getHostAddress());
                    logger.doLog(0,this.threadName+": Client disconnected: "+sockfd.getInetAddress().getHostAddress());
                    endThread();
                }catch(IOException e){
                    System.err.println("IO Error: "+e);
                    logger.doLog(0,this.threadName+": IO Error from "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
                }catch(ClassNotFoundException e){
                    System.err.println("Class not found :"+e);
                    logger.doLog(0,this.threadName+": Class not found from "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
                }
                break;
            }
            case 5:{ // rezerwacja biletu
                try{
                    this.oout.writeObject((String)"!GDATA!");
                    String tmp = (String)oin.readObject();
                    if(tmp.equals("!OK!"))
                        this.oout.writeObject((String)"!NAZW!");
                        String nazwa = (String)oin.readObject();
                        this.oout.writeObject((String)"!SEANS!");
                        int showid = (Integer)oin.readObject();
                        int[][] seat = (int[][])oin.readObject();
//                        boolean seatok = true;
//                        for(int s[] : seat){
//                            if ((s[0] < 0) || (s[0] > 10) || (s[1] < 0) || (s[1] > 10)){
//                                seatok = false;
//                            }
//                        }
////                        TODO
//                        if(!seatok){
//                            this.oout.writeObject((String)"!SNOK!");
//                            break;
//                        }
////                        TODO
//                        this.oout.writeObject((String)"!SOK!");                            
                        Res res = new Res(nazwa,showid,seat.length,seat);
                        System.out.println("DEBUG::SHOWID: "+showid);
                    synchronized (this){
                        try{
                            File r = new File("Res.kin");
                            Res ares[];
                            List<Res> reslist = new ArrayList<Res>();
                            int num = 0;
                            if(!r.exists()){
                                r.createNewFile();
                                reslist.add(res);
                            }else{
                                ObjectInputStream we = new ObjectInputStream(new FileInputStream("Res.kin"));
                                reslist = (ArrayList<Res>)we.readObject();
                                we.close();
                                reslist.add(res);
                            }
                            ObjectOutputStream wy = new ObjectOutputStream(new FileOutputStream("Res.kin"));
                            wy.writeObject(reslist);
                            wy.close();
                        }catch(IOException e){
                            System.err.println("IO Error from "+sockfd.getInetAddress().getHostAddress()+": "+e);
                            logger.doLog(0,this.threadName+": IO Error from "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
                        }
                    }
                    logger.doLog(1,"Res: "+res.getName()+" ShowID: "+res.getShowID()+" SeatCount: "+res.getSeats().length);
                }catch(SocketException e){
                    System.err.println("Client Disconnected: "+sockfd.getInetAddress().getHostAddress());
                    logger.doLog(0,this.threadName+": Client disconnected: "+sockfd.getInetAddress().getHostAddress());
                    endThread();
                }catch(IOException e){
                    System.err.println("IO Error: "+e);
                    logger.doLog(0,this.threadName+": IO Error from "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
                }catch(ClassNotFoundException e){
                    System.err.println("Class not found :"+e);
                    logger.doLog(0,this.threadName+": Class not found from "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
                }
                break;
            }
            case 6:{// potwierdzenie rezerwacji
                try{
                    this.oout.writeObject((String)"!GDATA!");
                    String tmp = (String)oin.readObject();           
                    List<Res> reslist = new ArrayList<Res>();
                    if(tmp.equals("!OK!")){
                        File r = new File("Res.kin");
                            if(!r.exists()){
                                this.oout.writeObject((String)"!NORES!");
                                break;
                            }else{
                                this.oout.writeObject((String)"!OKRES!");
                            }
                         synchronized(this){
                            ObjectInputStream we = new ObjectInputStream(new FileInputStream("Res.kin"));
                            reslist = (ArrayList<Res>)we.readObject();
                            we.close();
                         }
                         Res restmp[] = new Res[reslist.size()];
                         restmp = reslist.toArray(restmp);
                         this.oout.writeObject((Res[])restmp);
                        this.oout.writeObject((String)"!GORES!"); // Get Object Res
                        Res res = (Res)oin.readObject();
                        synchronized(this){
                            boolean acc = false;
                            for(int i =0;i<reslist.size();i++){
                                if(reslist.get(i).equals(res)){
                                    reslist.get(i).accept();
                                    acc = true;
                                    break;
                                }
                            }
                            if(acc){
                                ObjectOutputStream wy = new ObjectOutputStream(new FileOutputStream("Res.kin"));
                                wy.writeObject(reslist);
                                wy.close();
                                this.oout.writeObject((String)"!OK!");
                            }else this.oout.writeObject((String)"!NORES!");
                        }
                        logger.doLog(1, "AccRes: "+res.getName()+" ShowID:"+res.getShowID()+" SeatsCount:"+res.getSeats().length);
                    }
                }catch(SocketException e){
                    System.err.println("Client Disconnected: "+sockfd.getInetAddress().getHostAddress());
                    logger.doLog(0,this.threadName+": Client disconnected: "+sockfd.getInetAddress().getHostAddress());
                    endThread();
                }catch(IOException e){
                    System.err.println("IO Error: "+e);
                    logger.doLog(0,this.threadName+": IO Error from "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
                }catch(ClassNotFoundException e){
                    System.err.println("Class not found :"+e);
                    logger.doLog(0,this.threadName+": Class not found from "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
                }
                break;
            }
            case 7 :{ // odbiór rezerwacji
                try{
                    this.oout.writeObject((String)"!GDATA!");
                    String tmp = (String)oin.readObject();
                    List<Res> reslist = new ArrayList<Res>();
                    if(tmp.equals("!OK!")){
                        File r = new File("Res.kin");
                            if(!r.exists()){
                                this.oout.writeObject((String)"!NORES!");
                                break;
                            }else{
                                this.oout.writeObject((String)"!OKRES!");
                            }
                        synchronized(this){
                            ObjectInputStream we = new ObjectInputStream(new FileInputStream("Res.kin"));
                            reslist = (ArrayList<Res>)we.readObject();
                            we.close();
                         }
                        Res restmp[] = new Res[reslist.size()];
                        restmp = reslist.toArray(restmp);
                        this.oout.writeObject((Res[])restmp);
                        this.oout.writeObject((String)"!GORES!"); // Get Object Res
                        Res res = (Res)oin.readObject();
                        synchronized(this){                            
                            boolean ok = false;
                            System.out.println("Checking");
                            for(int i = 0;i<reslist.size();i++){
                                if(reslist.get(i).equals(res)){
                                    reslist.get(i).get();
                                    System.out.println(i);
                                    ok = true;
                                    break;
                                }
                            }
                            if(ok){
                                ObjectOutputStream wy = new ObjectOutputStream(new FileOutputStream("Res.kin"));
                                wy.writeObject(reslist);
                                wy.close();                                
                                this.oout.writeObject((String)"!OK!");
                            }else this.oout.writeObject((String)"!NORES!");
                        }
                        logger.doLog(1, "GetRes: "+res.getName()+" ShowID:"+res.getShowID()+" SeatsCount:"+res.getSeats().length);
                    }
                }catch(SocketException e){
                    System.err.println("Client Disconnected: "+sockfd.getInetAddress().getHostAddress());
                    logger.doLog(0,this.threadName+": Client disconnected: "+sockfd.getInetAddress().getHostAddress());
                    endThread();
                }catch(IOException e){
                    System.err.println("IO Error: "+e);
                    e.printStackTrace();
                    logger.doLog(0,this.threadName+": IO Error from "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
                }catch(ClassNotFoundException e){
                    System.err.println("Class not found :"+e);
                    logger.doLog(0,this.threadName+": Class not found from "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
                }
                break;
            }
            case 8:{ // anulowanie rezerwacji
                try{
                    this.oout.writeObject((String)"!GDATA!");
                    String tmps = (String)oin.readObject();
                    List<Res> reslist = new ArrayList<Res>();
                    if(tmps.equals("!OK!")){
                        File r = new File("Res.kin");
                            if(!r.exists()){
                                this.oout.writeObject((String)"!NORES!");
                                break;
                            }else{
                                this.oout.writeObject((String)"!OKRES!");
                            }
                         synchronized(this){
                            ObjectInputStream we = new ObjectInputStream(new FileInputStream("Res.kin"));
                            reslist = (ArrayList<Res>)we.readObject();
                            we.close();
                         }
                        Res restmp[] = new Res[reslist.size()];
                        restmp = reslist.toArray(restmp);
                        this.oout.writeObject((Res[])restmp);
                        this.oout.writeObject((String)"!GORES!"); // Get Object Res
                        Res res = (Res)oin.readObject();
                        synchronized(this){
                            int tmp = -254;
                            for(int i = 0;i<reslist.size();i++){
                               if(reslist.get(i).equals(res)){
                                    tmp = i;
                                    reslist.remove(i);
                                    break;
                                }
                            }
                            if(tmp>-1){
                                ObjectOutputStream wy = new ObjectOutputStream(new FileOutputStream("Res.kin"));
                                wy.writeObject(reslist);
                                wy.close();                                
                                 this.oout.writeObject((String)"!OK!");
                            }else  this.oout.writeObject((String)"!NORES!");
                        }
                        logger.doLog(1, "CancelRes: "+res.getName()+" ShowID:"+res.getShowID()+" SeatsCount:"+res.getSeats().length);
                    }
                }catch(SocketException e){
                    System.err.println("Client Disconnected: "+sockfd.getInetAddress().getHostAddress());
                    logger.doLog(0,this.threadName+": Client disconnected: "+sockfd.getInetAddress().getHostAddress());
                    endThread();
                }catch(EOFException e){
                     System.err.println("Connection closed: "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
                      logger.doLog(0,this.threadName+":Connection closed from "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
                }catch(IOException e){
                    System.err.println("IO Error: "+e);
                    logger.doLog(0,this.threadName+": IO Error from "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
                }catch(ClassNotFoundException e){
                    System.err.println("Class not found :"+e);
                    logger.doLog(0,this.threadName+": Class not found from "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
                }
                break;
            }
            case 9:{ // sprawdzenie zajętości miejsc
                try{
                    this.oout.writeObject((String)"!GDATA!");
                    String tmps = (String)oin.readObject();
                    List<Res> reslist = new ArrayList<Res>();
                    int reserved[][] = new int[10][10];
                    if(tmps.equals("!OK!")){
                        this.oout.writeObject((String)"!GSID!");
                        int showid = (Integer)this.oin.readObject();
                        File r = new File("Res.kin");
                            if(!r.exists()){
                                for(int[] i : reserved){
                                    for(int j : i){
                                        j = 0;
                                    }
                                }
                                this.oout.writeObject(reserved);
                                break;
                            }
                         synchronized(this){
                            ObjectInputStream we = new ObjectInputStream(new FileInputStream("Res.kin"));
                            reslist = (ArrayList<Res>)we.readObject();
                            we.close();
                         }
                         for(Res rs : reslist){
                             if(rs.getShowID() == showid){
                                 int seats[][] = rs.getSeats();
                                 for(int[] i : seats){
//                                     CHANGED - check Required
                                     if (rs.ischecked()) reserved[i[0]][i[1]] = 2;
                                     else reserved[i[0]][i[1]] = 1;
                                 }
                             }
                         }
                         this.oout.writeObject(reserved);
                    }
                }catch(SocketException e){
                    System.err.println("Client Disconnected: "+sockfd.getInetAddress().getHostAddress());
                    logger.doLog(0,this.threadName+": Client disconnected: "+sockfd.getInetAddress().getHostAddress());
                    endThread();
                }catch(EOFException e){
                     System.err.println("Connection closed: "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
                      logger.doLog(0,this.threadName+":Connection closed from "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
                }catch(IOException e){
                    System.err.println("IO Error: "+e);
                    logger.doLog(0,this.threadName+": IO Error from "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
                }catch(ClassNotFoundException e){
                    System.err.println("Class not found :"+e);
                    logger.doLog(0,this.threadName+": Class not found from "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
                }
                break;
            }
            case 10:{ // sprzedaż biletu w kasie
                try{
                    this.oout.writeObject((String)"!GDATA!");
                    String tmp = (String)oin.readObject();
                    if(tmp.equals("!OK!"))
                        this.oout.writeObject((String)"!NAZW!");
                        String nazwa = (String)oin.readObject();
                        this.oout.writeObject((String)"!SEANS!");
                        int showid = (Integer)oin.readObject();
                        int[][] seat = (int[][])oin.readObject();
                        Res res = new Res(nazwa,showid,seat.length,seat);
                        res.accept();
                        res.get();
                    synchronized (this){
                        try{
                            File r = new File("Res.kin");
                            Res ares[];
                            List<Res> reslist = new ArrayList<Res>();
                            int num = 0;
                            if(!r.exists()){
                                r.createNewFile();
                                reslist.add(res);
                            }else{
                                ObjectInputStream we = new ObjectInputStream(new FileInputStream("Res.kin"));
                                reslist = (ArrayList<Res>)we.readObject();
                                we.close();
                                reslist.add(res);
                            }
                            ObjectOutputStream wy = new ObjectOutputStream(new FileOutputStream("Res.kin"));
                            wy.writeObject(reslist);
                            wy.close();
                        }catch(IOException e){
                            System.err.println("IO Error from "+sockfd.getInetAddress().getHostAddress()+": "+e);
                        }
                    }
                    logger.doLog(1,"OffLineRes: "+res.getName()+" ShowID: "+res.getShowID()+" SeatCount: "+res.getSeats().length);
                }catch(SocketException e){
                    System.err.println("Client Disconnected: "+sockfd.getInetAddress().getHostAddress());
                    logger.doLog(0,this.threadName+": Client disconnected: "+sockfd.getInetAddress().getHostAddress());
                    endThread();
                }catch(IOException e){
                    System.err.println("IO Error: "+e);
                    logger.doLog(0,this.threadName+": IO Error from "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
                }catch(ClassNotFoundException e){
                    System.err.println("Class not found :"+e);
                    logger.doLog(0,this.threadName+": Class not found from "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
                }
                break;
            }
            case 11:{ // getProductList
                try{
                    this.oout.writeObject((String)"!GDATA!");
                    String tmp = (String)oin.readObject();
                    List<Product> prodlist = new ArrayList<Product>();
                    if(tmp.equals("!OK!")){
                        File r = new File("Product.kin");
                        if(!r.exists()){
                            this.oout.writeObject((String)"!NOPROD!");
                            break;
                        }else{
                            this.oout.writeObject((String)"!OKPROD!");
                        }
                        synchronized(this){
                            ObjectInputStream we = new ObjectInputStream(new FileInputStream("Product.kin"));
                            prodlist = (ArrayList<Product>)we.readObject();
                            we.close();
                        }
                        Product prodtmp[] = new Product[prodlist.size()];
                        prodtmp = prodlist.toArray(prodtmp);
                        this.oout.writeObject((Product[])prodtmp);
                        logger.doLog(1, "SendProd to " + this.threadName);
                    }
                }catch(SocketException e){
                    System.err.println("Client Disconnected: "+sockfd.getInetAddress().getHostAddress());
                    logger.doLog(0, this.threadName+": Client disconnected: "+sockfd.getInetAddress().getHostAddress());
                    endThread();
                }catch(IOException e){
                    System.err.println("IO Error: "+e);
                    logger.doLog(0, this.threadName+": IOError from "+sockfd.getInetAddress().getHostAddress()+": "+e);
                } catch (ClassNotFoundException e) {
                    System.err.println("ClassNotFound Error: "+e);
                    logger.doLog(0, this.threadName+": ClassNotFound from "+sockfd.getInetAddress().getHostAddress()+": "+e);
                }
                break;
                
            }
            case 12:{ // sellProductFromList
                try{
                    this.oout.writeObject((String)"!GDATA!");
                    String tmp = (String)oin.readObject();
                    List<Product> prodlist = new ArrayList<Product>();
                    if(tmp.equals("!OK!")){
                        File r = new File("Product.kin");
                        if(!r.exists()){
                            this.oout.writeObject((String)"!NOPROD!");
                            break;
                        }else{
                            this.oout.writeObject((String)"!OKPROD!");
                        }
                        synchronized(this){
                            ObjectInputStream we = new ObjectInputStream(new FileInputStream("Product.kin"));
                            prodlist = (ArrayList<Product>)we.readObject();
                            we.close();
                        }
                        this.oout.writeObject((String)"!GIPROD!"); // get ID Product
                        int prodId = (int)this.oin.readObject();
                        boolean ok = false;
                        for(int i = 0; i < prodlist.size(); i++){
                            if(prodlist.get(i).getId() == prodId){
                                if(prodlist.get(i).buy()){
                                    ok = true;
                                    logger.doLog(1, "BuyProd: "+prodlist.get(i).getName());
                                }
                                break;
                            }
                        }
                        if(ok){
                            ObjectOutputStream wy = new ObjectOutputStream(new FileOutputStream("Product.kin"));
                            wy.writeObject(prodlist);
                            wy.close();
                            this.oout.writeObject((String)"!OK!");
                        }else this.oout.writeObject((String)"!NOK!");
                    }
                }catch(SocketException e){
                    System.err.println("Client Disconnected: "+sockfd.getInetAddress().getHostAddress());
                    logger.doLog(0, this.threadName+": Client disconnected: "+sockfd.getInetAddress().getHostAddress());
                    endThread();
                }catch(IOException e){
                    System.err.println("IO Error: "+e);
                    logger.doLog(0, this.threadName+": IOError from "+sockfd.getInetAddress().getHostAddress()+": "+e);
                } catch (ClassNotFoundException e) {
                    System.err.println("ClassNotFound Error: "+e);
                    logger.doLog(0, this.threadName+": ClassNotFound from "+sockfd.getInetAddress().getHostAddress()+": "+e);
                }
                break;
            }
            case 13:{ // getAttractionList
                try{
                    this.oout.writeObject((String)"!GDATA!");
                    String tmp = (String)oin.readObject();
                    List<Attraction> attrlist = new ArrayList<Attraction>();
                    if(tmp.equals("!OK!")){
                        File r = new File("Attraction.kin");
                        if(!r.exists()){
                            this.oout.writeObject((String)"!NOATTR!");
                            break;
                        }else{
                            this.oout.writeObject((String)"!OKATTR!");
                        }
                        synchronized(this){
                            ObjectInputStream we = new ObjectInputStream(new FileInputStream("Attraction.kin"));
                            attrlist = (ArrayList<Attraction>)we.readObject();
                            we.close();
                        }
                        Attraction attrtmp[] = new Attraction[attrlist.size()];
                        attrtmp = attrlist.toArray(attrtmp);
                        this.oout.writeObject((Attraction[])attrtmp);
                        logger.doLog(1, "SendAttr to " + this.threadName);
                    }
                }catch(SocketException e){
                    System.err.println("Client Disconnected: "+sockfd.getInetAddress().getHostAddress());
                    logger.doLog(0, this.threadName+": Client disconnected: "+sockfd.getInetAddress().getHostAddress());
                    endThread();
                }catch(IOException e){
                    System.err.println("IO Error: "+e);
                    logger.doLog(0, this.threadName+": IOError from "+sockfd.getInetAddress().getHostAddress()+": "+e);
                } catch (ClassNotFoundException e) {
                    System.err.println("ClassNotFound Error: "+e);
                    logger.doLog(0, this.threadName+": ClassNotFound from "+sockfd.getInetAddress().getHostAddress()+": "+e);
                }
                break;
            }
            case 14:{ // reserveAttraction
                try{
                    this.oout.writeObject((String)"!GDATA!");
                    String tmp = (String)oin.readObject();
                    List<AttrRes> reslist = new ArrayList<AttrRes>();
                    if(tmp.equals("!OK!")){
                        File r = new File("AttrRes.kin");
                        this.oout.writeObject((String)"!GOARES!");
                        Attraction atr = (Attraction)this.oin.readObject();
                        AttrRes res = new AttrRes(atr);
                        if(!r.exists()){
                            r.createNewFile();
                            reslist.add(res);
                        }else{
                            ObjectInputStream we = new ObjectInputStream(new FileInputStream("AttrRes.kin"));
                            reslist = (ArrayList<AttrRes>)we.readObject();
                            we.close();
                            reslist.add(res);
                        }
                        ObjectOutputStream wy = new ObjectOutputStream(new FileOutputStream("AttrRes.kin"));
                        wy.writeObject(reslist);
                        wy.close();                        
                        logger.doLog(1, "Reserved Attratraction by " + this.threadName);
                    }
                }catch(SocketException e){
                    System.err.println("Client Disconnected: "+sockfd.getInetAddress().getHostAddress());
                    logger.doLog(0, this.threadName+": Client disconnected: "+sockfd.getInetAddress().getHostAddress());
                    endThread();
                }catch(IOException e){
                    System.err.println("IO Error: "+e);
                    logger.doLog(0, this.threadName+": IOError from "+sockfd.getInetAddress().getHostAddress()+": "+e);
                } catch (ClassNotFoundException e) {
                    System.err.println("ClassNotFound Error: "+e);
                    logger.doLog(0, this.threadName+": ClassNotFound from "+sockfd.getInetAddress().getHostAddress()+": "+e);
                }
                break;
            }
            case 666 :{
                
                break;
            }
            case 667:{
                
                break;
            }
            case 777:{           
                try { // sendAllDataToAdmin
                    /*
                    Files:
                    CRooms.kin
                    Movies.kin
                    Product.kin
                    Res.kin
                    Shows.kin
                    Users.kin
                    */
                    this.oout.writeObject((String)"!GDATA!");
                    String tmp = (String)oin.readObject();
                    if(tmp.equals("!OK!")){
                        File f = new File("CRooms.kin");
                        List<CRoom> r = new ArrayList<CRoom>();
                        if(f.exists()){
                            ObjectInputStream we = new ObjectInputStream(new FileInputStream("CRooms.kin"));
                            r = (ArrayList<CRoom>)we.readObject();
                            we.close();
                            this.oout.writeObject((String)"!FILE:CROOMS!");
                            tmp = (String)oin.readObject();
                            if(tmp.equals("!OK!")) this.oout.writeObject(r);
                            
                        }
                        tmp = (String)oin.readObject();
                        
                        f = new File("Movies.kin");
                        List<Movie> r2 = new ArrayList<Movie>();
                        if(f.exists()){
                            ObjectInputStream we = new ObjectInputStream(new FileInputStream("Movies.kin"));
                            r2 = (ArrayList<Movie>)we.readObject();
                            we.close();
                            this.oout.writeObject((String)"!FILE:MOVIES!");
                            tmp = (String)oin.readObject();
                            if(tmp.equals("!OK!")) this.oout.writeObject(r);
                        }
                        tmp = (String)oin.readObject();
                        
                        f = new File("Product.kin");
                        List<Product> r3 = new ArrayList<Product>();
                        if(f.exists()){
                            ObjectInputStream we = new ObjectInputStream(new FileInputStream("Product.kin"));
                            r3 = (ArrayList<Product>)we.readObject();
                            we.close();
                            this.oout.writeObject((String)"!FILE:PRODUCT!");
                            tmp = (String)oin.readObject();
                            if(tmp.equals("!OK!")) this.oout.writeObject(r);
                        }
                        tmp = (String)oin.readObject();
                        
                        this.oout.writeObject((String)"!FILE:EOS!");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            default:{
                out.write("!NOCMD!");
            }
            
     }
    }
    /**
     * Sprawdza czy na serwerze dostępny jest podany przez klienta użytkownik
     * @return true jeśli podany użytkownik pozwala na zalogowanie się na serwerze, false jeśli nie
     */
    private boolean checkUser(){
        String usr = this.luser.getName();
        String pwd = this.luser.getPass();//sha1pass
        
        boolean log = false;
        try{
            ObjectInputStream we = new ObjectInputStream(new FileInputStream("Users.kin"));
            try {
                List<User> ar = (ArrayList<User>)we.readObject();
                User[] tmp = ar.toArray(new User[]{}); 
                we.close();
                int ctmp = 0;
                for(int i=0;i<tmp.length;i++){
                    if(usr.equals(tmp[i].getName())){
                        if(pwd.equals(tmp[i].getPass())){
                            log = true;
                            this.luser = tmp[i];
                            break;
                        }                        
                    }
                }
            } catch(ClassNotFoundException e){
                    System.err.println("Class not found :"+e);
                    logger.doLog(0,this.threadName+": Class not found from "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
            }
        }catch(IOException e){
            System.err.println("IO Error from "+sockfd.getInetAddress().getHostAddress()+": "+e);
             logger.doLog(0,this.threadName+": IO Error from "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
        }
        return log;
    }
//    private Object getObj(String file){
//        try{
//            ObjectInputStream we = new ObjectInputStream(new FileInputStream(file));
//            Object obj = we.readObject();
//            return obj;
//        }catch(IOException e){
//            System.err.println("Błąd odczytu pliku");
//            logger.doLog(this.threadName+": Błąd odczytu pliku from "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
//        }catch(ClassNotFoundException c){
//            System.err.println("Brak klasy Object");
//        } 
//        return null;
//    } 
    
    
//     private String toSHA1(byte[] pass) {
//        MessageDigest md;
//        try{
//            md = MessageDigest.getInstance("SHA-1");
//        }catch(NoSuchAlgorithmException e){
//            System.err.println("No Such Algorithm Exception: "+e);
//            logger.doLog(this.threadName+": No Such Algorithm Exception from "+this.sockfd.getInetAddress().getHostAddress()+": "+e);
//            return null;
//        }
//        return byteToHex(md.digest(pass));
//    }
//    
//    private String byteToHex(byte[] hash)
//    {
//        Formatter formatter = new Formatter();
//        for (byte b : hash)
//        {
//            formatter.format("%02x", b);
//        }
//        String result = formatter.toString();
//        formatter.close();
//        return result;
//    }
    
}
