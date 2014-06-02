/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak_mgmt;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import kinomaniak_objs.*;
import java.util.Scanner;
/**
 * Klasa główna Menadżera dla projektu Kinomaniak.
 * @author qbass
 */
public class MgmtApp {
    private static boolean loaded = false;
    private static Scanner in = new Scanner(System.in);
    
    @SuppressWarnings("empty-statement")
    private static int menu() throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.println("\nPress Enter to continue...");
        
        while(!sc.nextLine().equals(""));
        in = new Scanner(System.in);
        System.out.println("==MENU==");
        System.out.println("-1. Generate placeholder data");
        System.out.println("0. Load data");
        if(loaded){
            System.out.println("1. Save data");
            System.out.println("2. Add Movie");
            System.out.println("4. Add CRoom");
            System.out.println("5. Add Show");
            System.out.println("6. List Movies");
            System.out.println("7. List CRooms");
            System.out.println("8. List Shows");
            System.out.println("9. Delete Movie");
            System.out.println("10. Delete CRoom");
            System.out.println("11. Delete Show");
            System.out.println("12. Add User");
            System.out.println("13. Delete User");
            System.out.println("14. List Users");
            System.out.println("15. List Reservations");
        }
        System.out.println("666. Kill me");
        System.out.print("Your choice: ");
        try{            
            int c = MgmtApp.in.nextInt();
            return c;
        }catch(InputMismatchException e){
            System.err.println("You have to input a number!");
        }
       return -666;
    }
    
    /**
     * Główna metoda dla Aplikacji Menadżera projektu Kinomaniak.
     * @param args nieużywane
     * @throws IOException
     */
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws IOException{
        System.out.println("==Welcome in Movie DataBase Management System==");
        MovieDBMgmt2 mgmt = new MovieDBMgmt2();
        while(true){
        int tmp = menu();
        switch(tmp){
            case -1:{
                Movie[] mov = new Movie[1];
                mov[0] = new Movie("Heroes","Action","Everyone");
                CRoom[] cr = new CRoom[1];
                cr[0] = new CRoom(1);
                Time[] tm = new Time[1];
                tm[0] = new Time(12,10);
                Show[] sh = new Show[1];
                sh[0] = new Show(mov[0],cr[0],tm[0]);
                sh[0].setID(0);
                User[] usr = new User[1];
                usr[0] = new User("User","Password",0);
                try{
                    ObjectOutputStream wy = new ObjectOutputStream(new FileOutputStream("Movies.kin"));
                    wy.writeObject(mov);
                    wy.close();
                    wy = new ObjectOutputStream(new FileOutputStream("CRooms.kin"));
                    wy.writeObject(cr);
                    wy.close();
                    wy = new ObjectOutputStream(new FileOutputStream("Shows.kin"));
                    Date dateNow = new Date (); 
                    SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
                    StringBuilder sdt = new StringBuilder( dt.format( dateNow ) );
                    String st = sdt.toString();
                    wy.writeObject(st);
                    wy.writeObject(sh.length);
                    wy.writeObject(sh);
                    wy.close();
                    wy = new ObjectOutputStream(new FileOutputStream("Users.kin"));
                    wy.writeObject(usr);
                    wy.close();
                }catch(IOException e){
                    System.err.println("IO Error: "+e);
                }
                break;
            }
            case 0:{
                mgmt.getData();
                System.out.println("Data loaded...");
                loaded = true;
                break;
            }
            case 1:{
                mgmt.saveData();
                System.out.println("Data saved...");
                System.out.println("\nPress Enter to continue...");
                break;
            }
            case 2:{
                Scanner in = new Scanner(System.in);
                System.out.print("Movie Title: ");
                String name = in.nextLine();
                System.out.print("Movie Genre: ");
                String genre = in.nextLine();
                System.out.print("Movie Rating: ");
                String rating = in.nextLine();
                System.out.print("Short Movie Description('null' if no desc): ");
                String desc = in.nextLine();
                if(desc.equals("null")) mgmt.addMovie(name,genre,rating);
                else mgmt.addMovie(name,genre,rating,desc);
                System.out.println("Movie added");
                break;
            }
            case 3:{
                break;
            }
            case 4:{
                Scanner in = new Scanner(System.in);
                System.out.print("ID: ");
                int id = in.nextInt();
                mgmt.addCRoom(id);
                System.out.println("CRoom added");
                break;
            }
            case 5:{
                Scanner in = new Scanner(System.in);
                System.out.println("List of Movies: ");
                mgmt.listMovies();
                System.out.print("Your Choice: ");
                int mov = in.nextInt();
                System.out.println("List of CRooms: ");
                mgmt.listCRooms();
                System.out.print("Your Choice: ");
                int cr = in.nextInt();
                System.out.println("Date: ");
                System.out.print("Day: ");
                int day = in.nextInt();
                System.out.print("Month: ");
                int mon = in.nextInt();
                System.out.print("Year: ");
                int year = in.nextInt();
                System.out.println("Time: ");
                System.out.print("Hour: ");
                int h = in.nextInt();
                System.out.print("Minutes: ");
                int m = in.nextInt();
                mgmt.setTime(h,m,day,mon,year);
                mgmt.addShow(mgmt.getMovies()[mov],mgmt.getTime(),mgmt.getCRooms()[cr]);
                System.out.println("Show added");
                break;
            }
            case 6:{
                mgmt.listMovies();
                break;
            }
            case 7:{
                mgmt.listCRooms();
                break;
            }
            case 8:{
                System.out.flush();
                mgmt.listShows();
                break;
            }
            case 9:{
                //delete Movie
                mgmt.listMovies();
                System.out.print("MovieID to delete: ");
                int d = MgmtApp.in.nextInt();
                if(mgmt.delMovie(d) < 0) System.out.println("No Movie: "+d);
                else System.out.println("Movie "+d+" deleted");
                break;
            }
            case 10:{
                //delete CRoom
                mgmt.listCRooms();
                System.out.print("CRoomID to delete: ");
                int d = MgmtApp.in.nextInt();
                if(mgmt.delCRoom(d) < 0) System.out.println("No CRoom: "+d);
                else System.out.println("CRoom "+d+" deleted");
                break;
            }
            case 11:{
                //delete Show
                mgmt.listShows();
                System.out.print("ShowID to delete: ");
                int d = MgmtApp.in.nextInt();
                if(mgmt.delShow(d) < 0) System.out.println("No Show: "+d);
                else System.out.println("Show "+d+" deleted");
                break;
            }
            case 12:{
                //add user
                Scanner in = new Scanner(System.in);
                System.out.print("Username: ");
                String uname = in.nextLine();
                System.out.print("Password: ");
                String upass = in.nextLine();
                System.out.println("User type: ");
                System.out.println("0. Client\n1. Cashier");
                System.out.print("Your choice: ");
                int utype = in.nextInt();
                mgmt.addUser(uname, upass, utype);
                break;
            }
            case 13:{
                //delete user
                mgmt.listUsers();
                System.out.print("UserID to delete: ");
                Scanner in = new Scanner(System.in);
                int id = in.nextInt();
                if(mgmt.delUser(id) < 0) System.out.println("No User: "+id);
                else System.out.println("User "+id+" deleted");
                break;
            }
            case 14:{
                //list users
                mgmt.listUsers();
                break;
            }
            case 15:{
                //list Ress
                mgmt.listRess();
                break;
            }
            case 666:{
                System.out.println("Goodbye...");
                System.exit(1);
            }
            default:{
                System.out.println("You have to choose proper option!");
                break;
            }
        }
    }
    }
}
