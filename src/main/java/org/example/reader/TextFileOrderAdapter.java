package org.example.reader;

public class TextFileOrderAdapter extends FileOrderAdapter {
    private static final String DELIMITER = "\\|";

    @Override
    protected String getDelimiter() {
        return DELIMITER;
    }
}
