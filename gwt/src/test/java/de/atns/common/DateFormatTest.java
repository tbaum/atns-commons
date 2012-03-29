package de.atns.common;

import org.gwttime.time.DateMidnight;
import org.gwttime.time.DateTime;
import org.junit.Test;

import static de.atns.common.gwt.datefilter.client.DateUtils.*;
import static org.junit.Assert.assertEquals;

/**
 * @author mwolter
 * @since 16.03.12 10:19
 */
public class DateFormatTest {

    @Test
    public void testClearTime() throws Exception {
        assertEquals(new DateMidnight(2012, 1, 1), clearTime(new DateTime(2012, 1, 1, 0, 0)));
        assertEquals(new DateMidnight(2012, 1, 1), clearTime(new DateTime(2012, 1, 1, 1, 0)));
        assertEquals(new DateMidnight(2012, 1, 1), clearTime(new DateTime(2012, 1, 1, 23, 0)));
        assertEquals(new DateMidnight(2012, 1, 1), clearTime(new DateTime(2012, 1, 1, 2, 59)));
        assertEquals(new DateMidnight(2012, 1, 1), clearTime(new DateTime(2012, 1, 1, 23, 59)));
    }

    @Test
    public void testFirstDayOfMonth() throws Exception {
        assertEquals(new DateMidnight(2012, 1, 1), getFirstDayOfMonth(new DateMidnight(2012, 1, 3)));
        assertEquals(new DateMidnight(2011, 2, 1), getFirstDayOfMonth(new DateMidnight(2011, 2, 3)));
        assertEquals(new DateMidnight(2012, 2, 1), getFirstDayOfMonth(new DateMidnight(2012, 2, 29)));
        assertEquals(new DateMidnight(2012, 3, 1), getFirstDayOfMonth(new DateMidnight(2012, 3, 3)));
        assertEquals(new DateMidnight(2012, 6, 1), getFirstDayOfMonth(new DateMidnight(2012, 6, 3)));
        assertEquals(new DateMidnight(2012, 8, 1), getFirstDayOfMonth(new DateMidnight(2012, 8, 3)));
        assertEquals(new DateMidnight(2012, 10, 1), getFirstDayOfMonth(new DateMidnight(2012, 10, 3)));
        assertEquals(new DateMidnight(2012, 12, 1), getFirstDayOfMonth(new DateMidnight(2012, 12, 3)));
    }

    @Test
    public void testFirstDayOfQuarter() throws Exception {
        assertEquals(new DateMidnight(2012, 1, 1), getFirstDayOfQuarter(new DateMidnight(2012, 1, 1)));
        assertEquals(new DateMidnight(2012, 1, 1), getFirstDayOfQuarter(new DateMidnight(2012, 3, 31)));
        assertEquals(new DateMidnight(2012, 4, 1), getFirstDayOfQuarter(new DateMidnight(2012, 4, 1)));
        assertEquals(new DateMidnight(2012, 4, 1), getFirstDayOfQuarter(new DateMidnight(2012, 6, 30)));
        assertEquals(new DateMidnight(2012, 7, 1), getFirstDayOfQuarter(new DateMidnight(2012, 8, 1)));
        assertEquals(new DateMidnight(2012, 7, 1), getFirstDayOfQuarter(new DateMidnight(2012, 8, 31)));
        assertEquals(new DateMidnight(2012, 7, 1), getFirstDayOfQuarter(new DateMidnight(2012, 9, 1)));
        assertEquals(new DateMidnight(2012, 7, 1), getFirstDayOfQuarter(new DateMidnight(2012, 9, 30)));
        assertEquals(new DateMidnight(2012, 10, 1), getFirstDayOfQuarter(new DateMidnight(2012, 10, 1)));
        assertEquals(new DateMidnight(2012, 10, 1), getFirstDayOfQuarter(new DateMidnight(2012, 10, 31)));
        assertEquals(new DateMidnight(2012, 10, 1), getFirstDayOfQuarter(new DateMidnight(2012, 11, 1)));
        assertEquals(new DateMidnight(2012, 10, 1), getFirstDayOfQuarter(new DateMidnight(2012, 12, 31)));
    }

    @Test
    public void testFirstDayOfWeek() throws Exception {
        assertEquals(new DateMidnight(2011, 12, 26), getFirstDayOfWeek(new DateMidnight(2012, 1, 1)));
        assertEquals(new DateMidnight(2012, 1, 2), getFirstDayOfWeek(new DateMidnight(2012, 1, 2)));
        assertEquals(new DateMidnight(2012, 1, 2), getFirstDayOfWeek(new DateMidnight(2012, 1, 3)));
        assertEquals(new DateMidnight(2012, 1, 2), getFirstDayOfWeek(new DateMidnight(2012, 1, 4)));
        assertEquals(new DateMidnight(2012, 1, 2), getFirstDayOfWeek(new DateMidnight(2012, 1, 5)));
        assertEquals(new DateMidnight(2012, 1, 2), getFirstDayOfWeek(new DateMidnight(2012, 1, 6)));
        assertEquals(new DateMidnight(2012, 1, 2), getFirstDayOfWeek(new DateMidnight(2012, 1, 7)));
        assertEquals(new DateMidnight(2012, 1, 2), getFirstDayOfWeek(new DateMidnight(2012, 1, 8)));
        assertEquals(new DateMidnight(2012, 1, 9), getFirstDayOfWeek(new DateMidnight(2012, 1, 9)));
    }

