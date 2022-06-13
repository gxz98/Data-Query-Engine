package com.databricks;

import com.databricks.Impl.Table;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CSVReader {

    public Table read(String file) {
        Table table = new Table();
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
