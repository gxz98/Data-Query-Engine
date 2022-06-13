package com.databricks;

import com.databricks.Impl.Entry;

import java.util.ArrayList;
import java.util.List;

public interface Table {
    void setTitles(String line);

    void addEntry(String line);

    ArrayList<String> getTitle();

    void selectCols(ArrayList<String> col);

    void takeRows(int rowNum);

    void printCSV();
}
