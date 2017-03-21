package my.project.data.task;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by michele on 3/9/17.
 */
public class ReportingActivityTest {

    @Test
    public void equalsAndHashcode() throws Exception {
        ReportingActivity reportingActivity = new ReportingActivity();
        reportingActivity.setName("test1");
        reportingActivity.setReportingHoursList(ReportingHoursTestObjectGenerator.generateRandom());

        ReportingActivity reportingActivitySame = new ReportingActivity();
        reportingActivitySame.setName("test1");
        reportingActivitySame.setReportingHoursList(ReportingHoursTestObjectGenerator.generateRandom());

        ReportingActivity reportingActivityDifferent = new ReportingActivity();
        reportingActivityDifferent.setName("test2");
        reportingActivityDifferent.setReportingHoursList(ReportingHoursTestObjectGenerator.generateRandom());

        assertEquals(reportingActivity, reportingActivitySame);
        assertEquals(reportingActivity.hashCode(), reportingActivitySame.hashCode());

        assertNotEquals(reportingActivity, reportingActivityDifferent);
        assertNotEquals(reportingActivity.hashCode(), reportingActivityDifferent.hashCode());
    }

    @Test
    public void reportHours() throws Exception {
        ReportingActivity reportingActivity = new ReportingActivity();
        reportingActivity.setName("test1");
        Date date = new Date();
        reportingActivity.reportHours(date, new Float("1.0"));
        assertEquals(new Float("1.0"), reportingActivity.getReportingHoursList().get(0).getHoursReported());
        reportingActivity.reportHours(date, new Float("3.0"));
        assertEquals(new Float("4.0"), reportingActivity.getReportingHoursList().get(0).getHoursReported());
    }

}
