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

    public int getDay() {
        return day;
    }

    public void reportHours(String taskName, String activityName, Float hours) {
        Task task = findTask(taskName);
        Activity activity = task.findActivity(activityName);
        activity.hours = hours;
    }

    //public void addHours(String taskName, String activityName, Float hours) {}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportingDay that = (ReportingDay) o;

        return day == that.day;
    }

    @Override
    public int hashCode() {
        return day;
    }

    private class Task {
        public String name;
        public List<Activity> activityList = new ArrayList<>();

        public Activity findActivity(String activityName) {
            for (Activity activity:activityList) {
                if ( activity.name.equals(activityName) ) {
                    return activity;
                }
            }
            Activity activity = new Activity();
            activity.name = activityName;
            activityList.add(activity);
            return activity;
        }

    }

    private class Activity {
        public String name;
        public Float hours = new Float("0.0");
    }
}
