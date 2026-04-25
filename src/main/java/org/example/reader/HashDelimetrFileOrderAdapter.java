package org.example.reader;

public class HashDelimetrFileOrderAdapter extends FileOrderAdapter {
    private static final String DELIMITER = "#";

    @Override
    protected String getDelimiter() {
        return DELIMITER;
    }
}
