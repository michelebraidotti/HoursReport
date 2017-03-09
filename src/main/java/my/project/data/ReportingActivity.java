package my.project.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by michele on 3/7/17.
 */
public class ReportingActivity {
    private String name = "";
    private List<ReportingHours> reportingHoursList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ReportingHours> getReportingHoursList() {
        return reportingHoursList;
    }

    public void setReportingHoursList(List<ReportingHours> reportingHoursList) {
        this.reportingHoursList = reportingHoursList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ReportingActivity that = (ReportingActivity) o;

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

    public void reportHours(Date date, Float amount) {
        ReportingHours reportingHours = findReportingHours(date);
        reportingHours.setHoursReported(reportingHours.getHoursReported() + amount);
    }

    private ReportingHours findReportingHours(Date date) {
        for (ReportingHours hours:getReportingHoursList()) {
            if ( hours.getDate().equals(date) ) {
                return hours;
            }
        }
        ReportingHours reportingHours = new ReportingHours();
        reportingHours.setDate(date);
        getReportingHoursList().add(reportingHours);
        return reportingHours;
    }
}
