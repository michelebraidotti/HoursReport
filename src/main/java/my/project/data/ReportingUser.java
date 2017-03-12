package my.project.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;

/**
 * Created by michele on 3/7/17.
 */
public class ReportingUser {
    private String name = "";
    private List<ReportingTask> reportingTaskList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ReportingTask> getReportingTaskList() {
        return reportingTaskList;
    }

    public void setReportingTaskList(List<ReportingTask> reportingTaskList) {
        this.reportingTaskList = reportingTaskList;
    }

    public void reportHours(String taskName, String activityName, Date date, Float amount) {
        ReportingTask reportingTask = findReportingTaskByName(taskName);
        reportingTask.reportHours(activityName, date, amount);
    }

    public ReportingTask findReportingTaskByName(String taskName) {
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

    public Float[] reportHoursPerMonth() {
        Float[] hoursPerMonth = new Float[12];
        for (int i = 0; i < hoursPerMonth.length; i++) {
            hoursPerMonth[i] = new Float("0.0");
        }
        for (ReportingTask reportingTask:getReportingTaskList()) {
            for (ReportingActivity reportingActivity:reportingTask.getReportingActivityList()) {
                for (ReportingHours reportingHours:reportingActivity.getReportingHoursList()) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(reportingHours.getDate());
                    int month = cal.get(Calendar.MONTH);
                    hoursPerMonth[month] = hoursPerMonth[month] + reportingHours.getHoursReported();
                }
            }
        }
        return hoursPerMonth;
    }

    public Map<String, Float> reportHoursPerTask() {
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
