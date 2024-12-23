package uk.ac.nott.cs.comp2013.froggergame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.nott.cs.comp2013.froggergame.controller.*;
import uk.ac.nott.cs.comp2013.froggergame.controller.initializer.GameInitializer;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.factory.GameObjectFactory;
import uk.ac.nott.cs.comp2013.froggergame.controller.input.InputHandler;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.GameStateChecker;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;
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
		initializeStage(primaryStage);
		startGame();
	}

	private void initializeStage(Stage primaryStage) {
		Scene scene = initializeScene();
		gameController = initializeGameController();
		configureInputHandling(scene);

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	private Scene initializeScene() {
		background = new MyStage();
		return new Scene(background, WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	private GameController initializeGameController() {
		return GameControllerFactory.createGameController(background);
	}

	private void configureInputHandling(Scene scene) {
		Animal animal = gameController.getAnimal();
		inputHandler = new InputHandler(animal);
		scene.setOnKeyPressed(event -> inputHandler.handleKeyPressed(event));
		scene.setOnKeyReleased(event -> inputHandler.handleKeyReleased(event));
	}

	private void startGame() {
		background.start();
		background.playMusic();
		gameController.startGameLoop();
	}
}