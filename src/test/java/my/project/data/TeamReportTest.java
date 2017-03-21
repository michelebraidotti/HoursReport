package my.project.data;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by michele on 3/13/17.
 */
public class TeamReportTest {
    private int year = 2017;
    private String userName = "Michele";

    private ReportingUser generateReportingUser(int year, String userName) throws ParseException, ReportingException {
        ReportingUser reportingUser = new ReportingUser(year, userName);
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
        return reportingUser;
    }

    @Test
    public void getTaskNames() throws Exception {
        ReportingUser reportingUser = generateReportingUser(year, userName);
        TeamReport teamReport = new TeamReport();
        teamReport.addReportingUser(reportingUser);
        List<String> taskNames = teamReport.getTaskNames();
        assertEquals(12, taskNames.size());
        assertTrue(taskNames.contains("Holidays No activity specified"));
        assertTrue(taskNames.contains("Overhead No activity specified"));
        assertTrue(taskNames.contains("12345 - NAME1 No activity specified"));
        assertTrue(taskNames.contains("54678 - Name3 Activity1"));
        assertTrue(taskNames.contains("54678 - Name3 Activity2"));
        assertTrue(taskNames.contains("54786 - NAme4 Activity1"));
        assertTrue(taskNames.contains("54786 - NAme4 Activity2"));
        assertTrue(taskNames.contains("14786 - NAme5 Activity1"));
        assertTrue(taskNames.contains("14786 - NAme5 Activity2"));
        assertTrue(taskNames.contains("41235 - Name 6 Activity3"));
        assertTrue(taskNames.contains("51235 - Name 7 Activity3"));
        assertTrue(taskNames.contains("11235 - Name 8 Activity3"));
        assertFalse(taskNames.contains("Holidays"));
        assertFalse(taskNames.contains("Overhead"));
        assertFalse(taskNames.contains("12345 - NAME1"));
        assertFalse(taskNames.contains("54678 - Name3"));
        assertFalse(taskNames.contains("54786 - NAme4"));
        assertFalse(taskNames.contains("14786 - NAme5"));
        assertFalse(taskNames.contains("41235 - Name 6"));
        assertFalse(taskNames.contains("51235 - Name 7"));
        assertFalse(taskNames.contains("11235 - Name 8"));
    }

    @Test
    public void getReportingUsersNames() throws Exception {
        ReportingUser reportingUser = generateReportingUser(year, "Foo");
        ReportingUser reportingUser1 = generateReportingUser(year, "Bar");
        ReportingUser reportingUser2 = generateReportingUser(year, "Bazz");
        List<ReportingUser> reportingUserList = new ArrayList<>();
        reportingUserList.add(reportingUser);
        reportingUserList.add(reportingUser1);
        reportingUserList.add(reportingUser2);
        TeamReport teamReport = new TeamReport();
        teamReport.addAllReportingUsers(reportingUserList);
        List<String> reportingUserNames = teamReport.getReportingUsersNames();
        assertTrue(reportingUserNames.contains(reportingUser.getName()));
        assertTrue(reportingUserNames.contains(reportingUser1.getName()));
        assertTrue(reportingUserNames.contains(reportingUser2.getName()));
        assertEquals(3, reportingUserNames.size());
    }

    @Test
    public void getTaskHours() throws Exception, ReportingException {
        ReportingUser reportingUser = generateReportingUser(year, userName);
        TeamReport teamReport = new TeamReport();
        teamReport.addReportingUser(reportingUser);
        assertEquals(new Float("4.0"), teamReport.getTaskHours("Holidays No activity specified", reportingUser.getName()));
        assertEquals(new Float("2.0"), teamReport.getTaskHours("Overhead No activity specified", reportingUser.getName()));
        assertEquals(new Float("2.0"), teamReport.getTaskHours("12345 - NAME1 No activity specified", reportingUser.getName()));
        assertEquals(new Float("3.0"), teamReport.getTaskHours("54678 - Name3 Activity1", reportingUser.getName()));
        assertEquals(new Float("2.0"), teamReport.getTaskHours("54678 - Name3 Activity2", reportingUser.getName()));
        assertEquals(new Float("2.0"), teamReport.getTaskHours("54786 - NAme4 Activity1", reportingUser.getName()));
        assertEquals(new Float("4.0"), teamReport.getTaskHours("54786 - NAme4 Activity2", reportingUser.getName()));
        assertEquals(new Float("2.0"), teamReport.getTaskHours("14786 - NAme5 Activity1", reportingUser.getName()));
        assertEquals(new Float("2.0"), teamReport.getTaskHours("14786 - NAme5 Activity2", reportingUser.getName()));
        assertEquals(new Float("1.0"), teamReport.getTaskHours("41235 - Name 6 Activity3", reportingUser.getName()));
        assertEquals(new Float("4.0"), teamReport.getTaskHours("51235 - Name 7 Activity3", reportingUser.getName()));
        assertEquals(new Float("7.2"), teamReport.getTaskHours("11235 - Name 8 Activity3", reportingUser.getName()));
    }


}
