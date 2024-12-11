package p4_group_8_repo.view;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import p4_group_8_repo.controller.GameController;
import p4_group_8_repo.model.LevelManager;
import p4_group_8_repo.model.base.ImageLoader;
import p4_group_8_repo.model.entities.*;
import p4_group_8_repo.view.scene.MyStage;
import p4_group_8_repo.view.ui.BackgroundImage;
import p4_group_8_repo.view.ui.Digit;
import java.util.Stack;

public class Main extends Application {
	private static final int DIGIT_SIZE = 30;
	private static final int SCORE_LABEL_X = 15;
	private static final int SCORE_LABEL_Y = 30;
	private static final int DIGIT_START_X = 140;
	private static final int DIGIT_START_Y = 5;
	private static final int DIGIT_SPACING = 30;

	private AnimationTimer timer;
	private MyStage background;
	private Animal animal;
	private LevelManager levelManager;
	private int level = 1;
	private GameController gameController;


	public static void main(String[] args) {
		try {
			Application.launch(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) {
		initializeGame(primaryStage);
		startGame();
	}

	private void initializeGame(Stage primaryStage) {
		background = new MyStage();
		Scene scene = new Scene(background, 600, 800);

		setupAnimal();
		setupLevelManager();
		gameController = new GameController(background, animal, levelManager);
		setupGameComponents();
		background.start();
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	private void setupGameComponents() {
		setupBackground();
		gameController.setupLogs();
		gameController.setupTurtles();
		gameController.setupEndPoints();
		gameController.setupObstacles();
		updateScoreDisplay(0);
		if (!background.getChildren().contains(animal)) {
			background.add(animal);
		} else {
			background.getChildren().remove(animal);
			background.add(animal);
		}
	}

	private void setupBackground() {
		BackgroundImage froggerBackground = new BackgroundImage("iKogsKW.png");
		background.add(froggerBackground);
	}

	private void setupLevelManager() {
		levelManager = new LevelManager(background, animal);
		levelManager.setLevel(level);
	}

	private void startGame() {
		background.playMusic();
		gameController.startGameLoop();
	}

	private void setupAnimal() {
		animal = new Animal(ImageLoader.loadImage("froggerUp.png", 40, 40).getUrl());
		background.add(animal);
	}

	private void updateScoreDisplay(int score) {
		background.getChildren().removeIf(node -> node instanceof Digit);
		createScoreLabel();
		displayDigits(score);
	}

	private void createScoreLabel() {
		Text scoreLabel = new Text("SCORE:");
		scoreLabel.setFont(Font.font("Arial", 30));
		scoreLabel.setFill(Color.WHITE);
		scoreLabel.setX(SCORE_LABEL_X);
		scoreLabel.setY(SCORE_LABEL_Y);
		background.getChildren().add(scoreLabel);
	}

	private void displayDigits(int score) {
		int shift = 0;
		Stack<Integer> digits = new Stack<>();

		if (score == 0) {
			digits.push(0);
		} else {
			while (score > 0) {
				digits.push(score % 10);
				score /= 10;
			}
		}

		while (!digits.isEmpty()) {
			int digit = digits.pop();
			background.add(new Digit(digit, DIGIT_SIZE, DIGIT_START_X + shift, DIGIT_START_Y));
			shift += DIGIT_SPACING;
		}
	}
}