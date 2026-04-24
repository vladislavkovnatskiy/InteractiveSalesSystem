package org.example.reader;

import org.example.castomexception.IORuntimeException;
import org.example.order.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

public class HashDelimetrFileOrderAdapter implements OrderReader {
    private static final String DELIMITER = "#";
    private static final Integer ORDER_DATE_INDEX = 0;
    private static final Integer COMPANY_NAME_INDEX = 1;
    private static final Integer QUANTITY_INDEX = 2;

    @Override
    public List<Order> readOrders(Path filePath) throws IOException {
        try (Stream<String> stream = Files.lines(filePath)) {
            return stream
                    .filter(line -> line != null && !line.isBlank())
                    .map(this::parseLine).toList();
        } catch (IORuntimeException e) {
            throw new IORuntimeException("Error reading orders from file " + filePath);
        }
    }

    private Order parseLine(String line) {
        String[] parts = line.split(DELIMITER);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid line format: " + line);
        }
        LocalDateTime dateTime = LocalDateTime.parse(parts[ORDER_DATE_INDEX], DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        String companyName = parts[COMPANY_NAME_INDEX];
        int quantity = Integer.parseInt(parts[QUANTITY_INDEX]);
        return new Order(dateTime, companyName, quantity);
    }
}
