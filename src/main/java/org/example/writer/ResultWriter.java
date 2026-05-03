package org.example.writer;

import java.nio.file.Path;
import java.util.Map;

public interface ResultWriter {
    void writeResult(Map<String, Integer> result, Path outputPath);
}
