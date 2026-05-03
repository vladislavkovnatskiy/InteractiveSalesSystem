package org.example.discount;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.example.order.Order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DiscountFromMaxToMinWithStepTest {
    private final DiscountFromMaxToMinWithStep discountFromMaxToMinWithStep  = new DiscountFromMaxToMinWithStep();

    private final Order order1 = new Order(LocalDateTime.parse("2021-02-09T16:00:22", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), "Industrial", 8800);
    private final Order order2 = new Order(LocalDateTime.parse("2021-02-09T08:42:59", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), "Power Engineer", 17480);
    private final Order order3 = new Order(LocalDateTime.parse("2021-02-09T10:48:34", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), "Mosque", 33120);
    private final Order order4 = new Order(LocalDateTime.parse("2021-02-09T11:41:31", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), "Atomic", 12500);
    private final Order order5 = new Order(LocalDateTime.parse("2021-02-09T08:57:51", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), "Preparatory", 21410);

    @Test
    void testCalculateTotalCostsSortedByDate() {

        List<Order> orders = List.of(order1, order2, order3, order4, order5);

        int pricePerKg = 10;
        int minDiscount = 0;
        double maxDiscount = 0.5;
        double stepDiscount = 0.05;

        Map<String, Integer> result = discountFromMaxToMinWithStep.calculateTotalCosts(orders, pricePerKg, minDiscount, maxDiscount, stepDiscount);

        assertEquals(5, result.size());
        assertEquals(61600, result.get("Industrial"));
        assertEquals(87400, result.get("Power Engineer"));
        assertEquals(198720, result.get("Mosque"));
        assertEquals(81250, result.get("Atomic"));
        assertEquals(117755, result.get("Preparatory"));
    }

    @Test
    void testCalculateTotalCostsEmptyList() {
        List<Order> orders = List.of();
        Map<String, Integer> result = discountFromMaxToMinWithStep.calculateTotalCosts(orders, 100, 0, 0.2, 0.05);
        assertEquals(0, result.size());
    }

    @Test
    void testCalculateTotalCostsSingleOrder() {
        Order singleOrder = mock(Order.class);
        when(singleOrder.company()).thenReturn("Single");
        when(singleOrder.quantityKg()).thenReturn(50);

        List<Order> orders = List.of(singleOrder);

        int pricePerKg = 20;
        int minDiscount = 0;
        double maxDiscount = 0.25;
        double stepDiscount = 0.05;

        Map<String, Integer> result = discountFromMaxToMinWithStep.calculateTotalCosts(
                orders, pricePerKg, minDiscount, maxDiscount, stepDiscount);

        assertEquals(1, result.size());
        assertEquals(750, result.get("Single"));

        verify(singleOrder, times(1)).company();
        verify(singleOrder, times(1)).quantityKg();
    }

    @Test
    void testCalculateTotalCostsMinDiscountTriggered() {
        Order a = mock(Order.class);
        Order b = mock(Order.class);
        Order c = mock(Order.class);
        Order d = mock(Order.class);

        LocalDateTime now = LocalDateTime.now();
        when(a.orderDate()).thenReturn(now);
        when(b.orderDate()).thenReturn(now);
        when(c.orderDate()).thenReturn(now);
        when(d.orderDate()).thenReturn(now);

        when(a.quantityKg()).thenReturn(10);
        when(b.quantityKg()).thenReturn(10);
        when(c.quantityKg()).thenReturn(10);
        when(d.quantityKg()).thenReturn(10);

        when(a.company()).thenReturn("TestCo");
        when(b.company()).thenReturn("TestCo");
        when(c.company()).thenReturn("TestCo");
        when(d.company()).thenReturn("TestCo");

        List<Order> orders = List.of(a, b, c, d);
        int pricePerKg = 10;
        double minDiscount = 0;
        double maxDiscount = 0.3;
        double stepDiscount = 0.1;

        Map<String, Integer> result = discountFromMaxToMinWithStep.calculateTotalCosts(
                orders, pricePerKg, minDiscount, maxDiscount, stepDiscount);

        assertEquals(1, result.size());
        assertEquals(340, result.get("TestCo"));

        verify(a, times(1)).quantityKg();
        verify(b, times(1)).quantityKg();
        verify(c, times(1)).quantityKg();
        verify(d, times(1)).quantityKg();
    }
    @Test
    void testCalculateTotalCostsWithMinDiscountOnePercent(){
        Order a = mock(Order.class);
        Order b = mock(Order.class);
        Order c = mock(Order.class);
        Order d = mock(Order.class);

        LocalDateTime now = LocalDateTime.now();
        when(a.orderDate()).thenReturn(now);
        when(b.orderDate()).thenReturn(now);
        when(c.orderDate()).thenReturn(now);
        when(d.orderDate()).thenReturn(now);

        when(a.quantityKg()).thenReturn(10);
        when(b.quantityKg()).thenReturn(10);
        when(c.quantityKg()).thenReturn(10);
        when(d.quantityKg()).thenReturn(10);

        when(a.company()).thenReturn("TestCo");
        when(b.company()).thenReturn("TestCo");
        when(c.company()).thenReturn("TestCo");
        when(d.company()).thenReturn("TestCo");

        List<Order> orders = List.of(a, b, c, d);
        int pricePerKg = 10;
        double minDiscount = 0.01;
        double maxDiscount = 0.07;
        double stepDiscount = 0.03;

        Map<String, Integer> result = discountFromMaxToMinWithStep.calculateTotalCosts(orders, pricePerKg, minDiscount, maxDiscount, stepDiscount);

        assertEquals(1, result.size());
        assertEquals(387, result.get("TestCo"));
        verify(a, times(1)).quantityKg();
        verify(b, times(1)).quantityKg();
        verify(c, times(1)).quantityKg();
        verify(d, times(1)).quantityKg();
    }
}
