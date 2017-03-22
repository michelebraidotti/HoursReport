package my.project.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.paint.Color;
import my.project.data.task.ReportingActivity;
import my.project.data.task.ReportingTask;
import my.project.data.ReportingUser;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by michele on 3/15/17.
 */
public class MonthReportTableView extends TableView {
    private static final String TASK_COL_NAME = "Task";
    private final int year;
    private final int month;
    private ObservableList<Map> data = FXCollections.observableArrayList();

    public MonthReportTableView(int year, int month) {
        this.year = year;
        this.month = month;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        // Create first col
        TableColumn<Map, String> taskNameColumn = new TableColumn<>(TASK_COL_NAME);
        taskNameColumn.setCellValueFactory(new MapValueFactory(TASK_COL_NAME));
        taskNameColumn.setMinWidth(100);
        this.getColumns().add(taskNameColumn);
        // Create one col per day in the month
        while ( month==cal.get(Calendar.MONTH) ) {
            TableColumn<Map, String> column = new TableColumn<>(getColName(cal));
            column.setCellValueFactory(new MapValueFactory(getColName(cal)));
            column.setMinWidth(50);
            int dayOfTheWeek = cal.get(Calendar.DAY_OF_WEEK);
            column.setCellFactory(c -> { return new CalendarViewCellFactory(dayOfTheWeek); });
            getColumns().add(column);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    public void setData(ReportingUser reportingUser) {
        Calendar cal = Calendar.getInstance();
        for (ReportingTask reportingTask:reportingUser.getReportingTaskList()) {
            for (ReportingActivity reportingActivity:reportingTask.getReportingActivityList()) {
                Map<String, String> dataRow = new HashMap<>();
                dataRow.put(TASK_COL_NAME, reportingTask.getName() + " " + reportingActivity.getName());
                cal.set(Calendar.DAY_OF_MONTH, 1);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.YEAR, reportingUser.getYear());
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                while (month == cal.get(Calendar.MONTH)) {
                    dataRow.put(getColName(cal), reportingActivity.getHoursReportedByDate(cal.getTime()).toString());
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                }
                data.add(dataRow);
            }
        }
        this.setItems(data);
    }

    private String getColName(Calendar cal) {
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        return  day + "/" + month;
    }

    private class CalendarViewCellFactory extends TableCell<Map, String> {
        int dayOfTheWeek;

        public CalendarViewCellFactory(int dayOfTheWeek) {
            this.dayOfTheWeek = dayOfTheWeek;
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setText(null);
                setStyle("");
            } else {
                setText(item);
                if (dayOfTheWeek == Calendar.SATURDAY || dayOfTheWeek == Calendar.SUNDAY) {
                    setTextFill(Color.ORANGE);
                    setStyle("-fx-background-color: pink ");
                } else if (item.equals("0.0")) {
                    setTextFill(Color.GRAY);
                } else {
                    // no special looks applied
                }
            }
        }
    }
}


