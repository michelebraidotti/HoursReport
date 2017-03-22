package my.project.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import my.project.data.ReportingException;
import my.project.data.ReportingUser;
import my.project.data.TeamReport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by michele on 3/13/17.
 */
public class HoursPerTaskTableView extends TableView {
    public static final String TASK_COL_NAME = "Tasks";
    private ObservableList<Map> hoursPerTaskData = FXCollections.observableArrayList();

    public void createColumns(List<String> reportingUsersNames) {
        TableColumn<Map, String> taskNameColumn = new TableColumn<>(TASK_COL_NAME);
        taskNameColumn.setCellValueFactory(new MapValueFactory(TASK_COL_NAME));
        taskNameColumn.setMinWidth(100);
        this.getColumns().add(taskNameColumn);
        for (String reportingUserName:reportingUsersNames) {
            TableColumn<Map, String> column = new TableColumn<>(reportingUserName);
            column.setCellValueFactory(new MapValueFactory(reportingUserName));
            column.setMinWidth(100);
            this.getColumns().add(column);
        }
        setItems(hoursPerTaskData);
    }

    public void setHoursPerTaskData(TeamReport teamReport) throws ReportingException {
        for (String taskName:teamReport.getTaskNames()) {
            Map<String, String> dataRow = new HashMap<>();
            // first col is the task name
            dataRow.put(HoursPerTaskTableView.TASK_COL_NAME, taskName);
            // all other cols is the hours per task per user
            for (String reportingUserName:teamReport.getReportingUsersNames()) {
                dataRow.put(reportingUserName, teamReport.getTaskHours(taskName, reportingUserName).toString());
            }
            hoursPerTaskData.add(dataRow);
        }
    }
}
