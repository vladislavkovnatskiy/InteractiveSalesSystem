package org.example.discount;

public class RegularDiscount implements DiscountStrategy{
    @Override
    public int calculateDiscount(int amount){
        return (int) (amount * 0.05);
    }
}
