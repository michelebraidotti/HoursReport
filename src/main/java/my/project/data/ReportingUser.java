package my.project.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;
import java.util.List;

/**
 * Created by michele on 3/7/17.
 */
public class ReportingUser {
    private String name = "";
    private List<ReportingTask> reportingTaskList;

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

    private ReportingTask findReportingTaskByName(String taskName) {
        ReportingTask reportingTask = null;
        for (ReportingTask task:getReportingTaskList()) {
            if ( task.getName().equals(taskName) ) {
                return reportingTask;
            }
        }
        reportingTask = new ReportingTask();
        reportingTask.setName(taskName);
        getReportingTaskList().add(reportingTask);
        return reportingTask;
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
