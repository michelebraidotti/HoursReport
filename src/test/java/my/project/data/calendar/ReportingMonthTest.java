package my.project.data.calendar;

import my.project.data.ReportingException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by michele on 3/20/17.
 */
public class ReportingMonthTest {
    private ReportingMonth reportingMonth;
    private int year = 2016;
    private int month = Calendar.JANUARY;
    private int oneDay = 1;
    private int otherDay = 7;
    private int lastDay = 31;
    private String testUser = "testUser";

    @Before
    public void setUp() throws ReportingException {
        reportingMonth = new ReportingMonth(testUser, month, year);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, oneDay);
        Date date = new Date();
        date.setTime(cal.getTimeInMillis());
        reportingMonth.reportHours("task0", "activity0", date, new Float("1.0"));
        reportingMonth.reportHours("task0", "activity1", date, new Float("2.0"));
        cal.set(Calendar.DAY_OF_MONTH, otherDay);
        date.setTime(cal.getTimeInMillis());
        reportingMonth.reportHours("task0", "activity0", date, new Float("3.0"));
        reportingMonth.reportHours("task1", "activity0", date, new Float("3.0"));
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        date.setTime(cal.getTimeInMillis());
        reportingMonth.reportHours("task2", "activity0", date, new Float("99.9"));
    }

    @Test
    public void reportHours() throws Exception {
        assertEquals(testUser,reportingMonth.getReportingUser());
        assertEquals(month,reportingMonth.getMonth());
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month + 1);
        cal.set(Calendar.DAY_OF_MONTH, oneDay);
        Date date = new Date();
        date.setTime(cal.getTimeInMillis());
        try {
            reportingMonth.reportHours("task0", "activity0", date, new Float("3.0"));
            // should not reach this assertion
            assertTrue(false);
        }
        catch (ReportingException e) {
            // we expect the exception to happen
            assertTrue(StringUtils.isNotEmpty(e.getMessage()));
            assertTrue(true);
        }
    }

    /*

        reportingMonth.reportHours("task0", "activity0", date, new Float("1.0"));
        reportingMonth.reportHours("task0", "activity1", date, new Float("2.0"));
        reportingMonth.reportHours("task0", "activity0", date, new Float("3.0"));
        reportingMonth.reportHours("task1", "activity0", date, new Float("3.0"));
        reportingMonth.reportHours("task2", "activity0", date, new Float("99.9"));

     */

    @Test
    public void totalHoursPerTaskActivityPerDay() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, oneDay);
        Date date = new Date();
        date.setTime(cal.getTimeInMillis());
        assertEquals(new Float("1.0"), reportingMonth.totalHoursPerTaskActivityPerDay("task0", "activity0", date));
        assertEquals(new Float("2.0"), reportingMonth.totalHoursPerTaskActivityPerDay("task0", "activity1", date));
        cal.set(Calendar.DAY_OF_MONTH, otherDay);
        date.setTime(cal.getTimeInMillis());
        assertEquals(new Float("3.0"), reportingMonth.totalHoursPerTaskActivityPerDay("task0", "activity0", date));
        assertEquals(new Float("3.0"), reportingMonth.totalHoursPerTaskActivityPerDay("task1", "activity0", date));
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        date.setTime(cal.getTimeInMillis());
        assertEquals(new Float("99.9"), reportingMonth.totalHoursPerTaskActivityPerDay("task2", "activity0", date));
        cal.set(Calendar.MONTH, month + 1);
        date.setTime(cal.getTimeInMillis());
        try {
            reportingMonth.totalHoursPerTaskActivityPerDay("task0", "activity0", date);
            assertTrue(false);
        }
        catch (ReportingException e) {
            assertTrue(StringUtils.isNotEmpty(e.getMessage()));
            assertTrue(true);
        }
    }


    @Test
    public void totalHoursPerDay() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, oneDay);
        Date date = new Date();
        date.setTime(cal.getTimeInMillis());
        assertEquals(new Float("3.0"), reportingMonth.totalHoursPerDay(date));
        cal.set(Calendar.DAY_OF_MONTH, otherDay);
        date.setTime(cal.getTimeInMillis());
        assertEquals(new Float("6.0"), reportingMonth.totalHoursPerDay(date));
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        date.setTime(cal.getTimeInMillis());
        assertEquals(new Float("99.9"), reportingMonth.totalHoursPerDay(date));
        cal.set(Calendar.MONTH, month + 1);
        date.setTime(cal.getTimeInMillis());
        try {
            reportingMonth.totalHoursPerDay(date);
            assertTrue(false);
        }
        catch (ReportingException e) {
            assertTrue(StringUtils.isNotEmpty(e.getMessage()));
            assertTrue(true);
        }
    }

    @Test
    public void totalHours() throws Exception {
        assertEquals(new Float("108.9"), reportingMonth.totalHours());
    }

    @Test
    public void equalsAndHashCode() throws Exception {
        ReportingMonth oneMonth = new ReportingMonth(testUser, month, year);
        ReportingMonth sameMonth = new ReportingMonth(testUser, month, year - 1);
        ReportingMonth otherMonth = new ReportingMonth(testUser, month + 1, year);
        ReportingMonth yetAnotherMonth = new ReportingMonth("differentUser", month, year);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, oneDay);
        Date date = new Date();
        date.setTime(cal.getTimeInMillis());
        sameMonth.reportHours("task0", "activity0", date, new Float("1.0"));
        assertEquals(oneMonth, sameMonth);
        assertNotEquals(oneMonth, otherMonth);
        assertNotEquals(oneMonth, yetAnotherMonth);
        assertEquals(oneMonth.hashCode(), sameMonth.hashCode());
        assertNotEquals(oneMonth.hashCode(), otherMonth.hashCode());
        assertNotEquals(oneMonth.hashCode(), yetAnotherMonth.hashCode());
    }


}
