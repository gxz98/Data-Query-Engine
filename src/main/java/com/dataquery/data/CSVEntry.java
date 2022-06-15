package com.dataquery.data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CSVEntry implements Entry {
    static final String COMMA_DELIMITER = ",";

    // pattern to match integer numbers
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]*$");

    // one row in CSV table
    private ArrayList<String> row;

    public CSVEntry(String line) {
        row = new ArrayList<>(List.of(line.split(COMMA_DELIMITER)));
    }

    public CSVEntry(ArrayList<String> newEntry) {
        row = newEntry;
    }

    @Override
    public String getValueAt(int idx) {
        if (idx > row.size()) {
            return "";
        }
        return row.get(idx);
    }

    @Override
    public ArrayList<String> getRow() {
        return row;
    }

    @Override
    public void removeCol(int idx) {
        row.remove(idx);
    }

    @Override
    public String toString() {
        return row.toString();
    }

}
