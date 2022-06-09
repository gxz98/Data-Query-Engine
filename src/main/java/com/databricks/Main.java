package com.databricks;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to CSV Query Engine!");
        Scanner sc = new Scanner(System.in);
        String query = "";
        Executor executor = new Executor();

        while (!query.equals("EXIT")) {
            if (query.length() != 0) {
                String[] tokens = query.split("\\s+");
                if (!Objects.equals(tokens[0], "FROM")) {
                    throw new IllegalArgumentException("Query must start with FROM. Please select an CSV file.");
                }

                int size = tokens.length;
                int i = 0;
                while (i < size) {
                    if (Objects.equals(tokens[i], "FROM")) {
                        executor.readCSV(tokens[++i]);
                        i += 1;
                        if (i == size) {
                            executor.getTable().printCSV();
                        }
                    }
                    else if (Objects.equals(tokens[i], "SELECT")) {
                        executor.selectColumn(tokens[++i]).printCSV();
                        i += 1;
                    }
                    else if (Objects.equals(tokens[i], "TAKE")) {
                        executor.takeRow(tokens[++i]).printCSV();
                        i += 1;
                    }
                }
            }
            query = sc.nextLine();
        }
        sc.close();
    }
}
