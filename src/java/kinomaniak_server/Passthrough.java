/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak_server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author qbass
 */
public class Passthrough {
    
    /**
     * Główna metoda do obsługi połączeń ze strony klienta do wątków serwera.
     * @param args
     */
    public static void main(String[] args){
        Log logger = new Log();
        final int PORT = 8888;
        ServerSocket sockfd;
//        List<Thread> thrli = new ArrayList<Thread>();
        System.out.println("Waiting for connection");
        class Ex implements Runnable{ 
            public List<Thread> thrli = new ArrayList<Thread>();
            public boolean interrupted = false;
            public void doExit(){
                System.out.println("Waiting for clients disconnection...");
                while(true){
                    int alive = 0;
                    for (Thread t : thrli){
//                        t.interrupt();
                        if(t.isAlive()) alive++;
                    }
                    if(alive == 0){
                        this.interrupted = true;
                        break;
                    }                
                }
            }
            @Override
            public void run(){
                while(true){
                    Scanner in = new Scanner(System.in);
                    if(in.nextLine().equals("exit")) doExit();
                }
            }
        }
        Ex ex = new Ex();
        Thread exth = new Thread(ex);
        exth.start();        
            try{
                sockfd = new ServerSocket(PORT);
                sockfd.setSoTimeout(100);
                while(!ex.interrupted){ 
//                    wait for client to connect
                    try{
                        Socket tmpsockfd = sockfd.accept();
                        if(tmpsockfd != null){
                            System.out.println("Client connected from "+tmpsockfd.getInetAddress().getHostAddress());
                            String st = new StringBuilder(new SimpleDateFormat("dd-mm-yyyy").format(new Date())).toString();
                            logger.doLog(0,"separator");
                            logger.doLog(0,"Client connected from "+tmpsockfd.getInetAddress().getHostAddress());
                            ex.thrli.add(new Thread(new Server(tmpsockfd)));
                            ex.thrli.get(ex.thrli.size()-1).start();
                        }
                    }catch(SocketTimeoutException e){
                        
                    }catch(IOException e){
                        System.err.println("Could not connect Client.");
                    }
                }
                sockfd.close();
                System.exit(2);
            }catch(IOException e){
                System.err.println("Could not listen on port: "+PORT);  
                System.exit(1); 
            }
            
    }
}
