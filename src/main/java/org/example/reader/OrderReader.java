package org.example.reader;

import org.example.order.Order;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface OrderReader {
    List<Order> readOrders(Path filePath) throws IOException;
}
