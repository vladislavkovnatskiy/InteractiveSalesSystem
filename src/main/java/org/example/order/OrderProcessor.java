package org.example.order;

import org.example.discount.DiscountStrategy;
import org.example.reader.OrderAdapter;
import org.example.writer.FileWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class OrderProcessor {

    private final OrderAdapter reader;
    private final DiscountStrategy discountStrategy;
    private final FileWriter writer;

    public OrderProcessor(OrderAdapter reader, DiscountStrategy discountStrategy, FileWriter writer) {
        this.reader = reader;
        this.discountStrategy = discountStrategy;
        this.writer = writer;
    }

    public void processor(Path inputPath, Path outputPath, int pricePerKg, int minDiscount, double maxDiscount, double stepDiscount) throws IOException {

        List<Order> orders = reader.readOrders(inputPath);

        Map<String, Integer> result = discountStrategy.calculateTotalCosts(orders, pricePerKg, minDiscount, maxDiscount, stepDiscount);

        writer.writeResult(result, outputPath);
        System.out.println("The result is saved in " + outputPath.toAbsolutePath());
    }
}
