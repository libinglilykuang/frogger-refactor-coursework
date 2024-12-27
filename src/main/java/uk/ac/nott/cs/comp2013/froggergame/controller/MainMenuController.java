package uk.ac.nott.cs.comp2013.froggergame.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.nott.cs.comp2013.froggergame.view.GameView;
import uk.ac.nott.cs.comp2013.froggergame.view.HighScoresView;

import java.util.List;

/**
 * Controller for the main menu, handling actions such as starting the game, showing high scores, and quitting the game.
 */
public class MainMenuController {
    private final Stage primaryStage;
    private GameView gameView;
    private GameController gameController;

    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 800;

    public MainMenuController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void startGame() {
        initializeGame();

        gameView.startBackground();
        gameView.playBackgroundMusic();
        gameController.startGameLoop();
    }

    private void initializeGame() {
        gameView = new GameView();
        gameController = GameControllerFactory.createGameController(gameView.getGameStage());
        Scene scene = new Scene(gameView, WINDOW_WIDTH, WINDOW_HEIGHT);
        gameController.configureInputHandling(scene);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void showHighScores() {
        List<String[]> scores = List.of(
                new String[]{"PLAYER1", "1200"},
                new String[]{"PLAYER2", "950"},
                new String[]{"PLAYER3", "800"}
        );
        HighScoresView highScoresView = new HighScoresView(primaryStage, scores);
        highScoresView.show();
    }

    public void quitGame() {
        primaryStage.close();
    }
}
