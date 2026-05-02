package org.example.discount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
    private DiscountFromMaxToMinWithStep discountFromMaxToMinWithStep;

    @Mock
    private Order order1;
    @Mock
    private Order order2;
    @Mock
    private Order order3;
    @Mock
    private Order order4;
    @Mock
    private Order order5;

    @BeforeEach
    void setUp() {
        discountFromMaxToMinWithStep = new DiscountFromMaxToMinWithStep();
    }

    @Test
    void testCalculateTotalCostsSortedByDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        when(order1.orderDate()).thenReturn(LocalDateTime.parse("2021-02-09T16:00:22", formatter));
        when(order2.orderDate()).thenReturn(LocalDateTime.parse("2021-02-09T08:42:59", formatter));
        when(order3.orderDate()).thenReturn(LocalDateTime.parse("2021-02-09T10:48:34", formatter));
        when(order4.orderDate()).thenReturn(LocalDateTime.parse("2021-02-09T11:41:31", formatter));
        when(order5.orderDate()).thenReturn(LocalDateTime.parse("2021-02-09T08:57:51", formatter));

        when(order1.company()).thenReturn("Industrial");
        when(order2.company()).thenReturn("Power Engineer");
        when(order3.company()).thenReturn("Mosque");
        when(order4.company()).thenReturn("Atomic");
        when(order5.company()).thenReturn("Preparatory");

        when(order1.quantityKg()).thenReturn(8800);
        when(order2.quantityKg()).thenReturn(17480);
        when(order3.quantityKg()).thenReturn(33120);
        when(order4.quantityKg()).thenReturn(12500);
        when(order5.quantityKg()).thenReturn(21410);

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

        verify(order1, atLeastOnce()).orderDate();
        verify(order2, atLeastOnce()).orderDate();
        verify(order3, atLeastOnce()).orderDate();
        verify(order4, atLeastOnce()).orderDate();
        verify(order5, atLeastOnce()).orderDate();

        verify(order1, times(1)).company();
        verify(order2, times(1)).company();
        verify(order3, times(1)).company();
        verify(order4, times(1)).company();
        verify(order5, times(1)).company();

        verify(order1, times(1)).quantityKg();
        verify(order2, times(1)).quantityKg();
        verify(order3, times(1)).quantityKg();
        verify(order4, times(1)).quantityKg();
        verify(order5, times(1)).quantityKg();

        verifyNoMoreInteractions(order1, order2, order3, order4, order5);
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
        int minDiscount = 0;
        double maxDiscount = 0.3;
        double stepDiscount = 0.1;

        Map<String, Integer> result = discountFromMaxToMinWithStep.calculateTotalCosts(
                orders, pricePerKg, minDiscount, maxDiscount, stepDiscount);

        assertEquals(1, result.size());
        assertEquals(350, result.get("TestCo"));

        verify(a, times(1)).quantityKg();
        verify(b, times(1)).quantityKg();
        verify(c, times(1)).quantityKg();
        verify(d, times(1)).quantityKg();
    }
}
