package com.dataquery.data;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVTable implements Table {

    static final String COMMA_DELIMITER = ",";

    // list of rows in the table, not including column names
    private ArrayList<Entry> body;

    // list of column names
    private ArrayList<String> title;

    public CSVTable() {
        body = new ArrayList<>();
    }

    public CSVTable(ArrayList<String> t, ArrayList<Entry> entries) {
        body = entries;
        title = t;
    }

    @Override
    public void setTitles(String line) {
        title = new ArrayList<>(List.of(line.split(COMMA_DELIMITER)));
    }

    @Override
    public void addEntry(String line) {
        body.add(new CSVEntry(line));
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
        if (rowNum < 0) {
            throw new RuntimeException("The displayed number of data must by positive! ");
        }
        int take = Math.min(rowNum, body.size());
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
        this.orderByCol(col);
        t.orderByCol(col);
        int idx1 = getIndexByName(col);
        int idx2 = t.getIndexByName(col);

        Iterator<Entry> tableIter1 = body.iterator();
        Iterator<Entry> tableIter2 = t.getBody().iterator();
        // pointer for each table
        Entry p1 = tableIter1.hasNext() ? tableIter1.next() : null;
        Entry p2 = tableIter2.hasNext() ? tableIter2.next() : null;

        ArrayList<Entry> newBody = new ArrayList<>();
        while (p1 != null && p2 != null) {
            int compare = p1.getValueAt(idx1).compareTo(p2.getValueAt(idx2));
            // move the pointer with smaller value
            if (compare < 0) {
                p1 = tableIter1.hasNext()? tableIter1.next() : null;
            }
            if (compare > 0) {
                p2 = tableIter2.hasNext() ? tableIter2.next() : null;
            }
            else {
                ArrayList<String> joinedRow = new ArrayList<>();
                joinedRow.addAll(p1.getRow());
                ArrayList<String> removedCol_p2 = new ArrayList<>(p2.getRow());
                removedCol_p2.remove(idx2);
                joinedRow.addAll(removedCol_p2);
                newBody.add(new CSVEntry(joinedRow));
                p1 = tableIter1.hasNext() ? tableIter1.next() : null;
            }
        }
        body = newBody;
        t.getTitle().remove(col);
        title.addAll(t.getTitle());
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
