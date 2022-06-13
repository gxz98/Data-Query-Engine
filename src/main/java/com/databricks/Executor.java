package com.databricks;

import com.databricks.Impl.Entry;
import com.databricks.Impl.Table;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Executor {

    private static final String COMMA_DELIMITER = ",";
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]*$");
    private Table table;

    private CSVReader reader;

    public Executor() {
        table = new Table();
        reader = new CSVReader();
    }

    public void readCSV(String file) throws IOException {
        table = reader.read(file);
    }

    // test case: if no such required col
    public void selectColumn(String cols) {
        ArrayList<String> selected_cols = new ArrayList<>(List.of(cols.split(COMMA_DELIMITER)));
        table.selectCols(selected_cols);
    }

    // test case: if required row exceed the boundary
    public void takeRow(String num_row) {
        int count = Integer.parseInt(num_row);
        table.takeRows(count);
    }

    // test case: if the required col is not a numeric column
    // assume the numeric column is composed of integers
    public void orderByCol(String col) {
        table.orderByCol(col);
    }

    // assume inner join: selects records that have matching values in both tables
    public void joinCSV(String file, String col) throws IOException {
        Table t = reader.read(file);
        table.join(t, col);
    }

    public Table getTable() {
        return table;
    }
}
