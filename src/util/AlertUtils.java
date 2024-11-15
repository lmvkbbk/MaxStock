package util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AlertUtils {

    public void error(String title, String headerText,String message, String inputDetails) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);

        if (message != null && inputDetails != null) {
            alert.setContentText(message + "\n\nDetalhes: " + inputDetails);
        } else if (message != null) {
            alert.setContentText(message);
        } else if (inputDetails != null) {
            alert.setContentText("Detalhes: " + inputDetails);
        }

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/Resources/MaxStockLogo.png"));

        alert.getDialogPane().setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12px;");

        alert.showAndWait();
    }

    public void information(String title, String headerText, String message, String inputDetails) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);

        if (message != null && inputDetails != null) {
            alert.setContentText(message + "\n\nDetalhes: " + inputDetails);
        } else if (message != null) {
            alert.setContentText(message);
        } else if (inputDetails != null) {
            alert.setContentText("Detalhes: " + inputDetails);
        }

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/Resources/MaxStockLogo.png"));

        alert.getDialogPane().setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12px;");

        alert.showAndWait();
    }

    public void warning(String title, String headerText, String message, String inputDetails) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        if (message != null && inputDetails != null) {
            alert.setContentText(message + "\n\nDetalhes: " + inputDetails);
        } else if (message != null) {
            alert.setContentText(message);
        } else if (inputDetails != null) {
            alert.setContentText("Detalhes: " + inputDetails);
        }
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/Resources/MaxStockLogo.png"));

        alert.getDialogPane().setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12px;");

        alert.showAndWait();
    }
}
