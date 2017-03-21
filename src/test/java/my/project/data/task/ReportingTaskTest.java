package my.project.data.task;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by michele on 3/9/17.
 */
public class ReportingTaskTest {

    @Test
    public void equalsAndHashCode() throws Exception {
        ReportingTask reportingTask = new ReportingTask();
        reportingTask.setName("test1");
        reportingTask.setReportingActivityList(ReportingActivityTestObjectGenerator.generateRandom());

        ReportingTask reportingTaskSame = new ReportingTask();
        reportingTaskSame.setName("test1");
        reportingTaskSame.setReportingActivityList(ReportingActivityTestObjectGenerator.generateRandom());

        ReportingTask reportingTaskDifferent = new ReportingTask();
        reportingTaskDifferent.setName("test2");
        reportingTaskDifferent.setReportingActivityList(ReportingActivityTestObjectGenerator.generateRandom());

        assertEquals(reportingTask, reportingTaskSame);
        assertEquals(reportingTask.hashCode(), reportingTaskSame.hashCode());

        assertNotEquals(reportingTask, reportingTaskDifferent);
        assertNotEquals(reportingTask.hashCode(), reportingTaskDifferent.hashCode());
    }

    @Test
    public void findReportingActivityByName() {
        String activityName = "testActivity1";
        ReportingTask reportingTask = new ReportingTask();
        reportingTask.setReportingActivityList(ReportingActivityTestObjectGenerator.generateRandom());
        ReportingActivity reportingActivity = reportingTask.getReportingActivityList().get(0);
        reportingActivity.setName(activityName);
        assertEquals(reportingActivity, reportingTask.findReportingActivityByName(activityName));
    }

    @Test
    public void reportHours() throws Exception {
        String activityName = "testActivity1";
        ReportingTask reportingTask = new ReportingTask();
        reportingTask.setName("testTask1");
        Date date = new Date();
        reportingTask.reportHours(activityName, date, new Float("3.0"));
        ReportingActivity reportingActivity = reportingTask.findReportingActivityByName(activityName);
        assertEquals(new Float("3.0"), reportingActivity.getReportingHoursList().get(0).getHoursReported());
        reportingTask.reportHours(activityName, date, new Float("2.0"));
        assertEquals(new Float("5.0"), reportingActivity.getReportingHoursList().get(0).getHoursReported());
    }

}
