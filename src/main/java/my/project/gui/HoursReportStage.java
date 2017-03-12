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
import javafx.stage.Stage;
import my.project.data.ReportingUser;
import my.project.parser.MarkusExcelParser;

import java.io.IOException;

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

    private ObservableList<ReportingUser> reportingUsers;

    public HoursReportStage() throws IOException {
        String filePath = "src/test/resources/sample_data/test_Leistungsnachweis_2017.xlsx";
        MarkusExcelParser markusExcelParser = new MarkusExcelParser(filePath);
        markusExcelParser.parse();
        reportingUsers = FXCollections.observableArrayList(markusExcelParser.getReportingUser());

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
        Tab hoursPerMonthTab = new Tab();
        hoursPerMonthTab.setText(REPORT_PER_MONTH_TAB_TITLE);
        HoursPerMonthTableView hoursPerMonthTableView = new HoursPerMonthTableView();
        hoursPerMonthTableView.setItems(reportingUsers);
        VBox hoursPerMonthTableBox = new VBox();
        hoursPerMonthTableBox.setSpacing(5);
        hoursPerMonthTableBox.setPadding(new Insets(10, 10, 10, 10));
        hoursPerMonthTableView.setPrefHeight(700);
        hoursPerMonthTableBox.getChildren().add(hoursPerMonthTableView);
        hoursPerMonthTab.setContent(hoursPerMonthTableBox);
        hoursPerMonthTab.setClosable(false);

        Tab hoursPerTaskTab = new Tab();
        hoursPerTaskTab.setText(REPORT_PER_TASK_TAB_TITLE);
        hoursPerTaskTab.setContent(null);
        hoursPerTaskTab.setClosable(false);

        tabPane.getTabs().addAll(hoursPerMonthTab, hoursPerTaskTab);
        root.add(tabPane, 0, rowNumber++);

        setTitle(MAIN_WINDOW_TITLE_TEXT);
        setScene(new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT));
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

        }
    }
}
