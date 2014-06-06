/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kinomaniak.beans;

import kinomaniak_objs.*;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Qbass
 */
@ManagedBean
public class Ticket implements Serializable{
    private int id;
    private int type;

    public Ticket() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public Ticket(int type){
        this.id = this.getLastId() + 1;
        this.type = type;
    }
    
    private int getLastId(){
        int tmp = -1;
        
        return tmp;
    }
}
