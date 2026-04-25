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

public abstract class FileOrderAdapter implements OrderAdapter {
    protected static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    protected static final Integer ORDER_DATE_INDEX = 0;
    protected static final Integer COMPANY_NAME_INDEX = 1;
    protected static final Integer QUANTITY_INDEX = 2;

    protected abstract String getDelimiter();

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
        String delimiter = getDelimiter();
        String[] parts = line.split(delimiter);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid line format: " + line);
        }
        LocalDateTime dateTime = LocalDateTime.parse(parts[ORDER_DATE_INDEX], DATE_TIME_FORMATTER);
        String companyName = parts[COMPANY_NAME_INDEX];
        int quantity = Integer.parseInt(parts[QUANTITY_INDEX]);
        return new Order(dateTime, companyName, quantity);
    }
}
