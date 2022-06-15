package com.dataquery.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVTable implements Table {

    static final String COMMA_DELIMITER = ",";

    // list of rows in the table, not including column names
    private ArrayList<Entry> body;

    // list of column names
    private ArrayList<String> title;

    // number of rows in the table
    private int num_row;

    public CSVTable() {
        body = new ArrayList<>();
        num_row = 0;
    }

    @Override
    public void setTitles(String line) {
        title = new ArrayList<>(List.of(line.split(COMMA_DELIMITER)));
    }

    @Override
    public void addEntry(String line) {
        body.add(new CSVEntry(line));
        num_row += 1;
    }

    @Override
    public void selectCols(ArrayList<String> col) {
        List<Integer> selectIndexes = col.stream().map(this::getIndexByName).toList();
        ArrayList<Entry> newBody= new ArrayList<>();
        for (Entry e : body) {
            ArrayList<String> newRow = new ArrayList<>();
             for (int idx : selectIndexes) {
                newRow.add(e.getValueAt(idx));
             }
             newBody.add(new CSVEntry(newRow));
        }
        title = col;
        body = newBody;
    }

    @Override
    public void takeRows(int rowNum) {
        int take = Math.min(rowNum, num_row);
        body = new ArrayList<>(body.subList(0, take));
    }

    @Override
    public void orderByCol(String col) {
        int idx = getIndexByName(col);
        body = new ArrayList<>(body.stream().sorted(
                (e1, e2) -> Entry.compareBy(e1, e2, idx)).toList());
    }

    @Override
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
        t.getTitle().remove(col);
        int index = getIndexByName(col);
        ArrayList<Entry> newBody = new ArrayList<>();
        for (Entry e : getBody()) {
            String joinKey = e.getValueAt(index);
            if (keyToEntry.containsKey(joinKey)) {
                ArrayList<String> joinedRow = new ArrayList<>();
                joinedRow.addAll(e.getRow());
                joinedRow.addAll(keyToEntry.get(joinKey).getRow());
                newBody.add(new CSVEntry(joinedRow));
            }
        }
        title.addAll(t.getTitle());
        body = newBody;
    }

    @Override
    public void countBy(String col) {
        int idx = getIndexByName(col);
        ArrayList<String> column = getColumn(idx);
        Map<String, Long> count = column.stream().collect(Collectors.groupingBy(p -> p, Collectors.counting()));
        title = new ArrayList<>(List.of(col, "count"));
        body = new ArrayList<>();
        count.forEach((k,v) -> body.add(new CSVEntry(k + "," + v.toString())));
    }

    @Override
    public int getIndexByName(String col) {
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

    @Override
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

    /**
     * Get one column in a table by its index.
     * @param idx
     * @return a list of values in the specified column
     */
    public ArrayList<String> getColumn(int idx) {
        ArrayList<String> res = new ArrayList<>();
        for (Entry e : body) {
            res.add(e.getValueAt(idx));
        }
        return res;
    }

}
