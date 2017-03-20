package my.project.data.calendar;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by michele on 3/20/17.
 */
public class ReportingMonthTest {
    // TODO TODO TODO

    @Test
    public void reportHours() throws Exception {
        int year = 2016;
        int month = Calendar.JANUARY;
        ReportingMonth reportingMonth = new ReportingMonth("testUser", month, year);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        Date date = new Date();
        date.setTime(cal.getTimeInMillis());
        reportingMonth.reportHours("task0", "activiy0", date, new Float("1.0"));

    }

    @Test
    public void totalHoursPerDay() throws Exception {

    }

    @Test
    public void totalHours() throws Exception {

    }

    @Test
    public void equalsAndHashCode() throws Exception {

    }


}
