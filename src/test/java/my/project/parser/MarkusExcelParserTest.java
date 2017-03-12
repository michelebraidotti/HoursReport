package my.project.parser;

import my.project.data.ReportingActivity;
import my.project.data.ReportingHours;
import my.project.data.ReportingTask;
import my.project.data.ReportingUser;
import org.junit.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by michele on 3/9/17.
 */
public class MarkusExcelParserTest {

    @Test
    public void parse() throws IOException, ParseException {
        String filePath = "src/test/resources/sample_data/test_Leistungsnachweis_2017.xlsx";
        ReportingUser expectedUser = expectedReportingUser();
        MarkusExcelParser markusExcelParser = new MarkusExcelParser(filePath);
        markusExcelParser.parse();
        ReportingUser resultUser = markusExcelParser.getReportingUser();
        prettyPrint(resultUser);

        assertEquals(expectedUser, resultUser);
        assertEquals(expectedUser.getReportingTaskList().size(), resultUser.getReportingTaskList().size());

        for (ReportingTask expectedReportingTask:expectedUser.getReportingTaskList()) {
            assertEquals(expectedReportingTask, resultUser.findReportingTaskByName(expectedReportingTask.getName()));
            for (ReportingActivity expectedReportingActivity:expectedReportingTask.getReportingActivityList()) {
                assertEquals(expectedReportingActivity,
                        resultUser
                                .findReportingTaskByName(expectedReportingTask.getName())
                                .findReportingActivityByName(expectedReportingActivity.getName()));
                for (ReportingHours expectedReportingHours:expectedReportingActivity.getReportingHoursList()) {
                    List<ReportingHours> resultReportingHours = resultUser
                            .findReportingTaskByName(expectedReportingTask.getName())
                            .findReportingActivityByName(expectedReportingActivity.getName()).getReportingHoursList();
                    for (ReportingHours resulExporingHours:resultReportingHours) {
                        if (expectedReportingHours.getDate().equals(resulExporingHours.getDate())) {
                            assertEquals(expectedReportingHours.getHoursReported(), resulExporingHours.getHoursReported());
                        }
                    }
                }
            }
        }
    }

    private ReportingUser expectedReportingUser() throws ParseException {
        ReportingUser expectedUser = new ReportingUser();
        expectedUser.setName("Michele");
        DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        expectedUser.reportHours("Holidays", "No activity specified", formatter.parse("Mon Jan 02 00:00:00 CET 2017"), new Float( "4.0"));
        expectedUser.reportHours("Overhead", "No activity specified", formatter.parse("Thu Jan 05 00:00:00 CET 2017"), new Float("2.0"));
        expectedUser.reportHours("12345 - NAME1", "No activity specified", formatter.parse("Mon Jan 02 00:00:00 CET 2017"), new Float("2.0"));
        expectedUser.reportHours("54678 - Name3", "Activity1", formatter.parse("Mon Jan 02 00:00:00 CET 2017"), new Float("1.0"));
        expectedUser.reportHours("54678 - Name3", "Activity1", formatter.parse("Thu Jan 05 00:00:00 CET 2017"), new Float("2.0"));
        expectedUser.reportHours("54678 - Name3", "Activity2", formatter.parse("Wed Jan 04 00:00:00 CET 2017"), new Float("2.0"));
        expectedUser.reportHours("54786 - NAme4", "Activity1", formatter.parse("Fri Jan 06 00:00:00 CET 2017"), new Float("2.0"));
        expectedUser.reportHours("54786 - NAme4", "Activity2", formatter.parse("Wed Jan 04 00:00:00 CET 2017"), new Float("2.0"));
        expectedUser.reportHours("54786 - NAme4", "Activity2", formatter.parse("Fri Jan 06 00:00:00 CET 2017"), new Float("2.0"));
        expectedUser.reportHours("14786 - NAme5", "Activity1", formatter.parse("Thu Jan 05 00:00:00 CET 2017"), new Float("2.0"));
        expectedUser.reportHours("14786 - NAme5", "Activity2", formatter.parse("Thu Jan 05 00:00:00 CET 2017"), new Float("2.0"));
        expectedUser.reportHours("41235 - Name 6", "Activity3", formatter.parse("Mon Jan 02 00:00:00 CET 2017"), new Float("1.0"));
        expectedUser.reportHours("51235 - Name 7", "Activity3", formatter.parse("Tue Jan 03 00:00:00 CET 2017"), new Float("4.0"));
        expectedUser.reportHours("11235 - Name 8", "Activity3", formatter.parse("Wed Jan 04 00:00:00 CET 2017"), new Float("4.0"));
        expectedUser.reportHours("11235 - Name 8", "Activity3", formatter.parse("Fri Jan 06 00:00:00 CET 2017"), new Float("2.0"));
        return  expectedUser;
    }

    private void prettyPrint(ReportingUser reportingUser) {
        System.out.print(reportingUser.getName() + "\n");
        for (ReportingTask reportingTask:reportingUser.getReportingTaskList()) {
            for (ReportingActivity reportingActivity:reportingTask.getReportingActivityList()) {
                System.out.print(reportingTask.getName() + ", " + reportingActivity.getName() + ": ");
                for (ReportingHours reportingHours:reportingActivity.getReportingHoursList()) {
                    System.out.print("(" + reportingHours.getDate() + ", " + reportingHours.getHoursReported() + ") ");
                }
                System.out.print("\n");
            }
        }
    }
}
