package my.project.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;
import java.util.List;

/**
 * Created by michele on 3/7/17.
 */
public class ReportingTask {
    private String name;
    private List<ReportingActivity> reportingActivityList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ReportingActivity> getReportingActivityList() {
        return reportingActivityList;
    }

    public void setReportingActivityList(List<ReportingActivity> reportingActivityList) {
        this.reportingActivityList = reportingActivityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ReportingTask that = (ReportingTask) o;

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

    public void reportHours(String activityName, Date date, Float amount) {
        ReportingActivity reportingActivity = findReportingActivityByName(activityName);
        reportingActivity.reportHours(date, amount);
    }

    private ReportingActivity findReportingActivityByName(String activiyName) {
        ReportingActivity reportingActivity = null;
        for (ReportingActivity activity:getReportingActivityList()) {
            if ( activity.getName().equals(activiyName) ) {
                return activity;
            }
        }
        reportingActivity = new ReportingActivity();
        reportingActivity.setName(activiyName);
        getReportingActivityList().add(reportingActivity);
        return reportingActivity;
    }
}
