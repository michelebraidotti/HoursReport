package my.project.data.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by michele on 3/9/17.
 */
public class ReportingTaskTestObjectGenerator {
    private static final int MAX_SIZE = 7;

    public static List<ReportingTask> generateRandom() {
        List<ReportingTask> res = new ArrayList<>();
        Random generator = new Random();

        int nOfElements = generator.nextInt(MAX_SIZE);
        for (int i = 0; i < nOfElements; i++) {
            ReportingTask reportingTask = new ReportingTask();
            reportingTask.setName("test" + i);
            reportingTask.setReportingActivityList(ReportingActivityTestObjectGenerator.generateRandom());
            res.add(reportingTask);
        }
        return res;
    }
}
