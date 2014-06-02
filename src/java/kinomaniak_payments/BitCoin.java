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
public class BitCoin implements PaymentStrategy {
    private String pubKey;
    private String privKey;
    
    public BitCoin(){
        
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
    
    public void setData(String priv, String pub){
        this.privKey = priv;
        this.pubKey = pub;
    }
    
    private boolean checkData(){
        boolean ret = false;
        
        return ret;
    }
    
}
