package com.dataquery.data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CSVEntry implements Entry {
    static final String COMMA_DELIMITER = ",";

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
        if (idx >= row.size() || idx < 0) {
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
        if (idx >= row.size() || idx < 0) {
            return;
        }
        row.remove(idx);
    }

    @Override
    public String toString() {
        return row.toString();
    }

    /**
     * Compare two entries by their column contents.
     * @param obj
     * @return true or false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        if (!row.equals(((CSVEntry) obj).getRow())) return false;
        return true;
    }

}
