package com.dataquery;

import com.dataquery.data.CSVEntry;
import com.dataquery.data.CSVTable;
import com.dataquery.data.Entry;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CSVTableTest {

    private CSVTable table1;

    private CSVTable table2;

    @Before
    public void setup() {
        ArrayList<String> t1 = new ArrayList<>(Arrays.asList("CityID", "CityName", "CountryCode", "CityPop"));
        ArrayList<Entry> e1 = new ArrayList<>();
        e1.add(new CSVEntry("1,Kabul,AFG,1780000"));
        e1.add(new CSVEntry("2,Qandahar,AFG,237500"));
        e1.add(new CSVEntry("3,Herat,AFG,186800"));
        e1.add(new CSVEntry("4,Mazar-e-Sharif,AFG,127800"));
        e1.add(new CSVEntry("5,Amsterdam,NLD,731200"));
        table1 = new CSVTable(t1, e1);

        ArrayList<String> t2 = new ArrayList<>(Arrays.asList("CountryCode", "CountryName", "Continent", "CountryPop", "Capital"));
        ArrayList<Entry> e2 = new ArrayList<>();
        e2.add(new CSVEntry("ABW,Aruba,North_America,103000,129"));
        e2.add(new CSVEntry("ASM,American_Samoa,Oceania,68000,54"));
        e2.add(new CSVEntry("AFG,Afghanistan,Asia,22720000,1"));
        e2.add(new CSVEntry("BLR,Belarus,Europe,10236000,3520"));
        e2.add(new CSVEntry("BLZ,Belize,North_America,241000,185"));
        table2 = new CSVTable(t2, e2);
    }

    @Test(expected = RuntimeException.class)
    public void testGetIndexByName_Non_Existent_Col() {
        table1.getIndexByName("test");
    }

    @Test
    public void testSelectCols() {
        ArrayList<String> titles = new ArrayList<>(List.of("CityID", "CityName", "CountryCode"));
        table1.selectCols(titles);
        assertEquals(Arrays.asList("CityID", "CityName", "CountryCode"),
                        table1.getTitle());
        Assert.assertEquals(Arrays.asList(
                new CSVEntry("1,Kabul,AFG"),
                new CSVEntry("2,Qandahar,AFG"),
                new CSVEntry("3,Herat,AFG"),
                new CSVEntry("4,Mazar-e-Sharif,AFG"),
                new CSVEntry("5,Amsterdam,NLD")
        ), table1.getBody());
    }

    @Test
    public void testTakeRows() {
        table2.takeRows(2);
        assertEquals(Arrays.asList("CountryCode", "CountryName", "Continent", "CountryPop", "Capital"),
                table2.getTitle());
        Assert.assertEquals(Arrays.asList(
                new CSVEntry("ABW,Aruba,North_America,103000,129"),
                new CSVEntry("ASM,American_Samoa,Oceania,68000,54")
        ), table2.getBody());
    }

    @Test
    public void testTakeRows_Exceed_Maximum_Rows() {
        table1.takeRows(6);
        assertEquals(Arrays.asList("CityID", "CityName", "CountryCode", "CityPop"),
                table1.getTitle());
        Assert.assertEquals(Arrays.asList(
                new CSVEntry("1,Kabul,AFG,1780000"),
                new CSVEntry("2,Qandahar,AFG,237500"),
                new CSVEntry("3,Herat,AFG,186800"),
                new CSVEntry("4,Mazar-e-Sharif,AFG,127800"),
                new CSVEntry("5,Amsterdam,NLD,731200")
        ), table1.getBody());
    }

    @Test
    public void testOrderByCol() {
        table1.orderByCol("CityPop");
        Assert.assertEquals(Arrays.asList(
                new CSVEntry("1,Kabul,AFG,1780000"),
                new CSVEntry("5,Amsterdam,NLD,731200"),
                new CSVEntry("2,Qandahar,AFG,237500"),
                new CSVEntry("3,Herat,AFG,186800"),
                new CSVEntry("4,Mazar-e-Sharif,AFG,127800")
        ), table1.getBody());

        table2.orderByCol("Capital");
        Assert.assertEquals(Arrays.asList(
                new CSVEntry("BLR,Belarus,Europe,10236000,3520"),
                new CSVEntry("BLZ,Belize,North_America,241000,185"),
                new CSVEntry("ABW,Aruba,North_America,103000,129"),
                new CSVEntry("ASM,American_Samoa,Oceania,68000,54"),
                new CSVEntry("AFG,Afghanistan,Asia,22720000,1")
        ), table2.getBody());
    }

    @Test
    public void testJoin() {
        table1.join(table2, "CountryCode");
        assertEquals(Arrays.asList("CityID", "CityName", "CountryCode", "CityPop", "CountryName", "Continent", "CountryPop", "Capital"),
                table1.getTitle());
        Assert.assertEquals(Arrays.asList(
                new CSVEntry("1,Kabul,AFG,1780000,Afghanistan,Asia,22720000,1"),
                new CSVEntry("2,Qandahar,AFG,237500,Afghanistan,Asia,22720000,1"),
                new CSVEntry("3,Herat,AFG,186800,Afghanistan,Asia,22720000,1"),
                new CSVEntry("4,Mazar-e-Sharif,AFG,127800,Afghanistan,Asia,22720000,1")
        ), table1.getBody());
    }

    @Test
    public void testCountBy() {
        table1.countBy("CountryCode");
        assertEquals(Arrays.asList("CountryCode", "count"), table1.getTitle());
        Assert.assertEquals(Arrays.asList(
                new CSVEntry("AFG,4"),
                new CSVEntry("NLD,1")
        ), table1.getBody());
    }


}
