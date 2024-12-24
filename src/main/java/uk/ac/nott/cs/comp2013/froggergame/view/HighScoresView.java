package uk.ac.nott.cs.comp2013.froggergame.view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;
import java.util.Objects;

public class HighScoresView {
    private final Stage primaryStage;
    private final List<String[]> scores;
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 800;
    private static final String BACKGROUND_COLOR = "#2F2F5E";

    public HighScoresView(Stage primaryStage, List<String[]> scores) {
        this.primaryStage = primaryStage;
        this.scores = scores;
    }

    public void show() {
        BorderPane rootLayout = new BorderPane();
        rootLayout.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");

        ImageView banner = createHeaderBanner();
        rootLayout.setTop(banner);
        BorderPane.setAlignment(banner, Pos.TOP_CENTER);

        GridPane scoresLayout = createScoresLayout();
        rootLayout.setCenter(scoresLayout);

        Button newGameButton = createNewGameButton();
        rootLayout.setBottom(newGameButton);
        BorderPane.setAlignment(newGameButton, Pos.CENTER);

        Scene scene = new Scene(rootLayout, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ImageView createHeaderBanner() {
        ImageView banner = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/banner.png")).toExternalForm()));
        banner.setFitWidth(WINDOW_WIDTH);
        banner.setPreserveRatio(true);
        return banner;
    }

    private GridPane createScoresLayout() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(15);
        gridPane.setHgap(50);

        Text nameHeader = new Text("NAME");
        nameHeader.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        nameHeader.setFill(Color.YELLOW);
        GridPane.setHalignment(nameHeader, HPos.LEFT);
        gridPane.add(nameHeader, 0, 0);

        Text scoreHeader = new Text("SCORE");
        scoreHeader.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        scoreHeader.setFill(Color.YELLOW);
        GridPane.setHalignment(scoreHeader, HPos.RIGHT);
        gridPane.add(scoreHeader, 1, 0);

        if (scores == null || scores.isEmpty()) {
            Text noScores = new Text("COULD NOT ACCESS SCORES.");
            noScores.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            noScores.setFill(Color.YELLOW);
            gridPane.add(noScores, 0, 1, 2, 1);
        } else {
            int row = 1;
            for (String[] score : scores) {
                Text nameText = new Text(score[0]);
                nameText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
                nameText.setFill(Color.WHITE);
                GridPane.setHalignment(nameText, HPos.LEFT);
                gridPane.add(nameText, 0, row);

                Text scoreText = new Text(score[1]);
                scoreText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
                scoreText.setFill(Color.WHITE);
                GridPane.setHalignment(scoreText, HPos.RIGHT);
                gridPane.add(scoreText, 1, row);

                row++;
            }
        }

        return gridPane;
    }

    private Button createNewGameButton() {
        Button newGameButton = new Button("NEW GAME");
        newGameButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        newGameButton.setTextFill(Color.LIMEGREEN);
        newGameButton.setStyle("-fx-background-color: black; -fx-border-color: limegreen; -fx-border-width: 2px;");
        newGameButton.setOnMouseEntered(event -> newGameButton.setTextFill(Color.YELLOW));
        newGameButton.setOnMouseExited(event -> newGameButton.setTextFill(Color.LIMEGREEN));
        newGameButton.setOnAction(event -> startNewGame());
        BorderPane.setMargin(newGameButton, new Insets(-30, 0, 100, 0));
        return newGameButton;
    }

    private void startNewGame() {
        MainMenuView mainMenuView = new MainMenuView(primaryStage);
        mainMenuView.show();
    }
}
