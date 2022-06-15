package com.dataquery.reader;

import com.dataquery.data.Table;

public interface Reader {
    /**
     * Read in data in the file.
     * @param file
     * @return data stored in structured format
     */
    Table read(String file);
}
