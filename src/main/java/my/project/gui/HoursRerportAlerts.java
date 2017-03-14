package my.project.gui;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Created by michele on 3/13/17.
 */
public class HoursRerportAlerts {

    public static Alert errorDialog(String header, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(e.getMessage());
        return alert;
    }

    public static Alert infoDialog(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        return alert;
    }
}
