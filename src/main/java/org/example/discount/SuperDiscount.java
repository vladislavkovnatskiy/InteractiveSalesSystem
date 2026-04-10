package org.example.discount;

public class SuperDiscount implements DiscountStrategy{
    public static int customersCounter = 0;
    @Override
    public int calculateDiscount(int amount){
        if(customersCounter * 0.05 < 0.45) {
            int discountAmount = (int) (amount * (0.5 - customersCounter * 0.05));
            customersCounter++;
            return discountAmount;
        }else {
            RegularDiscount regularDiscount = new RegularDiscount();
            return regularDiscount.calculateDiscount(amount);
        }
    }
}
