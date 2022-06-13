package com.databricks.Impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Table implements com.databricks.Table {

    static final String COMMA_DELIMITER = ",";
    private ArrayList<Entry> body;
    private ArrayList<String> title;

    private int num_row;

    private int num_col;

    public Table() {
        body = new ArrayList<>();
        num_col = 0;
        num_row = 0;
    }

    @Override
    public void setTitles(String line) {
        title = new ArrayList<>(List.of(line.split(COMMA_DELIMITER)));
        num_col = title.size();
    }

    @Override
    public void addEntry(String line) {
        body.add(new Entry(line));
        num_row += 1;
    }

    // select the columns and change CSV table in place
    public void selectCols(ArrayList<String> col) {
        List<Integer> selectIndexes = col.stream().map(this::getIndexByName).toList();
        ArrayList<Entry> newBody= new ArrayList<>();
        for (Entry e : body) {
            ArrayList<String> newRow = new ArrayList<>();
             for (int idx : selectIndexes) {
                newRow.add(e.getValueAt(idx));
             }
             newBody.add(new Entry(newRow));
        }
        title = col;
        body = newBody;
    }

    // take rows and change CSV table in place
    public void takeRows(int rowNum) {
        int take = Math.min(rowNum, num_row);
        body = new ArrayList<>(body.subList(0, take));
    }

    // sort table entries
    public void orderByCol(String col) {
        int idx = getIndexByName(col);
        body = new ArrayList<>(body.stream().sorted(
                (e1, e2) -> Entry.compareBy(e1, e2, idx)).toList());
    }

    public void join(Table t, String col) {
        // a lookup for key-entry mapping
        HashMap<String, Entry> keyToEntry = new HashMap<>();
        int joinByIndex = t.getIndexByName(col);
        for (Entry e : t.getBody()) {
            String key = e.getValueAt(joinByIndex);
            e.removeCol(joinByIndex);
            keyToEntry.putIfAbsent(key, e);
        }
        // remove duplicate column name
        t.removeColName(col);
        int index = getIndexByName(col);
        ArrayList<Entry> newBody = new ArrayList<>();
        for (Entry e : getBody()) {
            String joinKey = e.getValueAt(index);
            if (keyToEntry.containsKey(joinKey)) {
                ArrayList<String> joinedRow = new ArrayList<>();
                joinedRow.addAll(e.getRow());
                joinedRow.addAll(keyToEntry.get(joinKey).getRow());
                newBody.add(new Entry(joinedRow));
            }
        }
        title.addAll(t.getTitle());
        body = newBody;
    }

    private int getIndexByName(String col) {
        int i = 0;
        while (i < title.size()) {
            if (title.get(i).equals(col)) {
                return i;
            }
            i += 1;
        }
        throw new RuntimeException("No column named " + col);
    }

    @Override
    public ArrayList<String> getTitle() {
        return title;
    }

    public void removeColName(String col) {
        title.remove(col);
    }

    public ArrayList<Entry> getBody() {
        return body;
    }

    @Override
    public void printCSV() {
        // remove bracket
        System.out.println(title.toString().replace("[", "").replace("]", ""));
        Stream<String> lines = body.stream().map(line -> line.toString().replace("[", "").replace("]", ""));
        lines.forEach(System.out::println);
    }

}
