package org.example.order;

import org.example.discount.DiscountStrategy;
import org.example.discount.DiscountFromMaxToMinWithStep;
import org.example.reader.OrderAdapter;
import org.example.reader.OrderReaderFactory;
import org.example.writer.FileWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class OrderProcessor {

    public void processor(Path inputPath, Path outputPath, int pricePerKg, int minDiscount, double maxDiscount, double stepDiscount) throws IOException {
        OrderAdapter reader = OrderReaderFactory.getOrderReader(inputPath);
        List<Order> orders = reader.readOrders(inputPath);

        DiscountStrategy discountStrategy = new DiscountFromMaxToMinWithStep();

        Map<String, Integer> result = discountStrategy.calculateTotalCosts(orders, pricePerKg, minDiscount, maxDiscount, stepDiscount);

        FileWriter writer = new FileWriter();
        writer.writeResult(result, outputPath);
        System.out.println("The result is saved in " + outputPath.toAbsolutePath());
    }
}