    @Test
    public void testLastDayOfMonth() throws Exception {
        assertEquals(new DateMidnight(2012, 1, 31), getLastDayOfMonth(new DateMidnight(2012, 1, 3)));
        assertEquals(new DateMidnight(2011, 2, 28), getLastDayOfMonth(new DateMidnight(2011, 2, 3)));
        assertEquals(new DateMidnight(2012, 2, 29), getLastDayOfMonth(new DateMidnight(2012, 2, 1)));
        assertEquals(new DateMidnight(2012, 2, 29), getLastDayOfMonth(new DateMidnight(2012, 2, 29)));
        assertEquals(new DateMidnight(2012, 3, 31), getLastDayOfMonth(new DateMidnight(2012, 3, 3)));
        assertEquals(new DateMidnight(2012, 6, 30), getLastDayOfMonth(new DateMidnight(2012, 6, 3)));
        assertEquals(new DateMidnight(2012, 8, 31), getLastDayOfMonth(new DateMidnight(2012, 8, 3)));
        assertEquals(new DateMidnight(2012, 10, 31), getLastDayOfMonth(new DateMidnight(2012, 10, 3)));
        assertEquals(new DateMidnight(2012, 12, 31), getLastDayOfMonth(new DateMidnight(2012, 12, 3)));
    }

    @Test
    public void testLastDayOfQuarter() throws Exception {
        assertEquals(new DateMidnight(2012, 3, 31), getLastDayOfQuarter(new DateMidnight(2012, 1, 1)));
        assertEquals(new DateMidnight(2012, 3, 31), getLastDayOfQuarter(new DateMidnight(2012, 3, 31)));
        assertEquals(new DateMidnight(2012, 6, 30), getLastDayOfQuarter(new DateMidnight(2012, 4, 1)));
        assertEquals(new DateMidnight(2012, 6, 30), getLastDayOfQuarter(new DateMidnight(2012, 6, 30)));
        assertEquals(new DateMidnight(2012, 9, 30), getLastDayOfQuarter(new DateMidnight(2012, 8, 1)));
        assertEquals(new DateMidnight(2012, 9, 30), getLastDayOfQuarter(new DateMidnight(2012, 8, 31)));
        assertEquals(new DateMidnight(2012, 9, 30), getLastDayOfQuarter(new DateMidnight(2012, 9, 1)));
        assertEquals(new DateMidnight(2012, 9, 30), getLastDayOfQuarter(new DateMidnight(2012, 9, 30)));
        assertEquals(new DateMidnight(2012, 12, 31), getLastDayOfQuarter(new DateMidnight(2012, 10, 1)));
        assertEquals(new DateMidnight(2012, 12, 31), getLastDayOfQuarter(new DateMidnight(2012, 10, 31)));
        assertEquals(new DateMidnight(2012, 12, 31), getLastDayOfQuarter(new DateMidnight(2012, 11, 1)));
        assertEquals(new DateMidnight(2012, 12, 31), getLastDayOfQuarter(new DateMidnight(2012, 12, 31)));
    }

    @Test
    public void testLastDayOfWeek() throws Exception {
        assertEquals(new DateMidnight(2012, 1, 1), getLastDayOfWeek(new DateMidnight(2012, 1, 1)));
        assertEquals(new DateMidnight(2012, 1, 8), getLastDayOfWeek(new DateMidnight(2012, 1, 2)));
        assertEquals(new DateMidnight(2012, 1, 8), getLastDayOfWeek(new DateMidnight(2012, 1, 3)));
        assertEquals(new DateMidnight(2012, 1, 8), getLastDayOfWeek(new DateMidnight(2012, 1, 4)));
        assertEquals(new DateMidnight(2012, 1, 8), getLastDayOfWeek(new DateMidnight(2012, 1, 5)));
        assertEquals(new DateMidnight(2012, 1, 8), getLastDayOfWeek(new DateMidnight(2012, 1, 6)));
        assertEquals(new DateMidnight(2012, 1, 8), getLastDayOfWeek(new DateMidnight(2012, 1, 7)));
        assertEquals(new DateMidnight(2012, 1, 8), getLastDayOfWeek(new DateMidnight(2012, 1, 8)));
        assertEquals(new DateMidnight(2012, 1, 15), getLastDayOfWeek(new DateMidnight(2012, 1, 9)));
    }
}
