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
 * Created by michele on 3/21/17.
 */
public class ReportingYearTest {

    private int year = 2016;
    private ReportingYear reportingYear;
    private String testUser = "testUser";
    private int month = Calendar.MARCH;
    private int oneDay = 11;

    @Before
    public void setUp() throws ReportingException {
        reportingYear = new ReportingYear(year, testUser);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, oneDay);
        Date date = new Date();
        date.setTime(cal.getTimeInMillis());
        reportingYear.reportHours("task0","activity0", date, new Float("1.1"));
        reportingYear.reportHours("task0","activity1", date, new Float("2.0"));
        cal.set(Calendar.MONTH, month + 1);
        date.setTime(cal.getTimeInMillis());
        reportingYear.reportHours("task0","activity0", date, new Float("1.0"));
        reportingYear.reportHours("task1","activity0", date, new Float("3.0"));
        cal.set(Calendar.DAY_OF_MONTH, oneDay + 2);
        date.setTime(cal.getTimeInMillis());
        reportingYear.reportHours("task0","activity1", date, new Float("3.0"));
        reportingYear.reportHours("task1","activity0", date, new Float("4.0"));
    }

    @Test
    public void reportHours() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year + 1);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, oneDay);
        Date date = new Date();
        date.setTime(cal.getTimeInMillis());
        try {
            reportingYear.reportHours("task0", "activity0", date, new Float("0.0"));
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
        assertEquals(new Float("3.1"), reportingYear.totalHoursPerDay(date));
        cal.set(Calendar.MONTH, month + 1);
        date.setTime(cal.getTimeInMillis());
        assertEquals(new Float("4.0"), reportingYear.totalHoursPerDay(date));
    }

    @Test
    public void totalHoursPerMonth() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, oneDay);
        assertEquals(new Float("3.1"), reportingYear.totalHoursPerMonth(cal.get(Calendar.MONTH)));
        assertEquals(new Float("11.0"), reportingYear.totalHoursPerMonth(cal.get(Calendar.MONTH ) + 1));
    }

    @Test
    public void equalsAndHashCode() throws Exception {
        ReportingYear sameYear = new ReportingYear(year, testUser);
        ReportingYear otherYear = new ReportingYear(year + 1, testUser);
        ReportingYear yetAnotherYear = new ReportingYear(year, "testUser0");
        assertEquals(reportingYear, sameYear);
        assertNotEquals(reportingYear, otherYear);
        assertNotEquals(reportingYear, yetAnotherYear);
        assertEquals(reportingYear.hashCode(), sameYear.hashCode());
        assertNotEquals(reportingYear.hashCode(), otherYear.hashCode());
        assertNotEquals(reportingYear.hashCode(), yetAnotherYear.hashCode());
    }

}
