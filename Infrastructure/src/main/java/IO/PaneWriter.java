package IO;

import IO.contracts.UserWriter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class PaneWriter implements UserWriter {
    @Override
    public void showMessage(String message) {
        var alert = new Alert(AlertType.INFORMATION, message);
        alert.setTitle("Game over!");
        alert.setHeaderText("Congratulations!");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                });
    }

    @Override
    public void showField(String field) {

    }
}
