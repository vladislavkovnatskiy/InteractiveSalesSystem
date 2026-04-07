package org.example.reader;

import org.example.order.Order;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

public class TextFileOrderAdapter implements OrderReader {
    private static final String DELIMITER = "\\|";

    @Override
    public List<Order> readOrders(Path filePath) throws IOException {
        try (Stream<String> stream = Files.lines(filePath)) {
            return stream
                    .filter(line -> line != null && !line.isEmpty())
                    .map(this::parseLine).toList();
        }catch (IOException e){
            throw new RuntimeException("Error reading orders from file " + filePath, e);
        }
    }
    private Order parseLine(String line) {
        String[] parts = line.split(DELIMITER);
        if(parts.length != 3) {
            throw new IllegalArgumentException("Invalid order line: " + line);
        }
        LocalDateTime dateTime = LocalDateTime.parse(parts[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String companyName = parts[1];
        int quantity = Integer.parseInt(parts[2]);
        return new Order(dateTime, companyName, quantity);
    }
}
