package org.example.discount;

import org.example.order.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscountFromMaxToMinWithStep implements DiscountStrategy {

    @Override
    public Map<String, Integer> calculateTotalCosts(List<Order> orders, int pricePerKG, int minDiscount, double maxDiscount, double stepDiscount) {
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

    private int calculatorDiscount(int amount, int i, int minDiscount, double maxDiscount, double stepDiscount) {

        if (i * stepDiscount <= (maxDiscount - stepDiscount)) {
            return (int) (amount * (maxDiscount - i * stepDiscount));
        } else {
            return minDiscount;
        }
    }
}
