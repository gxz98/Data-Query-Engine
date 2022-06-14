package com.csvquery.executor;

import com.csvquery.data.Table;

import java.io.IOException;

public interface Executor {

    void readFile(String file) throws IOException;

    void selectColumn(String cols);

    void takeRow(String num_row);

    void orderByCol(String col);

    void join(String file, String col) throws IOException;

    void count(String col);

    Table getTable();
}
