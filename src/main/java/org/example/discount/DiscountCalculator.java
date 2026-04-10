package org.example.discount;

public class DiscountCalculator {
    private final

    public int calculateDiscount(int volumeOfCement, int price, DiscountStrategy discountStrategy) {
        int amount = volumeOfCement * price;
        return discountStrategy.calculateDiscount(amount);
    }
}