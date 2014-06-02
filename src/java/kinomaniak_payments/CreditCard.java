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
public class CreditCard implements PaymentStrategy {
    private String ownerName;
    private String cardNumber;
    private int cvv;
    private String expDate;
    
    public CreditCard(){
        
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
    
    public void setData(String owner, String num, int cvv, String exp){
        this.ownerName = owner;
        this.cardNumber = num;
        this.cvv = cvv;
        this.expDate = exp;
    }
    
    private boolean checkData(){
        boolean ret = false;
        
        return ret;
    }
    
}
