package uk.ac.nott.cs.comp2013.froggergame.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;
import java.util.Objects;

import uk.ac.nott.cs.comp2013.froggergame.controller.MainMenuController;

/**
 * View for displaying high scores in the Frogger game.
 */
public class HighScoresView {
    private final Stage primaryStage;
    private final List<String[]> scores;
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 800;

    public HighScoresView(Stage primaryStage, List<String[]> scores) {
        this.primaryStage = primaryStage;
        this.scores = (scores == null) ? List.of() : scores;
    }

    public void show() {
        BorderPane rootLayout = new BorderPane();
        rootLayout.getStyleClass().add("root-layout");

        ImageView banner = createHeaderBanner();
        rootLayout.setTop(banner);
        BorderPane.setAlignment(banner, Pos.TOP_CENTER);

        VBox scoresLayout = createScoresLayout();
        rootLayout.setCenter(scoresLayout);

        HBox newGameButtonBox = createNewGameButtonBox();
        rootLayout.setBottom(newGameButtonBox);
        BorderPane.setAlignment(newGameButtonBox, Pos.CENTER);

        Scene scene = new Scene(rootLayout, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/highscores.css")).toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ImageView createHeaderBanner() {
        ImageView banner = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/banner.png")).toExternalForm()));
        banner.getStyleClass().add("header-banner");
        return banner;
    }

    private VBox createScoresLayout() {
        VBox scoresLayout = new VBox();
        scoresLayout.getStyleClass().add("scores-layout");

        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("grid-layout");

        Text nameHeader = new Text("NAME");
        nameHeader.getStyleClass().add("header-text");
        gridPane.add(nameHeader, 0, 0);

        Text scoreHeader = new Text("SCORE");
        scoreHeader.getStyleClass().add("header-text");
        gridPane.add(scoreHeader, 1, 0);

        if (scores == null || scores.isEmpty()) {
            Text noScores = new Text("COULD NOT ACCESS SCORES.");
            noScores.getStyleClass().add("no-scores-text");
            scoresLayout.getChildren().add(noScores);
        } else {
            int row = 1;
            for (String[] score : scores) {
                Text nameText = new Text(score[0]);
                nameText.getStyleClass().add("score-text");
                gridPane.add(nameText, 0, row);

                Text scoreText = new Text(score[1]);
                scoreText.getStyleClass().add("score-text");
                GridPane.setHalignment(scoreText, javafx.geometry.HPos.RIGHT);
                gridPane.add(scoreText, 1, row);

                row++;
            }
            scoresLayout.getChildren().add(gridPane);
        }

        return scoresLayout;
    }

    private HBox createNewGameButtonBox() {
        Button newGameButton = new Button("NEW GAME");
        newGameButton.getStyleClass().add("new-game-button");
        newGameButton.setOnAction(event -> startNewGame());

        HBox buttonBox = new HBox(newGameButton);
        buttonBox.getStyleClass().add("button-box");
        return buttonBox;
    }

    private void startNewGame() {
        MainMenuController mainMenuController = new MainMenuController(primaryStage);
        MainMenuView mainMenuView = new MainMenuView(primaryStage, mainMenuController);
        mainMenuView.show();
    }
}
