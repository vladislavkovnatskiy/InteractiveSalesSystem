package org.example.discount;

public class DiscountCalculatorFactory {
    public static DiscountStrategy getDiscountStrategy(String discountType) {
        if(discountType.equalsIgnoreCase("super")){
            return new SuperDiscount();
        }else {
            return new NoDiscount();
        }
    }
}