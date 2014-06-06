/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kinomaniak.beans;

import kinomaniak_objs.*;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Qbass
 */
@ManagedBean
public class GoldCard implements GC{    
        private final int id;
        private final int ownerId;
        private final float discount;
        
        

        @Override
        public int getId() {
            return id;
        }

        @Override
        public int getOwnerId() {
            return ownerId;
        }

        @Override
        public float getDiscount() {
            return discount;
        }

        public GoldCard(int id, int own, float disc){
            this.id = id;
            this.ownerId = own;
            this.discount = disc;
        }  
    }
