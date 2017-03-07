package my.project.data;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Calendar;

/**
 * Created by michele on 3/7/17.
 */
public class ReportingHours {
    private Calendar date = Calendar.getInstance();
    private Float hoursReported = new Float("0.0");

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Float getHoursReported() {
        return hoursReported;
    }

    public void setHoursReported(Float hoursReported) {
        this.hoursReported = hoursReported;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ReportingHours that = (ReportingHours) o;

        return new EqualsBuilder()
                .append(date, that.date)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(date)
                .toHashCode();
    }
}
