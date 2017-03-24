package my.project.gui;

import com.sun.org.apache.xalan.internal.utils.XMLSecurityPropertyManager;
import javafx.beans.NamedArg;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import my.project.data.ReportingException;
import my.project.data.ReportingUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by michele on 3/13/17.
 */
public class HoursPerTaskTableView extends TableView {
    public static final String TASK_COL_NAME = "Tasks";
    private ObservableList<Map> hoursPerTaskData = FXCollections.observableArrayList();
    private ObservableList<ReportingUser> reportingUsers;

    public HoursPerTaskTableView(ObservableList<ReportingUser> reportingUsers) {
        this.reportingUsers = reportingUsers;
        this.reportingUsers.addListener((ListChangeListener<ReportingUser>) c -> {
            while (c.next()) {
                if ( c.wasAdded() ) {
                    for (ReportingUser reportingUser:c.getAddedSubList()) {
                        try {
                            addColumn(reportingUser);
                        } catch (ReportingException e) {
                            HoursRerportAlerts.errorDialog("Error biuilding reporting task table", e);
                        }
                    }
                }
                else if ( c.wasRemoved() ) {
                    for (ReportingUser reportingUser : c.getRemoved()) {
                        try {
                            removeColumn(reportingUser);
                        } catch (ReportingException e) {
                            HoursRerportAlerts.errorDialog("Error biuilding reporting task table", e);
                        }
                    }
                }
                else {
                    // we don't care.
                }
            }
        });
        TableColumn<Map, String> taskNameColumn = new TableColumn<>(TASK_COL_NAME);
        taskNameColumn.setCellValueFactory(new MapValueFactory(TASK_COL_NAME));
        taskNameColumn.setMinWidth(100);
        this.getColumns().add(taskNameColumn);
        setItems(hoursPerTaskData);
    }

    private void removeColumn(ReportingUser reportingUser) throws ReportingException {
        this.getColumns().remove(reportingUser.getName());
        resetTableContent(reportingUser);
    }

    private void addColumn(ReportingUser reportingUser) throws ReportingException {
        TableColumn<Map, String> column = new TableColumn<>(reportingUser.getName());
        column.setCellValueFactory(new MapValueFactory(reportingUser.getName()));
        column.setMinWidth(100);
        this.getColumns().add(column);
        // we need to "refresh the whole table
        resetTableContent(reportingUser);
    }

    private void resetTableContent(ReportingUser reportingUser) throws ReportingException {
        hoursPerTaskData.removeAll(hoursPerTaskData);
        for (String taskName:getTaskNames()) {
            Map<String, String> dataRow = new HashMap<>();
            // first col is the task name
            dataRow.put(HoursPerTaskTableView.TASK_COL_NAME, taskName);
            // all other cols is the hours per task per user
            for (String reportingUserName:getReportingUsersNames()) {
                dataRow.put(reportingUserName, getTaskHours(taskName, reportingUserName).toString());
            }
            hoursPerTaskData.add(dataRow);
        }
    }

    private List<String> getTaskNames() {
        List<String> taskNames = new ArrayList<>();
        for (ReportingUser reportingUser:reportingUsers) {
            taskNames.addAll(reportingUser.totalHoursPerTask().keySet());
        }
        return taskNames;
    }

    private List<String> getReportingUsersNames() {
        List<String> reportingUsersNames = new ArrayList<>();
        for (ReportingUser reportingUser:reportingUsers) {
            reportingUsersNames.add(reportingUser.getName());
        }
        return reportingUsersNames;
    }

    private Float getTaskHours(String task, String userName) throws ReportingException {
        Map<String, Float> hoursPerTask = getReportingUser(userName).totalHoursPerTask();
        if ( hoursPerTask.containsKey(task)) {
            return hoursPerTask.get(task);
        }
        return new Float("0.0");
    }

    private ReportingUser getReportingUser(String userName) throws ReportingException {
        for (ReportingUser reportingUser:reportingUsers) {
            if (reportingUser.getName().equals(userName)) {
                return reportingUser;
            }
        }
        throw  new ReportingException("User " + userName + " not found.");
    }
}
