package uk.ac.nott.cs.comp2013.froggergame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.nott.cs.comp2013.froggergame.controller.*;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.GameObjectFactory;
import uk.ac.nott.cs.comp2013.froggergame.controller.input.InputHandler;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;

public class Main extends Application {
	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 800;

	private MyStage background;
	private GameController gameController;
	private InputHandler inputHandler;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		setupStage(primaryStage);
		startGame();
	}

	private void setupStage(Stage primaryStage) {
		background = new MyStage();
		Scene scene = new Scene(background, WINDOW_WIDTH, WINDOW_HEIGHT);

		gameController = createGameController(background);
		gameController.initializeGame();

		Animal animal = gameController.getAnimal();

		inputHandler = new InputHandler();
		scene.setOnKeyPressed(event -> inputHandler.processInput(animal, event));
		scene.setOnKeyReleased(event -> inputHandler.processInput(animal, event));

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	private GameController createGameController(MyStage background) {
		ScoreManager scoreManager = new ScoreManager();
		GameObjectFactory gameObjectFactory = new GameObjectFactory();
		return new GameController(background, scoreManager, gameObjectFactory);
	}


	private void startGame() {
		background.start();
		background.playMusic();

		gameController.startGameLoop();
	}
}