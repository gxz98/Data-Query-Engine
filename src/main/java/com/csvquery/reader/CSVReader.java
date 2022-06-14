package com.csvquery.reader;

import com.csvquery.data.CSVTable;
import com.csvquery.data.Table;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CSVReader implements Reader {

    @Override
    public Table read(String file) {
        Table table = new CSVTable();
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
            return table;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
