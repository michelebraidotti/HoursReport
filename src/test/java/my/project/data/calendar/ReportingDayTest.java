package my.project.data.calendar;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by michele on 3/20/17.
 */
public class ReportingDayTest {

    private ReportingDay reportingDay;
    private int day = 11;

    @Before
    public void setUp() {
        reportingDay = new ReportingDay(day);
        reportingDay.reportHours("task0", "activty0", new Float("1.2"));
        reportingDay.reportHours("task0", "activty1", new Float("2.0"));
        reportingDay.reportHours("task1", "activty0", new Float("0.1"));
    }

    @Test
    public void reportHours() throws Exception {
        assertEquals(day, reportingDay.getDay());
    }

    @Test
    public void totalHours() throws Exception {
        assertEquals(new Float("3.3"), reportingDay.totalHours());
    }

    @Test
    public void equalsAndHashCode() throws Exception {
        ReportingDay sameDay = new ReportingDay(day);
        ReportingDay otherDay = new ReportingDay(day + 1);
        assertEquals(reportingDay, sameDay);
        assertNotEquals(reportingDay, otherDay);
        assertEquals(reportingDay.hashCode(), sameDay.hashCode());
        assertNotEquals(reportingDay.hashCode(), otherDay.hashCode());
    }
}