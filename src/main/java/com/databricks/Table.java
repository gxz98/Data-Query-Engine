package com.databricks;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Table {

    static final String COMMA_DELIMITER = ",";
    private List<Entry> body;
    private String title;

    public Table() {
        body = new ArrayList<>();
    }

    public void setTitles(String line) {
        title = line;
    }

    public void addEntry(String line) {
        body.add(new Entry(title, line));
    }

    public String getTitle() {
        return title;
    }

    public List<Entry> getBody() {
        return body;
    }

    public void printCSV() {
        System.out.println(title);
        Stream<String> lines = body.stream().map(Entry::toString);
        lines.forEach(System.out::println);
    }

}
