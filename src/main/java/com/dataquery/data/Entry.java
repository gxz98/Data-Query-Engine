package com.dataquery.data;

import java.util.ArrayList;
import java.util.regex.Pattern;

public interface Entry {

    /**
     * Get value in an entry by the column index.
     * @param idx column index
     * @return value string
     */
    String getValueAt(int idx);

    /**
     * Get one row in the table.
     * @return data in one entry
     */
    ArrayList<String> getRow();

    /**
     * Delete one column by its index in an entry.
     * @param idx
     */
    void removeCol(int idx);

    /**
     * Compare two Entries by the column value.
     * If the column is numeric, sort entries in descending order.
     * Otherwise, sort in lexicographical order.
     * @return positive or negative integer value
     */
    static int compareBy(Entry e1, Entry e2, int idx) {
        String value1 = e1.getValueAt(idx);
        String value2 = e2.getValueAt(idx);
        // numeric column
        if (value1.matches(Pattern.compile("^[0-9]*$").pattern())) {
            int v1 = Integer.parseInt(value1);
            int v2 = Integer.parseInt(value2);
            if (v1 == v2) return 0;
            return v1 < v2 ? 1 : -1;
        }
        // string column
        else {
            return value1.compareTo(value2);
        }
    }

}
