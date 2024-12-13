package uk.ac.nott.cs.comp2013.froggergame.view.managers;

import javafx.scene.control.Alert;

public class AlertManager {
    public static void showGameWonAlert(int points) {
        showAlert("You Have Won The Game!", "Congratulations!", "Your High Score: " + points);
    }

    public static void showGameOverAlert() {
        showAlert("Game Over", "Try Again", "Press SPACE to restart...");
    }

    private static void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }
}
