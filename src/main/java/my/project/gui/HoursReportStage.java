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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import my.project.data.ReportingException;
import my.project.data.ReportingUser;
import my.project.data.TeamReport;
import my.project.parser.MarkusExcelParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static final String CALENDAR_REPORT_TAB_TITLE = "Hours per day";
    private static final String ABOUT_MENU_TEXT = "About";



    private ObservableList<ReportingUser> reportingUsers = FXCollections.observableArrayList();
    private Tab hoursPerTaskTab;
    private CalendarReportTabPane calendarReportTabPane;

    public HoursReportStage() throws IOException {

        // main pane
        GridPane root = new GridPane();
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(100);
        root.getColumnConstraints().addAll(column);
        int rowNumber = 0;

        // menu bar
        root.add(buildMenuBar(), 0, rowNumber++);

        TabPane tabPane = new TabPane();
        // hours per month tab
        Tab hoursPerMonthTab = buildHoursPerMonthTab();

        // hours per task tab
        hoursPerTaskTab = new Tab(REPORT_PER_TASK_TAB_TITLE);
        hoursPerTaskTab.setContent(null);
        hoursPerTaskTab.setClosable(false);

        // hours per day tab
        calendarReportTabPane = new CalendarReportTabPane(reportingUsers);
        Tab calendarReportTab = new Tab(CALENDAR_REPORT_TAB_TITLE);
        calendarReportTab.setContent(calendarReportTabPane);
        calendarReportTab.setClosable(false);

        tabPane.getTabs().addAll(hoursPerMonthTab, hoursPerTaskTab, calendarReportTab);
        root.add(tabPane, 0, rowNumber++);

        setTitle(MAIN_WINDOW_TITLE_TEXT);
        setScene(new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }

    private void updateHoursPerTaskTab() throws ReportingException {
        // Create and compute report
        TeamReport teamReport = new TeamReport();
        teamReport.addAllReportingUsers(reportingUsers);
        // Create the table
        HoursPerTaskTableView hoursPerTaskTableView = new HoursPerTaskTableView();
        hoursPerTaskTableView.createColumns(teamReport.getReportingUsersNames());
        // Populating the tables
        ObservableList<Map> hoursPerTaskData = FXCollections.observableArrayList();
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
        hoursPerTaskTableView.setItems(hoursPerTaskData);

        // add to table to tab
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        hoursPerTaskTableView.setPrefHeight(700);
        vBox.getChildren().add(hoursPerTaskTableView);
        hoursPerTaskTab.setContent(vBox);
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

        MenuItem helpItem = new MenuItem(HELP_MENU_TEXT);
        helpItem.setOnAction(new HelpAction());

        MenuItem aboutItem = new MenuItem(ABOUT_MENU_TEXT);
        aboutItem.setOnAction(new AboutAction());

        menuHelp.getItems().addAll(helpItem, aboutItem);

        menuBar.getMenus().addAll(menuFile, menuHelp);
        return menuBar;
    }

    private class HelpAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            HoursRerportAlerts.infoDialog("Help", "Do you need help?", "Well ... there's no help.").showAndWait();
        }
    }

    private class AboutAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            HoursRerportAlerts.infoDialog("About", "Who did this?", "Coders anonymous").showAndWait();
        }
    }

    private class OpenDirAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Open directory report");
            directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            File reportsDir = directoryChooser.showDialog(HoursReportStage.this);
            File[] files = reportsDir.listFiles();
            List<ReportingUser> reportingUserList = new ArrayList<>();
            for (File file:files) {
                parseFile(file);
            }
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
            parseFile(reportFile);
        }
    }

    private void parseFile(File file) {
        MarkusExcelParser markusExcelParser = null;
        try {
            markusExcelParser = new MarkusExcelParser(file.getAbsolutePath());
        } catch (IOException e) {
            HoursRerportAlerts.errorDialog("Problem loading file", e).showAndWait();
        }
        try {
            markusExcelParser.parse();
        } catch (ReportingException e) {
            HoursRerportAlerts.errorDialog("Error during reporting", e);
        }
        ReportingUser reportingUser = markusExcelParser.getReportingUser();
        reportingUsers.add(reportingUser);
        // TODO the following must be replaced by a listener for reporting users
        // "refresh" task view
//        try {
//            updateHoursPerTaskTab();
//        } catch (ReportingException e) {
//            HoursRerportAlerts.errorDialog("Error during reporting", e);
//        }
//        hoursPerDayTab.setContent(updateHoursPerDayTab());
    }

}
