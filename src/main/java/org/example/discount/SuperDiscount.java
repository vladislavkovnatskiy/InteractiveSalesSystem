package org.example.discount;

import org.example.order.Order;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SuperDiscount implements DiscountStrategy {
    @Override
    public Map<String, Integer> calculateTotalCosts(List<Order> orders, int pricePerKG) {
        List<Order> sortedOrders = orders.stream()
                .sorted((o1, o2) -> o1.orderDate().compareTo(o2.orderDate())).toList();

        return IntStream.range(0, sortedOrders.size())
                .mapToObj(i -> {
                    Order order = sortedOrders.get(i);
                    int amount = order.quantityKg() * pricePerKG;
                    int cost = amount - calculatorDiscount(amount, i);
                    return Map.entry(order.company(), cost);
                }).collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.summingInt(Map.Entry::getValue)
                ));
    }

    private int calculatorDiscount(int amount, int i) {

        if (i * 0.05 <= 0.45) {
            return (int) (amount * (0.5 - i * 0.05));
        } else {
            return 0;
        }
    }
}
