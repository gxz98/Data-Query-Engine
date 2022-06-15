package com.dataquery;

import com.dataquery.data.Table;
import com.dataquery.reader.CSVReader;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class CSVReaderTest {
    private CSVReader csvReader;

    @Before
    public void setup() {
        csvReader = new CSVReader();
    }

    @Test
    public void testRead() {
        Table table = csvReader.read("city.csv");
        assertEquals(Arrays.asList("CityID", "CityName", "CountryCode", "CityPop"),
                        table.getTitle());
        assertEquals(4079, table.getBody().size());
    }
}
