package org.example.order;

import org.example.discount.DiscountStrategy;
import org.example.reader.OrderAdapter;
import org.example.writer.FileWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderProcessorTest {
    @Mock
    private OrderAdapter orderAdapter;

    @Mock
    private DiscountStrategy discountStrategy;

    @Mock
    private FileWriter fileWriter;

    @InjectMocks
    private OrderProcessor orderProcessor;

    @Test
    void testOrderProcessor() throws IOException {
        Path inputPath = Paths.get("input.txt");
        Path outputPath = Paths.get("output.txt");

        int pricePerKg = 10;
        int minDiscount = 0;
        double maxDiscount = 0.5;
        double stepDiscount = 0.05;

        List<Order> mockOrders = List.of(
                new Order(LocalDateTime.parse("2021-02-09T16:00:22", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), "Industrial", 8800),
                new Order(LocalDateTime.parse("2021-02-09T08:42:59", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), "Power Engineer", 17480),
                new Order(LocalDateTime.parse("2021-02-09T10:48:34", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), "Mosque", 33120),
                new Order(LocalDateTime.parse("2021-02-09T11:41:31", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), "Atomic", 12500),
                new Order(LocalDateTime.parse("2021-02-09T08:57:51", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), "Preparatory", 21410),
                new Order(LocalDateTime.parse("2021-02-09T20:26:03", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), "Resident", 5610),
                new Order(LocalDateTime.parse("2021-02-09T09:50:10", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), "Fossil", 19600),
                new Order(LocalDateTime.parse("2021-02-10T08:53:25", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), "Power Engineer", 24600),
                new Order(LocalDateTime.parse("2021-02-09T17:39:17", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), "Carryover", 29670),
                new Order(LocalDateTime.parse("2021-02-09T12:32:48", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), "Electricity", 3680),
                new Order(LocalDateTime.parse("2021-02-09T09:11:43", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), "Pyramid", 10100)
        );

        Map<String, Integer> mockResult = Map.of(
                "Pyramid", 65650,
                "Electricity", 31281,
                "Preparatory", 128460,
                "Industrial", 79201,
                "Power Engineer", 342140,
                "Mosque", 248400,
                "Carryover", 281866,
                "Resident", 56100,
                "Fossil", 137200,
                "Atomic", 100001
        );

        when(orderAdapter.readOrders(inputPath)).thenReturn(mockOrders);
        when(discountStrategy.calculateTotalCosts(mockOrders, pricePerKg, minDiscount, maxDiscount, stepDiscount)).thenReturn(mockResult);

        orderProcessor.processor(inputPath, outputPath, pricePerKg, minDiscount, maxDiscount, stepDiscount);

        verify(orderAdapter, times(1)).readOrders(inputPath);
        verify(discountStrategy,times(1)).calculateTotalCosts(mockOrders, pricePerKg, minDiscount, maxDiscount, stepDiscount);
        verify(fileWriter, times(1)).writeResult(mockResult, outputPath);
    }
}
