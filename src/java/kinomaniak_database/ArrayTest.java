/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kinomaniak_database;

/**
 *
 * @author Jakub
 */
public class ArrayTest {
    
    public static void main(String[] args){
        String str = "1:1,1:2,1:3";
        String str2 = "2:3,2:4,2:5,2:6";
        
        String split[] = str.split(",");
        int seats[][] = new int[split.length][2];
        System.out.println(split.length);
        int i = 0;
        for(String s : split){
            seats[i][0] = Integer.valueOf(s.split(":")[0]);
            seats[i][1] = Integer.valueOf(s.split(":")[1]);
            i++;
        }
        for(String s : split) System.out.println(s);
        for(int st[] : seats){
            System.out.println("Row: "+st[0]+" Col: "+st[1]);
        }
    }
    
}
