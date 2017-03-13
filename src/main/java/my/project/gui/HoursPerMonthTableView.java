package my.project.gui;


import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import my.project.data.ReportingUser;

import java.util.List;

/**
 * Created by michele on 3/11/17.
 */
public class HoursPerMonthTableView extends TableView {

    public HoursPerMonthTableView() {

        TableColumn reporterNameCol = new TableColumn("Reporter");
        reporterNameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReportingUser, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ReportingUser, String> r) {
                return new ReadOnlyObjectWrapper(r.getValue().getName());
            }
        });
        reporterNameCol.setMinWidth(100);

        TableColumn janHoursCol = new TableColumn("Jan");
        janHoursCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReportingUser, Float>, ObservableValue<Float>>() {
            public ObservableValue<Float> call(TableColumn.CellDataFeatures<ReportingUser, Float> r) {
                Float[] res = r.getValue().reportHoursPerMonth();
                return new ReadOnlyObjectWrapper(res[0]);
            }
        });
        janHoursCol.setMinWidth(20);

        TableColumn febHoursCol = new TableColumn("Feb");
        febHoursCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReportingUser, Float>, ObservableValue<Float>>() {
            public ObservableValue<Float> call(TableColumn.CellDataFeatures<ReportingUser, Float> r) {
                Float[] res = r.getValue().reportHoursPerMonth();
                return new ReadOnlyObjectWrapper(res[1]);
            }
        });
        febHoursCol.setMinWidth(20);

        TableColumn marHoursCol = new TableColumn("Mar");
        marHoursCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReportingUser, Float>, ObservableValue<Float>>() {
            public ObservableValue<Float> call(TableColumn.CellDataFeatures<ReportingUser, Float> r) {
                Float[] res = r.getValue().reportHoursPerMonth();
                return new ReadOnlyObjectWrapper(res[2]);
            }
        });
        marHoursCol.setMinWidth(20);

        TableColumn aprHoursCol = new TableColumn("Apr");
        aprHoursCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReportingUser, Float>, ObservableValue<Float>>() {
            public ObservableValue<Float> call(TableColumn.CellDataFeatures<ReportingUser, Float> r) {
                Float[] res = r.getValue().reportHoursPerMonth();
                return new ReadOnlyObjectWrapper(res[3]);
            }
        });
        aprHoursCol.setMinWidth(20);

        TableColumn mayHoursCol = new TableColumn("May");
        mayHoursCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReportingUser, Float>, ObservableValue<Float>>() {
            public ObservableValue<Float> call(TableColumn.CellDataFeatures<ReportingUser, Float> r) {
                Float[] res = r.getValue().reportHoursPerMonth();
                return new ReadOnlyObjectWrapper(res[4]);
            }
        });
        mayHoursCol.setMinWidth(20);

        TableColumn junHoursCol = new TableColumn("Jun");
        junHoursCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReportingUser, Float>, ObservableValue<Float>>() {
            public ObservableValue<Float> call(TableColumn.CellDataFeatures<ReportingUser, Float> r) {
                Float[] res = r.getValue().reportHoursPerMonth();
                return new ReadOnlyObjectWrapper(res[5]);
            }
        });
        junHoursCol.setMinWidth(20);

        TableColumn julHoursCol = new TableColumn("Jul");
        julHoursCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReportingUser, Float>, ObservableValue<Float>>() {
            public ObservableValue<Float> call(TableColumn.CellDataFeatures<ReportingUser, Float> r) {
                Float[] res = r.getValue().reportHoursPerMonth();
                return new ReadOnlyObjectWrapper(res[6]);
            }
        });
        julHoursCol.setMinWidth(20);

        TableColumn augHoursCol = new TableColumn("Aug");
        augHoursCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReportingUser, Float>, ObservableValue<Float>>() {
            public ObservableValue<Float> call(TableColumn.CellDataFeatures<ReportingUser, Float> r) {
                Float[] res = r.getValue().reportHoursPerMonth();
                return new ReadOnlyObjectWrapper(res[7]);
            }
        });
        augHoursCol.setMinWidth(20);

        TableColumn septHoursCol = new TableColumn("Sep");
        septHoursCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReportingUser, Float>, ObservableValue<Float>>() {
            public ObservableValue<Float> call(TableColumn.CellDataFeatures<ReportingUser, Float> r) {
                Float[] res = r.getValue().reportHoursPerMonth();
                return new ReadOnlyObjectWrapper(res[8]);
            }
        });
        septHoursCol.setMinWidth(20);

        TableColumn octHoursCol = new TableColumn("Oct");
        octHoursCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReportingUser, Float>, ObservableValue<Float>>() {
            public ObservableValue<Float> call(TableColumn.CellDataFeatures<ReportingUser, Float> r) {
                Float[] res = r.getValue().reportHoursPerMonth();
                return new ReadOnlyObjectWrapper(res[9]);
            }
        });
        octHoursCol.setMinWidth(20);

        TableColumn novHoursCol = new TableColumn("Nov");
        novHoursCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReportingUser, Float>, ObservableValue<Float>>() {
            public ObservableValue<Float> call(TableColumn.CellDataFeatures<ReportingUser, Float> r) {
                Float[] res = r.getValue().reportHoursPerMonth();
                return new ReadOnlyObjectWrapper(res[10]);
            }
        });
        novHoursCol.setMinWidth(20);

        TableColumn decHoursCol = new TableColumn("Dec");
        decHoursCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ReportingUser, Float>, ObservableValue<Float>>() {
            public ObservableValue<Float> call(TableColumn.CellDataFeatures<ReportingUser, Float> r) {
                Float[] res = r.getValue().reportHoursPerMonth();
                return new ReadOnlyObjectWrapper(res[11]);
            }
        });
        decHoursCol.setMinWidth(20);

        getColumns().addAll(reporterNameCol, janHoursCol, febHoursCol,
                marHoursCol, aprHoursCol, mayHoursCol, junHoursCol,
                julHoursCol, augHoursCol, septHoursCol, octHoursCol,
                novHoursCol, decHoursCol);
    }

}
