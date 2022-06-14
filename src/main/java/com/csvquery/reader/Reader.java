package com.csvquery.reader;

import com.csvquery.data.Table;

public interface Reader {
    /**
     * Read in data in the file.
     * @param file
     * @return data stored in structured format
     */
    Table read(String file);
}
