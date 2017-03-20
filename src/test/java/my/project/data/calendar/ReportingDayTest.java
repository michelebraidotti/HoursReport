package my.project.data.calendar;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by michele on 3/20/17.
 */
public class ReportingDayTest {

    @Test
    public void reportHoursAndTotalHours() throws Exception {
        ReportingDay reportingDay = new ReportingDay(11);
        reportingDay.reportHours("task0", "activty0", new Float("1.2"));
        reportingDay.reportHours("task0", "activty1", new Float("2.0"));
        reportingDay.reportHours("task1", "activty0", new Float("0.1"));
        assertEquals(new Float("3.3"), reportingDay.totalHours());
    }

}
