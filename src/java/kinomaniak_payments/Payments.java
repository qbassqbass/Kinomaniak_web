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
public class Payments {
    private static PaymentStrategy payment = null;
    
    public static void setPayment(String strat){
        switch(strat){
            case "BitCoin":
                payment = new BitCoin();
                break;
            case "Cash":
                payment = new Cash();
                break;
            case "PayPal":
                payment = new PayPal();
                break;
            case "CreditCard":
                payment = new CreditCard();
                break;
            default:
                payment = new DummyPay();
        }
    }
}
