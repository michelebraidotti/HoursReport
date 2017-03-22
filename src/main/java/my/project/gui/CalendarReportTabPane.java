package my.project.gui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import my.project.data.ReportingUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by michele on 3/22/17.
 */
public class CalendarReportTabPane extends TabPane {
    private ObservableList<ReportingUser> reportingUsers;

    public CalendarReportTabPane(ObservableList<ReportingUser> reportingUsers) {
        this.reportingUsers = reportingUsers;
        reportingUsers.addListener((ListChangeListener<ReportingUser>) c -> {
            while (c.next()) {
                if ( c.wasAdded() ) {
                    for (ReportingUser reportingUser:c.getAddedSubList()) {
                        addReportingUserTab(reportingUser);
                    }
                }
                else if ( c.wasRemoved() ) {
                    for (ReportingUser reportingUser : c.getRemoved()) {
                        removeReportingUserTab(reportingUser);
                    }
                }
                else {
                    // we don't care.
                }
            }
        });
    }

    private void removeReportingUserTab(ReportingUser reportingUser) {
        for (Tab tab:this.getTabs()) {
            if ( tab.getText().equals(reportingUser.getName()) ) {
                this.getTabs().remove(tab);
            }
        }
    }

    private void addReportingUserTab(ReportingUser reportingUser) {
        Tab tab = new Tab(reportingUser.getName());
        tab.setContent(buildCalendarViewTabPane(reportingUser));
        tab.setClosable(false);
        this.getTabs().add(tab);
    }

    private TabPane buildCalendarViewTabPane(ReportingUser reportingUser) {
        TabPane tabPane = new TabPane();
        for (int i = Calendar.JANUARY; i <= Calendar.DECEMBER; i++) {
            MonthReportTableView tableView = new MonthReportTableView(reportingUser.getYear(), i);
            tableView.setData(reportingUser);
            VBox vBox = new VBox();
            vBox.setSpacing(5);
            vBox.setPadding(new Insets(10, 10, 10, 10));
            tableView.setPrefHeight(700);
            vBox.getChildren().add(tableView);

            Tab tab = new Tab(monthName(i));
            tab.setContent(vBox);
            tab.setClosable(false);
            tabPane.getTabs().add(tab);
        }
        return tabPane;
    }

    private String monthName(int i) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, i);
        return new SimpleDateFormat("MMM").format(cal.getTime());
    }

}
