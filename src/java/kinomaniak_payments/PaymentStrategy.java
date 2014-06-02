/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kinomaniak_payments;

/**
 *
 * @author Qbass
 */
public interface PaymentStrategy {
    
    public void pay(float amount);
    
}
