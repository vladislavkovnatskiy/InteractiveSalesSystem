package org.example.discount;

import org.example.order.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscountFromMaxToMinWithStep implements DiscountStrategy {

    @Override
    public Map<String, Integer> calculateTotalCosts(List<Order> orders, int pricePerKG, double minDiscount, double maxDiscount, double stepDiscount) {
        List<Order> sortedOrders = orders.stream()
                .sorted((o1, o2) -> o1.orderDate().compareTo(o2.orderDate()))
                .toList();

        Map<String, Integer> result = new HashMap<>();
        for (int i = 0; i < sortedOrders.size(); i++) {
            Order order = sortedOrders.get(i);
            int amount = order.quantityKg() * pricePerKG;
            int cost = amount - calculatorDiscount(amount, i, minDiscount, maxDiscount, stepDiscount);
            String company = order.company();
            result.merge(company, cost, Integer::sum);
        }
        return result;
    }

    private int calculatorDiscount(int amount, int i, double minDiscount, double maxDiscount, double stepDiscount) {
        int maxPercent = (int) Math.round(maxDiscount * 100);
        int stepPercent = (int) Math.round(stepDiscount * 100);
        int minPercent = (int) Math.round(minDiscount * 100);

        if (maxPercent - i * stepPercent > minPercent) {
            return (amount * (maxPercent - i * stepPercent)) / 100;
        } else {
            return (amount * minPercent) / 100;
        }
    }
}
