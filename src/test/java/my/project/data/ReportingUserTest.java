package my.project.data;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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
    public void reportHoursPerMonth() throws ParseException {
        ReportingUser reportingUser = new ReportingUser();
        reportingUser.setName("Michele");
        DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        reportingUser.reportHours("Holidays", "No activity specified", formatter.parse("Mon Jan 02 00:00:00 CET 2017"), new Float( "4.0"));
        reportingUser.reportHours("Overhead", "No activity specified", formatter.parse("Thu Jan 05 00:00:00 CET 2017"), new Float("2.0"));
        reportingUser.reportHours("12345 - NAME1", "No activity specified", formatter.parse("Mon Jan 02 00:00:00 CET 2017"), new Float("2.0"));
        reportingUser.reportHours("54678 - Name3", "Activity1", formatter.parse("Mon Jan 02 00:00:00 CET 2017"), new Float("1.0"));
        reportingUser.reportHours("54678 - Name3", "Activity1", formatter.parse("Thu Jan 05 00:00:00 CET 2017"), new Float("2.0"));
        reportingUser.reportHours("54678 - Name3", "Activity2", formatter.parse("Wed Jan 04 00:00:00 CET 2017"), new Float("2.0"));
        reportingUser.reportHours("54786 - NAme4", "Activity1", formatter.parse("Fri Jan 06 00:00:00 CET 2017"), new Float("2.0"));
        reportingUser.reportHours("54786 - NAme4", "Activity2", formatter.parse("Wed Jan 04 00:00:00 CET 2017"), new Float("2.0"));
        reportingUser.reportHours("54786 - NAme4", "Activity2", formatter.parse("Fri Jan 06 00:00:00 CET 2017"), new Float("2.0"));
        reportingUser.reportHours("14786 - NAme5", "Activity1", formatter.parse("Thu Jan 05 00:00:00 CET 2017"), new Float("2.0"));
        reportingUser.reportHours("14786 - NAme5", "Activity2", formatter.parse("Thu Jan 05 00:00:00 CET 2017"), new Float("2.0"));
        reportingUser.reportHours("41235 - Name 6", "Activity3", formatter.parse("Mon Jan 02 00:00:00 CET 2017"), new Float("1.0"));
        reportingUser.reportHours("51235 - Name 7", "Activity3", formatter.parse("Tue Jan 03 00:00:00 CET 2017"), new Float("4.0"));
        reportingUser.reportHours("11235 - Name 8", "Activity3", formatter.parse("Wed Jan 04 00:00:00 CET 2017"), new Float("4.0"));
        reportingUser.reportHours("11235 - Name 8", "Activity3", formatter.parse("Fri Jan 06 00:00:00 CET 2017"), new Float("2.0"));
        reportingUser.reportHours("11235 - Name 8", "Activity3", formatter.parse("Mon Dec 11 00:00:00 CET 2017"), new Float("1.2"));

        Float[] hoursPerMonth = reportingUser.reportHoursPerMonth();
        // Jan. hours
        assertEquals(new Float("34.00"), hoursPerMonth[0]);
        // Feb. hours
        assertEquals(new Float("0.00"), hoursPerMonth[1]);
        // Dec. hours
        assertEquals(new Float("1.20"), hoursPerMonth[11]);
    }

    @Test
    public void reportHoursPerTask() throws ParseException {
        ReportingUser reportingUser = new ReportingUser();
        reportingUser.setName("Michele");
        DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        reportingUser.reportHours("12345 - NAME1", "No activity specified", formatter.parse("Mon Jan 02 00:00:00 CET 2017"), new Float("2.0"));
        reportingUser.reportHours("12345 - NAME1", "No activity specified", formatter.parse("Mon Jan 02 00:00:00 CET 2017"), new Float("1.0"));
        reportingUser.reportHours("54786 - NAme4", "Activity1", formatter.parse("Fri Jan 06 00:00:00 CET 2017"), new Float("2.0"));
        reportingUser.reportHours("54786 - NAme4", "Activity2", formatter.parse("Wed Jan 04 00:00:00 CET 2017"), new Float("2.0"));
        reportingUser.reportHours("54786 - NAme4", "Activity2", formatter.parse("Fri Jan 06 00:00:00 CET 2017"), new Float("2.0"));
        reportingUser.reportHours("41235 - Name 6", "Activity3", formatter.parse("Mon Jan 02 00:00:00 CET 2017"), new Float("1.0"));
        reportingUser.reportHours("11235 - Name 8", "Activity3", formatter.parse("Wed Jan 04 00:00:00 CET 2017"), new Float("4.0"));
        reportingUser.reportHours("11235 - Name 8", "Activity3", formatter.parse("Fri Jan 06 00:00:00 CET 2017"), new Float("2.0"));
        reportingUser.reportHours("11235 - Name 8", "Activity3", formatter.parse("Mon Dec 11 00:00:00 CET 2017"), new Float("1.2"));

        Map<String, Float> hoursPerTask = reportingUser.reportHoursPerTask();
        assertEquals(new Float("3.00"), hoursPerTask.get("12345 - NAME1 No activity specified"));
        assertEquals(new Float("2.00"), hoursPerTask.get("54786 - NAme4 Activity1"));
        assertEquals(new Float("4.00"), hoursPerTask.get("54786 - NAme4 Activity2"));
        assertEquals(new Float("1.00"), hoursPerTask.get("41235 - Name 6 Activity3"));
        assertEquals(new Float("7.20"), hoursPerTask.get("11235 - Name 8 Activity3"));
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
