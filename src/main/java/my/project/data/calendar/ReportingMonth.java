package my.project.data.calendar;

import my.project.data.ReportingActivity;
import my.project.data.ReportingException;
import my.project.data.ReportingHours;
import my.project.data.ReportingTask;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by michele on 3/20/17.
 */
public class ReportingMonth {
    private String reportingUser;
    private int month; // Must correspond to one of Calendar.MONTH
    private List<ReportingDay> reportingDays = new ArrayList<>();

    public ReportingMonth(String reportingUser, int month, int year) {
        this.reportingUser = reportingUser;
        this.month = month;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        while (month == cal.get(Calendar.MONTH)) {
            reportingDays.add(new ReportingDay(cal.get(Calendar.DAY_OF_MONTH)));
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    public String getReportingUser() {
        return reportingUser;
    }

    public int getMonth() {
        return month;
    }

    public void reportHours(String taskName, String activityName, Date day, Float amount) throws ReportingException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        if (cal.get(Calendar.MONTH) !=  month) {
            throw new ReportingException("Month mismatch. Trying to report '" + taskName + " " + activityName
                    + "' to month " + cal.get(Calendar.MONTH) + " but object refers to month " + month);
        }
        ReportingDay reportingDay = reportingDays.get(cal.get(Calendar.DAY_OF_MONTH));
        reportingDay.reportHours(taskName, activityName, amount);
    }

    public Float totalHoursPerDay(Date day) throws ReportingException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        if (cal.get(Calendar.MONTH) != month) {
            throw new ReportingException("Month mismatch. Trying to compute hours for month " + cal.get(Calendar.MONTH)
                    + " but object refers to month " + month);
        }
        ReportingDay reportingDay = reportingDays.get(cal.get(Calendar.DAY_OF_MONTH));
        return reportingDay.totalHours();
    }

    public Float totalHours() {
        Float total = new Float("0.0");
        for (ReportingDay reportingDay:reportingDays) {
            total = total + reportingDay.totalHours();
        }
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ReportingMonth that = (ReportingMonth) o;

        return new EqualsBuilder()
                .append(month, that.month)
                .append(reportingUser, that.reportingUser)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(reportingUser)
                .append(month)
                .toHashCode();
    }
}


