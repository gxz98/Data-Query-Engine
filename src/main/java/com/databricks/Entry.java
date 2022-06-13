package com.databricks;

import java.util.ArrayList;

public interface Entry {
    String getValueAt(int idx);

    ArrayList<String> getRow();

}
