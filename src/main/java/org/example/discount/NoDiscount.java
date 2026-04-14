package org.example.discount;

import org.example.order.Order;

import java.util.List;
import java.util.Map;

public class NoDiscount implements DiscountStrategy {
    @Override
    public Map<String, Integer> calculateTotalCosts(List<Order> orders, int PRICE_PER_KG) {
        return null;
    }
}
