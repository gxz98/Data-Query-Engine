package com.databricks.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Entry implements com.databricks.Entry {
    static final String COMMA_DELIMITER = ",";

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]*$");
    // one row of data in the CSV file
    private ArrayList<String> row;

    public Entry(String line) {
        row = new ArrayList<>(List.of(line.split(COMMA_DELIMITER)));
    }

    public Entry(ArrayList<String> newEntry) {
        row = newEntry;
    }

    @Override
    public String getValueAt(int idx) {
        if (idx > row.size()) {
            return "";
        }
        return row.get(idx);
    }

    /**
     * Compare two Entries by the column value.
     * If the column is numeric, sort entries in descending order.
     * @return positive or negative integer value.
     */
    public static int compareBy(Entry e1, Entry e2, int idx) {
        String value1 = e1.getValueAt(idx);
        String value2 = e2.getValueAt(idx);
        // numeric column
        if (value1.matches(NUMBER_PATTERN.pattern())) {
            return Integer.parseInt(value1) < Integer.parseInt(value2) ? 1 : -1;
        }
        // string column
        else {
            return value1.compareTo(value2);
        }
    }

    public ArrayList<String> getRow() {
        return row;
    }

    public void removeCol(int idx) {
        row.remove(idx);
    }

    @Override
    public String toString() {
        return row.toString();
    }

}
