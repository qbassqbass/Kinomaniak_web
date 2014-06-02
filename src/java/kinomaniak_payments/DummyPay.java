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
public class DummyPay implements PaymentStrategy{
    private final String dummyData = "dummy";

    public DummyPay(){
        
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
    
    private boolean checkData(){
        boolean ret = true;
        
        return ret;
    }
    
}
