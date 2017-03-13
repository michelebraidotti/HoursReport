package my.project.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by michele on 3/10/17.
 */
public class TeamReport {
    private List<ReportingUser> reportingUsers = new ArrayList<>();

    public void addReportingUser(ReportingUser reportingUser) {
        reportingUsers.add(reportingUser);
    }

    public void addAllReportingUsers(List<ReportingUser>reportingUsers) {
        this.reportingUsers.addAll(reportingUsers);
    }

    public List<String> getTaskNames() {
        List<String> taskNames = new ArrayList<>();
        for (ReportingUser reportingUser:reportingUsers) {
            taskNames.addAll(reportingUser.reportHoursPerTask().keySet());
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

    public Float getTaskHours(String task, String userName) throws ReportingUserException {
        Map<String, Float> hoursPerTask = getReportingUser(userName).reportHoursPerTask();
        if ( hoursPerTask.containsKey(task)) {
            return hoursPerTask.get(task);
        }
        return new Float("0.0");
    }

    private ReportingUser getReportingUser(String userName) throws ReportingUserException {
        for (ReportingUser reportingUser:reportingUsers) {
            if (reportingUser.getName().equals(userName)) {
                return reportingUser;
            }
        }
        throw  new ReportingUserException("User " + userName + " not found.");
    }
}
