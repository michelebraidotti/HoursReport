package my.project.data;

import my.project.data.calendar.ReportingYear;
import my.project.data.task.ReportingActivity;
import my.project.data.task.ReportingHours;
import my.project.data.task.ReportingTask;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;

/**
 * Created by michele on 3/7/17.
 */
public class ReportingUser {
    private String name = "";
    private int year = 0;
    private List<ReportingTask> reportingTaskList = new ArrayList<>();
    private ReportingYear reportingYear;

    public ReportingUser(int year, String userName) {
        this.year = year;
        this.name = userName;
        reportingYear = new ReportingYear(year, userName);
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public List<ReportingTask> getReportingTaskList() {
        return reportingTaskList;
    }

    public void reportHours(String taskName, String activityName, Date date, Float amount) throws ReportingException {
        ReportingTask reportingTask = findReportingTaskByName(taskName);
        reportingTask.reportHours(activityName, date, amount);
        reportingYear.reportHours(taskName, activityName, date, amount);
    }

    private ReportingTask findReportingTaskByName(String taskName) {
        for (ReportingTask task:getReportingTaskList()) {
            if ( task.getName().equals(taskName) ) {
                return task;
            }
        }
        ReportingTask reportingTask = new ReportingTask();
        reportingTask.setName(taskName);
        getReportingTaskList().add(reportingTask);
        return reportingTask;
    }

    public Float totalHoursPerMonth(int month) {
        return reportingYear.totalHoursPerMonth(month);
    }

    public Map<String, Float> totalHoursPerTask() {
        Map<String, Float> hoursPerTask = new HashMap<>();
        for (ReportingTask reportingTask:getReportingTaskList()) {
            for (ReportingActivity reportingActivity:reportingTask.getReportingActivityList()) {
                String mapKey = reportingTask.getName() + " " + reportingActivity.getName();
                for (ReportingHours reportingHours:reportingActivity.getReportingHoursList()) {
                    if ( hoursPerTask.containsKey(mapKey) ) {
                        hoursPerTask.put(mapKey, hoursPerTask.get(mapKey) + reportingHours.getHoursReported());
                    }
                    else {
                        hoursPerTask.put(mapKey, reportingHours.getHoursReported());
                    }
                }
            }
        }
        return hoursPerTask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ReportingUser that = (ReportingUser) o;

        return new EqualsBuilder()
                .append(name, that.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .toHashCode();
    }
}
