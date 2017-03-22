package my.project.data.calendar;

import my.project.data.ReportingException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by michele on 3/20/17.
 */
public class ReportingYear {
    private int year;
    private String reportingUser;
    private ReportingMonth[] reportingMonths;

    public ReportingYear(int year, String reportingUser) {
        this.year = year;
        this.reportingUser = reportingUser;
        reportingMonths = new ReportingMonth[12];
        for ( int i = Calendar.JANUARY; i <= Calendar.DECEMBER; i++ ) {
            reportingMonths[i] = new ReportingMonth(reportingUser, i, year);
        }
    }

    public void reportHours(String taskName, String activityName, Date day, Float amount) throws ReportingException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        if ( cal.get(Calendar.YEAR) !=  year ) {
            throw new ReportingException("Year mismatch. Trying to report '" + taskName + " " + activityName
                    + "' to year " + cal.get(Calendar.YEAR) + " but object refers to year " + year);
        }
        else {
            reportingMonths[cal.get(Calendar.MONTH)].reportHours(taskName, activityName, day, amount);
        }
    }

    public Float totalHoursPerTaskActivityPerDay(String taskName, String activityName, Date day) throws ReportingException {
        Calendar cal = convert(day);
        return reportingMonths[cal.get(Calendar.MONTH)].totalHoursPerTaskActivityPerDay(taskName, activityName, day);
    }

    public Float totalHoursPerDay(Date day) throws ReportingException {
        Calendar cal = convert(day);
        return reportingMonths[cal.get(Calendar.MONTH)].totalHoursPerDay(day);
    }

    public Float totalHoursPerMonth(int month) {
        return reportingMonths[month].totalHours();
    }


    private Calendar convert(Date day) throws ReportingException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        if ( cal.get(Calendar.YEAR) !=  year ) {
            throw new ReportingException("Year mismatch. Trying to compute hours for year " + cal.get(Calendar.YEAR)
                    + " but object refers to year " + year);
        }
        return cal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ReportingYear that = (ReportingYear) o;

        return new EqualsBuilder()
                .append(year, that.year)
                .append(reportingUser, that.reportingUser)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(year)
                .append(reportingUser)
                .toHashCode();
    }
}
