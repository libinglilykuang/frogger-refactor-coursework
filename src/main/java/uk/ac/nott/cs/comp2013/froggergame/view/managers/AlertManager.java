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
    public static void showGameWonAlert(MyStage primaryStage, int points) {
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);

        Rectangle background = new Rectangle(300, 100);
        background.setFill(Color.DARKGREEN);
        background.setArcWidth(15);
        background.setArcHeight(15);

        Text titleText = new Text("YOU WON!");
        titleText.setFill(Color.WHITE);
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        Text scoreText = new Text("Your High Score: " + points);
        scoreText.setFill(Color.LIGHTYELLOW);
        scoreText.setFont(Font.font("Arial", FontWeight.NORMAL, 20));

        StackPane messagePane = new StackPane();
        messagePane.setAlignment(Pos.TOP_CENTER);
        StackPane.setAlignment(titleText, Pos.TOP_CENTER);
        StackPane.setAlignment(scoreText, Pos.BOTTOM_CENTER);

        root.getChildren().addAll(background, titleText, scoreText);
        root.setLayoutX(170);
        root.setLayoutY(320);

        primaryStage.getChildren().add(root);
        primaryStage.requestLayout();
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
