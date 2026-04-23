package org.example.writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class FileWriter {
    public void writeResult(Map<String, Integer> result, Path outputPath) {
        List<String> lines = result.entrySet().stream().map(entry -> entry.getKey() + " - " + entry.getValue()).toList();
        try {
            Files.write(outputPath, lines);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to a file: " + outputPath, e);
        }
    }
}
