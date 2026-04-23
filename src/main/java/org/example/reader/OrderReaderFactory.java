package org.example.reader;

import java.io.IOException;
import java.nio.file.Path;

public class OrderReaderFactory {
    public static OrderReader getOrderReader(Path filePath) throws IOException {
        String fileName = filePath.getFileName().toString().toLowerCase();

        if (fileName.endsWith(".txt")) {
            return new TextFileOrderAdapter();
        } else {
            return new HashDelimetrFileOrderAdapter();
        }
    }
}
