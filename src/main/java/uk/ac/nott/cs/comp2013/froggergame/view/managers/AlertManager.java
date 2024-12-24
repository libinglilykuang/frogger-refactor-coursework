package uk.ac.nott.cs.comp2013.froggergame.view.managers;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;

public class AlertManager {
    public static void showGameWonAlert(int points) {
        showAlert("You Have Won The Game!", "Congratulations!", "Your High Score: " + points);
    }

    public static void showGameOverAlert(MyStage primaryStage) {
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);
        Rectangle background = new Rectangle(250, 50);
        background.setFill(Color.BLACK);
        background.setArcWidth(10);
        background.setArcHeight(10);

        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFill(Color.RED);
        gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        root.getChildren().addAll(background, gameOverText);
        root.setLayoutX(200);
        root.setLayoutY(430);
        primaryStage.getChildren().add(root);
        primaryStage.requestLayout();
    }

    private static void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }
}
