package my.project.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by michele on 3/9/17.
 */
public class ReportingHoursTestObjectGenerator {
    private static final int MAX_SIZE = 19;

    public static List<ReportingHours> generateRandom() {
        List<ReportingHours> res = new ArrayList<>();
        Random generator = new Random();
        Date date = new Date();

        int nOfElements = generator.nextInt(MAX_SIZE);
        for (int i = 0; i < nOfElements; i++) {
           ReportingHours reportingHours = new ReportingHours();
           reportingHours.setDate(date);
           reportingHours.setHoursReported(new Float(generator.nextInt(8)));
           res.add(reportingHours);
        }
        return res;
    }
}
