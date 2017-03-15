package my.project.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import my.project.data.ReportingUser;

/**
 * Created by michele on 3/15/17.
 */
public class ReportingUserCalendarView extends TabPane {
    private static String[] MONTHS = {
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dic"
    };

    public ReportingUserCalendarView(ReportingUser reportingUser) {
        for (int i = 0; i < MONTHS.length; i++) {
            Tab tab = new Tab(MONTHS[i]);
            tab.setContent(buildReportingUserCalendarTab(reportingUser, i));
            tab.setClosable(false);
            this.getTabs().add(tab);
        }
    }

    private VBox buildReportingUserCalendarTab(ReportingUser reportingUser, int monthNumber) {
        ReportingUserCalendarTableView tableView = new ReportingUserCalendarTableView(reportingUser, monthNumber);
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        tableView.setPrefHeight(700);
        vBox.getChildren().add(tableView);
        return vBox;
    }
}
