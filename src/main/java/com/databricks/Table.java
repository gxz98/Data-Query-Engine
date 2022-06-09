package com.databricks;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Table {

    static final String COMMA_DELIMITER = ",";
    private List<Entry> body;
    private String title;

    private int num_row;

    private int num_col;

    public Table() {
        body = new ArrayList<>();
        num_col = 0;
        num_row = 0;
    }

    public void setTitles(String line) {
        title = line;
        num_col = line.split(COMMA_DELIMITER).length;
    }

    public void addEntry(String line) {
        body.add(new Entry(title, line));
        num_row += 1;
    }

    public String getTitle() {
        return title;
    }

    public List<Entry> getBody() {
        return body;
    }

    public int getRowNum() {
        return num_row;
    }

    public void printCSV() {
        System.out.println(title);
        Stream<String> lines = body.stream().map(Entry::toString);
        lines.forEach(System.out::println);
    }

}
