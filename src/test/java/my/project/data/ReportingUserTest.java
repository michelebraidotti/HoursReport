package my.project.data;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by michele on 3/9/17.
 */
public class ReportingUserTest {
    @Test
    public void reportHours() throws Exception {
        ReportingUser reportingUser = new ReportingUser();
        reportingUser.setName("testUser1");
        Date date = new Date();
        String taskName = "task1";
        String activityName = "activity";
        reportingUser.reportHours(taskName, activityName, date, new Float("1.0"));
        ReportingActivity reportingActivity = reportingUser
                .findReportingTaskByName(taskName)
                .findReportingActivityByName(activityName);
        assertEquals(new Float("1.0"), reportingActivity.getReportingHoursList().get(0).getHoursReported());
        reportingUser.reportHours(taskName, activityName, date, new Float("2.0"));
        assertEquals(new Float("3.0"), reportingActivity.getReportingHoursList().get(0).getHoursReported());
    }

    @Test
    public void findReportingTaskByName() throws Exception {
        ReportingUser reportingUser = new ReportingUser();
        reportingUser.setName("testUser1");
        reportingUser.setReportingTaskList(ReportingTaskTestObjectGenerator.generateRandom());
        ReportingTask reportingTask = reportingUser.getReportingTaskList().get(0);
        String taskName = "testTask1";
        reportingTask.setName(taskName);
        assertEquals(reportingTask, reportingUser.findReportingTaskByName(taskName));
    }

    @Test
    public void equalsAndHashCode() {
        ReportingUser reportingUser = new ReportingUser();
        reportingUser.setName("testUser1");
        reportingUser.setReportingTaskList(ReportingTaskTestObjectGenerator.generateRandom());

        ReportingUser reportingUserSame = new ReportingUser();
        reportingUserSame.setName("testUser1");
        reportingUserSame.setReportingTaskList(ReportingTaskTestObjectGenerator.generateRandom());

        ReportingUser reportingUserDifferent = new ReportingUser();
        reportingUserDifferent.setName("testUser2");
        reportingUserDifferent.setReportingTaskList(ReportingTaskTestObjectGenerator.generateRandom());

        assertEquals(reportingUser, reportingUserSame);
        assertEquals(reportingUser.hashCode(), reportingUserSame.hashCode());

        assertNotEquals(reportingUser, reportingUserDifferent);
        assertNotEquals(reportingUser.hashCode(), reportingUserDifferent.hashCode());
    }
}
