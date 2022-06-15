package com.dataquery.data;

import java.util.ArrayList;

public interface Table {

    /**
     * Select columns by their column name.
     * @param col
     */
    void selectCols(ArrayList<String> col);

    /**
     * Select the first few rows in the table.
     * @param rowNum number of rows to take
     */
    void takeRows(int rowNum);

    /**
     * Sort the table by the given column name.
     * If the specified column is numeric, sort in descending order.
     * Otherwise, sort in lexicographical order.
     * @param col
     */
    void orderByCol(String col);

    /**
     * Join two tables by the shared column.
     * @param t
     * @param col shared column name among two tables
     */
    void join(Table t, String col);

    /**
     * Count the entries by a specified column.
     * Reduce the table into two columns: specified column and the count results.
     * @param col
     */
    void countBy(String col);

    /**
     * Get table title.
     * @return a list of column names
     */
    ArrayList<String> getTitle();

    /**
     * Specify the column names in the table.
     * @param line
     */
    void setTitles(String line);

    /**
     * Add a row of data in the table.
     * @param line
     */
    void addEntry(String line);

    /**
     * Print the current table.
     */
    void printCSV();

    /**
     * Get all rows in the table, except for the table title.
     * @return
     */
    ArrayList<Entry> getBody();

    /**
     * Get the column index by its name.
     * @param col
     * @return the index of column name
     */
    int getIndexByName(String col);
}
