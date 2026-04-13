package org.example;

import org.example.discount.DiscountCalculatorFactory;
import org.example.discount.DiscountStrategy;
import org.example.order.Order;
import org.example.reader.OrderReader;
import org.example.reader.OrderReaderFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws IOException {
        final int PRICE_PER_KG = 10;
        if (args.length < 1) {
            System.err.println("Укажите путь к входному файлу");
            System.err.println("Пример: src/main/resources/discount_day.txt");
            System.exit(1);
        }

        Path inputPath = Paths.get(args[0]);
        Path outputPath = Paths.get("src/main/result/result.txt");

        //выбрали тип аддаптера для чтения через фабрику и получили список заказов
        OrderReader reader = OrderReaderFactory.getOrderReader(inputPath);
        List<Order> orders = reader.readOrders(inputPath);

        //super промо, регулярное или без промо
        DiscountStrategy discountStrategy = DiscountCalculatorFactory.getDiscountStrategy(args[1]);

        Map<String,Integer> result = discountStrategy.calculateTotalCosts(orders, PRICE_PER_KG);

        writeResult(result,outputPath);
        System.out.println("The result is saved in " +  outputPath.toAbsolutePath());
    }

    private static void writeResult(Map<String, Integer> result, Path outputPath){
        List<String> lines = result.entrySet().stream().map(entry -> entry.getKey() + " - " + entry.getValue()).toList();
        try {
            Files.write(outputPath,lines);
        } catch (IOException e) {throw new RuntimeException("Error writing to a file: " + outputPath, e);}
    }
}
