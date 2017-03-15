package my.project.gui;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.List;
import java.util.Map;

/**
 * Created by michele on 3/13/17.
 */
public class HoursPerTaskTableView extends TableView {

    public static final String TASK_COL_NAME = "Tasks";


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
            Callback<TableColumn<Map, String>, TableCell<Map, String>>
                    cellFactoryForMap = (TableColumn<Map, String> p) ->
                    new TextFieldTableCell(new StringConverter() {
                        @Override
                        public String toString(Object t) {
                            return t.toString();
                        }
                        @Override
                        public Object fromString(String string) {
                            return string;
                        }
                    });
            column.setCellFactory(cellFactoryForMap);
        }
    }
}
