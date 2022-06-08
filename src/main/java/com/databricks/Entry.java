package com.databricks;

import java.util.HashMap;
public class Entry {
    static final String COMMA_DELIMITER = ",";
    private HashMap<String, String> entry;
    private String row;

    public Entry(String title, String line) {
        row = line;
        entry = new HashMap<>();
        String[] titles = title.split(COMMA_DELIMITER);
        String[] values = line.split(COMMA_DELIMITER);
        for (int i = 0; i < titles.length; i++) {
            entry.put(titles[i], values[i]);
        }
    }

    public String getValue(String col) {
        return entry.getOrDefault(col, null);
    }

    @Override
    public String toString() {
        return row;
    }

}
