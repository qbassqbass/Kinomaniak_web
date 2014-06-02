/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak_server;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Klasa logująca zdarzenia/błędy do plików
 * @author qbass
 */
public class Log {
    /**
     * Konstruktor klasy Log sprawdzający dostępność podkatalogów logs oraz stats i ewentualne utworzenie ich.
     */
    public Log(){
        File dir = new File("logs");
        if(!dir.exists()) dir.mkdir();
        dir = new File("stats");
        if(!dir.exists()) dir.mkdir();
        dir = new File("others");
        if(!dir.exists()) dir.mkdir();
    }
    /**
     * Metoda zapisująca zdarzenia do pliku z aktualnymi logami
     * @param type typ logu(0 - zdarzenie/błąd, 1 - statystyka)
     * @param tolog zdarzenie/błąd do zapisania w logu
     */
    public void doLog(int type, String tolog){
        PrintWriter out = null;
        String fname;
        switch(type){
            case 0:{
                fname = "logs/"+new StringBuilder(new SimpleDateFormat("dd-MM-yyyy").format(new Date())).toString()+".log";
                break;
            }
            case 1:{
                fname = "stats/"+new StringBuilder(new SimpleDateFormat("dd-MM-yyyy").format(new Date())).toString()+".log";
                break;
            }
            default:{
                fname =  "others/"+new StringBuilder(new SimpleDateFormat("dd-MM-yyyy").format(new Date())).toString()+".log";
            }
        }
        try{
            File fil = new File(fname);
            if(!fil.exists()) fil.createNewFile();
            out = new PrintWriter(new BufferedWriter(new FileWriter(fname,true)));
            if(tolog.equals("separator")){
                String st = new StringBuilder(new SimpleDateFormat("HH:mm:ss").format(new Date())).toString();
                out.println("===="+st+"====");
            }else{
                out.println(new StringBuilder(new SimpleDateFormat("HH:mm:ss").format(new Date())).toString()+"  "+tolog);
            }
            out.close();
        }catch(IOException e){
            System.err.println("IOError: "+e);
        }finally{
            if(out != null) out.close();
        }
    }
    
}
