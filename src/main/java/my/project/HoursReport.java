package my.project;

import javafx.application.Application;
import javafx.stage.Stage;
import my.project.gui.HoursReportStage;

/**
 * Created by michele on 3/10/17.
 */
public class HoursReport extends Application {

    public static void main( String[] args ) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage = new HoursReportStage();
        primaryStage.show();
    }
}
