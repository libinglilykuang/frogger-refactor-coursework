package uk.ac.nott.cs.comp2013.froggergame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.nott.cs.comp2013.froggergame.controller.GameController;
import uk.ac.nott.cs.comp2013.froggergame.controller.GameControllerFactory;
import uk.ac.nott.cs.comp2013.froggergame.view.GameView;

public class Main extends Application {
	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 800;
	private GameView gameView;
	private GameController gameController;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		initializeStage(primaryStage);
		startGame();
	}

	private void initializeStage(Stage primaryStage) {
		gameView = new GameView();
		gameController = GameControllerFactory.createGameController(gameView.getGameStage());
		Scene scene = new Scene(gameView, WINDOW_WIDTH, WINDOW_HEIGHT);
		gameController.configureInputHandling(scene);

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	private void startGame() {
		gameView.startBackground();
		gameView.playBackgroundMusic();
		gameController.startGameLoop();
	}
}
