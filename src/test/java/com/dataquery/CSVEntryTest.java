package com.dataquery;

import com.dataquery.data.CSVEntry;
import com.dataquery.data.Entry;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CSVEntryTest {
    private Entry entryFromString;

    private Entry entryFromString2;

    private Entry entryFromList;

    @Before
    public void setup() {
        entryFromString = new CSVEntry("1,Kabul,AFG,1780000");
        entryFromString2 = new CSVEntry("1,Kabul,AFG,1780000");
        entryFromList = new CSVEntry(new ArrayList<>(Arrays.asList("232", "Gaboatâ€žo_dos_Guararapes", "BRA", "558680")));
    }

    /**
     * Test case: when index is out of column boundary
     */
    @Test
    public void testGetValueAt_OutOfBoundary() {
        assertEquals("", entryFromString.getValueAt(4));
        assertEquals("", entryFromList.getValueAt(-1));
    }

    @Test
    public void testGetValueAt() {
        assertEquals("AFG", entryFromString.getValueAt(2));
        assertEquals("558680", entryFromList.getValueAt(3));
    }

    /**
     * Test case: when index is out of column boundary
     */
    @Test
    public void testRemoveCol_OutOfBoundary() {
        entryFromList.removeCol(-1);
        assertEquals(Arrays.asList("232", "Gaboatâ€žo_dos_Guararapes", "BRA", "558680"),
                        entryFromList.getRow());

        entryFromString.removeCol(4);
        assertEquals(Arrays.asList("1", "Kabul", "AFG", "1780000"),
                        entryFromString.getRow());
    }

    @Test
    public void testRemoveCol() {
        entryFromList.removeCol(3);
        assertEquals(Arrays.asList("232", "Gaboatâ€žo_dos_Guararapes", "BRA"),
                            entryFromList.getRow());
        entryFromString.removeCol(0);
        assertEquals(Arrays.asList("Kabul", "AFG", "1780000"),
                        entryFromString.getRow());
    }

    @Test
    public void testCompareBy_Numeric() {
        // smaller than
        assertEquals(1, Entry.compareBy(entryFromString, entryFromList, 0));
        // equal to
        assertEquals(0, Entry.compareBy(entryFromString, entryFromString2, 0));
        // greater than
        assertEquals(-1, Entry.compareBy(entryFromString, entryFromList, 3));
    }

    @Test
    public void testCompareBy_Non_Numeric() {
        // smaller than
        assertTrue(Entry.compareBy(entryFromString, entryFromList, 1) > 0);
        // equal to
        assertEquals(0, Entry.compareBy(entryFromString, entryFromString2, 0));
        // greater than
        assertTrue(Entry.compareBy(entryFromString, entryFromList, 2) < 0);
    }
}
