package my.project.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by michele on 3/10/17.
 */
public class TeamReport {
    private List<ReportingUser> reportingUsers = new ArrayList<>();

    public void addHours(ReportingUser reportingUser) {
        reportingUsers.add(reportingUser);
    }

    public void addAllHours(List<ReportingUser>reportingUsers) {
        this.reportingUsers.addAll(reportingUsers);
    }

    public List<String> getTaskNames() {
        List<String> taskNames = new ArrayList<>();
        for (ReportingUser reportingUser:reportingUsers) {
            for (ReportingTask reportingTask:reportingUser.getReportingTaskList()) {
                taskNames.add(reportingTask.getName());
            }
        }
        return taskNames;
    }

    public List<String> getReportingUsersNames() {
        List<String> reportingUsersNames = new ArrayList<>();
        for (ReportingUser reportingUser:reportingUsers) {
            reportingUsersNames.add(reportingUser.getName());
        }
        return reportingUsersNames;
    }

    public Float getTaskHours(String task, String userName) {

    }


}
