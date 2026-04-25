package org.example.order;

import org.example.discount.DiscountStrategy;
import org.example.discount.SuperDiscount;
import org.example.reader.OrderAdapter;
import org.example.reader.OrderReaderFactory;
import org.example.writer.FileWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class OrderProcessor {
        private static final int DEFAULT_PRICE_PER_KG = 10;
        private static final int MIN_DISCOUNT = 0;
        private static final double MAX_DISCOUNT = 0.5;
        private static final double STEP_DISCOUNT = 0.05;
    public static void processor(String[] args, Path inputPath, Path outputPath) throws IOException {
        //выбрали тип аддаптера для чтения через фабрику и получили список заказов
        OrderAdapter reader = OrderReaderFactory.getOrderReader(inputPath);
        List<Order> orders = reader.readOrders(inputPath);

        DiscountStrategy discountStrategy = new SuperDiscount();

        int pricePerKg = (args.length > 1) ? Integer.parseInt(args[1]) : DEFAULT_PRICE_PER_KG;
        int minDiscount = (args.length > 2) ? Integer.parseInt(args[2]) : MIN_DISCOUNT;
        double maxDiscount = (args.length > 3) ? Double.parseDouble(args[3]) : MAX_DISCOUNT;
        double stepDiscount = (args.length > 4) ? Double.parseDouble(args[4]) : STEP_DISCOUNT;

        // Расчёт итоговой стоимости
        Map<String, Integer> result = discountStrategy.calculateTotalCosts(orders, pricePerKg, minDiscount, maxDiscount, stepDiscount);

        //Запись результата
        FileWriter writer = new FileWriter();
        writer.writeResult(result, outputPath);
        System.out.println("The result is saved in " + outputPath.toAbsolutePath());
    }
}
