package org.example;

import org.example.order.OrderProcessor;

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

        OrderProcessor.processor(args, inputPath, outputPath);
    }
}
