package my.project.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by michele on 3/9/17.
 */
public class ReportingActivityTestObjectGenerator {
    private static final int MAX_SIZE = 11;

    public static List<ReportingActivity> generateRandom() {
        List<ReportingActivity> res = new ArrayList<>();
        Random generator = new Random();

        int nOfElements = generator.nextInt(MAX_SIZE);
        for (int i = 0; i < nOfElements; i++) {
            ReportingActivity reportingActivity = new ReportingActivity();
            reportingActivity.setName("test" + i);
            reportingActivity.setReportingHoursList(ReportingHoursTestObjectGenerator.generateRandom());
            res.add(reportingActivity);
        }
        return res;
    }
}
