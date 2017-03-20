package my.project.data.calendar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michele on 3/20/17.
 */
public class ReportingDay {
    private int day;
    private List<Task> taskList = new ArrayList<>();

    public ReportingDay(int day) {
        this.day = day;
    }

    public void reportHours(String taskName, String activityName, Float hours) {
        Task task = findTask(taskName);
        Activity activity = task.findActivity(activityName);
        activity.hours = hours;
    }

    public Float totalHours() {
        Float total = new Float("0.0");
        for (Task task:taskList) {
            for (Activity activity:task.activityList) {
                total = total + activity.hours;
            }
        }
        return total;
    }

    private Task findTask(String taskName) {
        for (Task task:taskList) {
            if (task.name.equals(taskName))
                return task;
        }
        Task task = new Task();
        task.name = taskName;
        taskList.add(task);
        return task;
    }

    private class Task {
        public String name;
        public List<Activity> activityList = new ArrayList<>();

        public Activity findActivity(String acivityName) {
            for (Activity activity:activityList) {
                if ( activity.name.equals(acivityName) ) {
                    return activity;
                }
            }
            Activity activity = new Activity();
            activity.name = acivityName;
            activityList.add(activity);
            return activity;
        }

    }

    private class Activity {
        public String name;
        public Float hours = new Float("0.0");
    }
}
