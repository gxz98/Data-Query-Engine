package com.databricks;

import java.io.*;
import java.util.StringJoiner;
import java.util.stream.Stream;

/**
 * Read in the command line inputs and assign tasks to each corresponding class.
 */
public class Executor {

    static final String COMMA_DELIMITER = ",";
    private Table table;

    public Executor() {
        table = new Table();
    }

    public void readCSV(String file) throws IOException {
        try (
                InputStream inputStream = this.getClass().getResourceAsStream("/" + file);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(inputStreamReader);
        ) {
            table.setTitles(br.readLine());
            String line;
            while ((line = br.readLine()) != null) {
                table.addEntry(line);
            }
        }
    }

    public Table selectColumn(String cols) {
        Table tab = new Table();
        tab.setTitles(cols);
        String[] selected_cols = cols.split(COMMA_DELIMITER);
        for (Entry e : table.getBody()) {
            StringJoiner line = new StringJoiner(",");
            for (String col : selected_cols) {
                line.add(e.getValue(col));
            }
            tab.addEntry(line.toString());
        }
        return tab;
    }

    public Table getTable() {
        return table;
    }
}
