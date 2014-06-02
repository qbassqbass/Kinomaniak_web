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
public class PayPal implements PaymentStrategy {
    private String emailAddress;
    private String password;
    
    public PayPal(){
        
    }
    
    @Override
    public void pay(float amount) {
        if(this.checkData()){
            
        }else{
            //send info about payment error
            //maybe some:
            //throw new PaymentException();
        }
    }
    
    public void setData(String email, String password){
        this.emailAddress = email;
        this.password = password;
    }
    
    private boolean checkData(){
        boolean ret = false;
        
        return ret;
    }
    
}
