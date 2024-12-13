package uk.ac.nott.cs.comp2013.froggergame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.nott.cs.comp2013.froggergame.controller.GameController;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;

public class Main extends Application {
	private MyStage background;
	private GameController gameController;


	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		initializeGame(primaryStage);
		startGame();
	}

	private void initializeGame(Stage primaryStage) {
		background = new MyStage();
		Scene scene = new Scene(background, 600, 800);

		gameController = new GameController(background);
		gameController.initializeGameComponents();

		background.start();
		background.playMusic();

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	private void startGame() {
		gameController.startGameLoop();
	}
}