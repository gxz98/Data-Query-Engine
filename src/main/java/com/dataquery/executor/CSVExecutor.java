package com.dataquery.executor;

import com.dataquery.data.CSVTable;
import com.dataquery.data.Table;
import com.dataquery.reader.CSVReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVExecutor implements Executor {

    private static final String COMMA_DELIMITER = ",";

    private Table table;

    private CSVReader reader;

    public CSVExecutor() {
        table = new CSVTable();
        reader = new CSVReader();
    }

    @Override
    public void readFile(String file) throws IOException {
        table = reader.read(file);
    }

    @Override
    // test case: if no such required col
    public void selectColumn(String cols) {
        ArrayList<String> selected_cols = new ArrayList<>(List.of(cols.split(COMMA_DELIMITER)));
        table.selectCols(selected_cols);
    }

    // test case: if required row exceed the boundary
    @Override
    public void takeRow(String num_row) {
        int count = Integer.parseInt(num_row);
        table.takeRows(count);
    }

    // test case: if the required col is not a numeric column
    // assume the numeric column is composed of integers
    @Override
    public void orderByCol(String col) {
        table.orderByCol(col);
    }

    // assume inner join: selects records that have matching values in both tables
    @Override
    public void join(String file, String col) throws IOException {
        Table t = reader.read(file);
        table.join(t, col);
    }

    @Override
    public void count(String col) {
        table.countBy(col);
    }

    @Override
    public Table getTable() {
        return table;
    }
}
