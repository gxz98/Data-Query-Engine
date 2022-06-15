package com.dataquery;

import com.dataquery.executor.CSVExecutor;
import com.dataquery.executor.Executor;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to CSV Query Engine!");
        Scanner sc = new Scanner(System.in);
        String query = "";
        Executor executor = new CSVExecutor();

        while (!query.equals("EXIT")) {
            if (query.length() != 0) {
                String[] tokens = query.split("\\s+");
                if (!Objects.equals(tokens[0], "FROM")) {
                    throw new IllegalArgumentException("Query must start with FROM. Please select an CSV file.");
                }
                int size = tokens.length;
                int i = 0;
                while (i < size) {
                    switch (tokens[i]) {
                        case "FROM" -> {
                            executor.readFile(tokens[++i]);
                            i += 1;
                            if (i == size) {
                                executor.getTable().printCSV();
                            }
                        }
                        case "SELECT" -> {
                            executor.selectColumn(tokens[++i]);
                            executor.getTable().printCSV();
                            i += 1;
                        }
                        case "TAKE" -> {
                            executor.takeRow(tokens[++i]);
                            executor.getTable().printCSV();
                            i += 1;
                        }
                        case "ORDERBY" -> {
                            executor.orderByCol(tokens[++i]);
                            i += 1;
                            if (i == size) {
                                executor.getTable().printCSV();
                            }
                        }
                        case "JOIN" -> {
                            executor.join(tokens[++i], tokens[++i]);
                            i += 1;
                            if (i == size) {
                                executor.getTable().printCSV();
                            }
                        }
                        case "COUNTBY" -> {
                            executor.count(tokens[++i]);
                            i += 1;
                            if (i == size) {
                                executor.getTable().printCSV();
                            }
                        }
                        default -> throw new RuntimeException("Unknown query " + tokens[i]);
                    }
                }
            }
            query = sc.nextLine();
        }
        sc.close();
    }
}
