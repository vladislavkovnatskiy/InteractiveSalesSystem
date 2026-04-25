package org.example.reader;

import org.example.order.Order;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface OrderAdapter {
    List<Order> readOrders(Path filePath) throws IOException;
}
