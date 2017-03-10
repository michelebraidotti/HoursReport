package my.project.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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

    public HoursReportStage() {
        // main pane
        GridPane root = new GridPane();
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(100);
        root.getColumnConstraints().addAll(column);
        int rowNumber = 0;

        // menu bar
        root.add(buildMenuBar(), 0, rowNumber++);
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
