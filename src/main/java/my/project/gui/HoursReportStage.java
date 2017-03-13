package my.project.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import my.project.data.ReportingUser;
import my.project.data.ReportingUserException;
import my.project.data.TeamReport;
import my.project.parser.MarkusExcelParser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.apache.poi.hssf.usermodel.HSSFShapeTypes.Rectangle;

/**
 * Created by michele on 3/10/17.
 */
public class HoursReportStage extends Stage {

    private static final String FILE_MENU_TEXT = "File";
    private static final String OPEN_DIR_TEXT = "Open directory";
    private static final String HELP_MENU_TEXT = "Help";
    private static final String OPEN_FILE_TEXT = "Open file";
    private static final String MAIN_WINDOW_TITLE_TEXT = "Hours Report";
    private static final double DEFAULT_WIDTH = 1200;
    private static final double DEFAULT_HEIGHT = 800;
    private static final String REPORT_PER_MONTH_TAB_TITLE = "Hours per month";
    private static final String REPORT_PER_TASK_TAB_TITLE = "Hours per task";

    private ObservableList<ReportingUser> reportingUsers = FXCollections.observableArrayList();
    private TeamReport teamReport = new TeamReport();

    public HoursReportStage() throws IOException {

        // main pane
        GridPane root = new GridPane();
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(100);
        root.getColumnConstraints().addAll(column);
        int rowNumber = 0;

        // menu bar
        root.add(buildMenuBar(), 0, rowNumber++);

        // Tabbed pane
        TabPane tabPane = new TabPane();
        Tab hoursPerMonthTab = buildHoursPerMonthTab();

        Tab hoursPerTaskTab = null;
        try {
            hoursPerTaskTab = buildHoursPerTaskTab();
        } catch (ReportingUserException e) {
            e.printStackTrace();
        }

        tabPane.getTabs().addAll(hoursPerMonthTab, hoursPerTaskTab);
        root.add(tabPane, 0, rowNumber++);

        setTitle(MAIN_WINDOW_TITLE_TEXT);
        setScene(new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }

    private Tab buildHoursPerTaskTab() throws ReportingUserException {
        Tab tab = new Tab();
        tab.setText(REPORT_PER_TASK_TAB_TITLE);
        HoursPerTaskTableView tableView = new HoursPerTaskTableView(teamReport.getReportingUsersNames());
        tableView.setItems(generateDataInMap());
        tab.setContent(tableView);
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        tableView.setPrefHeight(700);
        vBox.getChildren().add(tableView);
        tab.setContent(vBox);
        tab.setClosable(false);
        return tab;
    }
    private ObservableList<Map> generateDataInMap() throws ReportingUserException {
        ObservableList<Map> tableContent = FXCollections.observableArrayList();
        for (String taskName:teamReport.getTaskNames()) {
            Map<String, String> dataRow = new HashMap<>();
            // first col is the task name
            dataRow.put(HoursPerTaskTableView.TASK_COL_NAME, taskName);
            // all other cols is the hours per task per user
            for (String reportingUserName:teamReport.getReportingUsersNames()) {
                dataRow.put(reportingUserName, teamReport.getTaskHours(taskName, reportingUserName).toString());
            }
            tableContent.add(dataRow);
        }
        return tableContent;
    }

    private Tab buildHoursPerMonthTab() {
        Tab tab = new Tab();
        tab.setText(REPORT_PER_MONTH_TAB_TITLE);
        HoursPerMonthTableView tableView = new HoursPerMonthTableView();
        tableView.setItems(reportingUsers);
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        tableView.setPrefHeight(700);
        vBox.getChildren().add(tableView);
        tab.setContent(vBox);
        tab.setClosable(false);
        return tab;
    }

    private MenuBar buildMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu(FILE_MENU_TEXT);

        MenuItem openDirItem = new MenuItem(OPEN_DIR_TEXT);
        openDirItem.setOnAction(new OpenDirAction());

        MenuItem openFileItem = new MenuItem(OPEN_FILE_TEXT);
        openFileItem.setOnAction(new OpenFileAction());

        menuFile.getItems().addAll(openDirItem, openFileItem);
        Menu menuHelp = new Menu(HELP_MENU_TEXT);
        menuBar.getMenus().addAll(menuFile, menuHelp);
        return menuBar;
    }

    private class OpenDirAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

        }
    }

    private class OpenFileAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open time report");
            fileChooser.setInitialDirectory(
                    new File(System.getProperty("user.home"))
            );
            File reportFile = fileChooser.showOpenDialog(HoursReportStage.this);
            MarkusExcelParser markusExcelParser = null;
            try {
                markusExcelParser = new MarkusExcelParser(reportFile.getAbsolutePath());
            } catch (IOException e) {
                HoursRerportAlerts.errorDialog("Problem loading file", e).showAndWait();
            }
            markusExcelParser.parse();
            ReportingUser reportingUser = markusExcelParser.getReportingUser();
            teamReport = new TeamReport();
            teamReport.addReportingUser(reportingUser);
            reportingUsers.clear();
            reportingUsers.add(reportingUser);
        }
    }

}
