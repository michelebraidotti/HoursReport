package my.project.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

/**
 * Created by michele on 3/7/17.
 */
public class ReportingActivity {
    private String name;
    private List<ReportingHours> reportingHoursList;

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
}
