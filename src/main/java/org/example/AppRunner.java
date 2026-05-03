package org.example;

import org.example.discount.DiscountFromMaxToMinWithStep;
import org.example.discount.DiscountStrategy;
import org.example.order.OrderProcessor;
import org.example.reader.OrderAdapter;
import org.example.reader.OrderReaderFactory;
import org.example.writer.FileWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AppRunner {

    public static void run(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Укажите путь к входному файлу");
            System.err.println("Пример: src/main/resources/discount_day.txt");
            System.exit(1);
        }

        Path inputPath = Paths.get(args[0]);
        Path outputPath = Paths.get("src/main/result/result.txt");

        int pricePerKg = Integer.parseInt(args[1]);
        int minDiscount = Integer.parseInt(args[2]);
        double maxDiscount = Double.parseDouble(args[3]);
        double stepDiscount = Double.parseDouble(args[4]);

        OrderAdapter reader = OrderReaderFactory.getOrderReader(inputPath);
        DiscountStrategy discountStrategy = new DiscountFromMaxToMinWithStep();
        FileWriter writer = new FileWriter();

        OrderProcessor orderProcessor = new OrderProcessor(reader, discountStrategy, writer);
        orderProcessor.processor(inputPath, outputPath, pricePerKg, minDiscount, maxDiscount, stepDiscount);
    }
}
