package p4_group_8_repo.view;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import p4_group_8_repo.model.entities.*;
import p4_group_8_repo.view.scene.MyStage;
import p4_group_8_repo.view.ui.BackgroundImage;
import p4_group_8_repo.view.ui.digit;

import java.util.Objects;
import java.util.Stack;

public class Main extends Application {
	AnimationTimer timer;
	MyStage background;
	Animal animal;
	private int level = 1;
	private int totalEnds = 2;
	private int reachedEnds = 0;
	private boolean levelReset = false;

	public void setLevel(int level) {
		background.getChildren().removeIf(node -> node instanceof Text && ((Text) node).getText().startsWith("LEVEL:"));

		Text levelLabel = new Text("LEVEL: " + level);
		levelLabel.setFont(Font.font("Arial", 30));
		levelLabel.setFill(Color.WHITE);
		levelLabel.setX(250);
		levelLabel.setY(30);
		background.getChildren().add(levelLabel);
	}
	public void onTargetReached() {
		reachedEnds++;
		if (reachedEnds == totalEnds) {
			checkLevelProgress();
		}
	}

	public void checkLevelProgress() {
		if (reachedEnds == totalEnds) {
			increaseLevel();
			resetLevel();
		}
	}
	public void resetLevel() {
		levelReset = true;
		reachedEnds = 0;

		background.getChildren().removeIf(node -> node instanceof End);
		int startX = 4;
		int y = 96;
		int gap = 133;

		for (int i = 0; i < 5; i++) {
			background.add(new End(startX + i * gap, y));
		}
		animal.setX(300);
		animal.setY(733);

		Platform.runLater(() -> {
			background.layout();
			levelReset = false;
		});
	}

	public void increaseLevel() {
		level++;
		setLevel(level);

	}

	public static void main(String[] args) {
		try {
			System.out.println("Main method started...");
			Application.launch(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	    background = new MyStage();
	    Scene scene  = new Scene(background,600,800);

		BackgroundImage froggerback = new BackgroundImage(Objects.requireNonNull(getClass().getResource("/p4_group_8_repo/iKogsKW.png")).toExternalForm());
		background.add(froggerback);

		background.add(new Log(getClass().getResource("/p4_group_8_repo/log2.png").toExternalForm(), 200, 0, 176, 1));
		//background.add(new Log(getClass().getResource("/p4_group_8_repo/log2.png").toExternalForm(), 200, 220, 196, 1));
		//background.add(new Log(getClass().getResource("/p4_group_8_repo/log2.png").toExternalForm(), 200, 440, 206, 1));

		//background.add(new Log(getClass().getResource("/p4_group_8_repo/log3.png").toExternalForm(), 150, 0, 166, 0.75));

		background.add(new Log(getClass().getResource("/p4_group_8_repo/logs.png").toExternalForm(), 300, 0, 276, 1.5));
		background.add(new Log(getClass().getResource("/p4_group_8_repo/logs.png").toExternalForm(), 300, 400, 276, 1.5));

		//background.add(new Log(getClass().getResource("/p4_group_8_repo/logs.png").toExternalForm(), 260, 620, 266, 1.5));

		background.add(new Log(getClass().getResource("/p4_group_8_repo/log3.png").toExternalForm(), 150, 50, 329, 0.75));
		background.add(new Log(getClass().getResource("/p4_group_8_repo/log3.png").toExternalForm(), 150, 270, 329, 0.75));
		background.add(new Log(getClass().getResource("/p4_group_8_repo/log3.png").toExternalForm(), 150, 490, 329, 0.75));

		//background.add(new Log(getClass().getResource("/p4_group_8_repo/log3.png").toExternalForm(), 150, 570, 329, 0.75));

		background.add(new Turtle(500, 376, -1, 130, 130));
		background.add(new Turtle(300, 376, -1, 130, 130));
		background.add(new Turtle(700, 376, -1, 130, 130));
		background.add(new Turtle(600, 217, -1, 130, 130));
		background.add(new Turtle(400, 217, -1, 130, 130));
		background.add(new Turtle(200, 217, -1, 130, 130));

		/*background.add(new Log(getClass().getResource("/p4_group_8_repo/log2.png").toExternalForm(), 120, 200, 100, 1));
		background.add(new Log(getClass().getResource("/p4_group_8_repo/log2.png").toExternalForm(), 120, 0, 100, 1));
		background.add(new Log(getClass().getResource("/p4_group_8_repo/log2.png").toExternalForm(), 120, 100, 120, -1));
		background.add(new Log(getClass().getResource("/p4_group_8_repo/log2.png").toExternalForm(), 120, 200, 120, -1));
		background.add(new Log(getClass().getResource("/p4_group_8_repo/log2.png").toExternalForm(), 120, 100, 140, 1));
		background.add(new Log(getClass().getResource("/p4_group_8_repo/log2.png").toExternalForm(), 120, 200, 140, 1));
		background.add(new Log(getClass().getResource("/p4_group_8_repo/log2.png").toExternalForm(), 120, 100, 160, -1));
		background.add(new Log(getClass().getResource("/p4_group_8_repo/log2.png").toExternalForm(), 120, 300, 160, -1));
		background.add(new Log(getClass().getResource("/p4_group_8_repo/log2.png").toExternalForm(), 120, 100, 180, 1));
		background.add(new Log(getClass().getResource("/p4_group_8_repo/log2.png").toExternalForm(), 120, 200, 180, 1));
		background.add(new Log(getClass().getResource("/p4_group_8_repo/log2.png").toExternalForm(), 120, 100, 200, -1));
		background.add(new Log(getClass().getResource("/p4_group_8_repo/log2.png").toExternalForm(), 120, 200, 200, -1));
		background.add(new Log(getClass().getResource("/p4_group_8_repo/log2.png").toExternalForm(), 120, 100, 220, 1));
		background.add(new Log(getClass().getResource("/p4_group_8_repo/log2.png").toExternalForm(), 120, 200, 220, 1));
		background.add(new Log(getClass().getResource("/p4_group_8_repo/log2.png").toExternalForm(), 120, 400, 220, 1));
*/
		//End end2 = new End();
		//End end3 = new End();
		//End end4 = new End();
		//End end5 = new End();
		//background.add(end1);
		//background.add(end2);
		//background.add(end3);

		background.add(new End(4,96));
		background.add(new End(141-8,96));
		background.add(new End(141 + 141-13-6,96));
		background.add(new End(141 + 141-13+141-13-3,96));
		background.add(new End(141 + 141-13+141-13+141-13,96));

		animal = new Animal(getClass().getResource("/p4_group_8_repo/froggerUp.png").toExternalForm());
		background.add(animal);

		//background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/truck1Right.png").toExternalForm(), 0, 649, 1, 120, 120));
		//background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/truck1Right.png").toExternalForm(), 300, 649, 1, 120, 120));
		//background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/truck1Right.png").toExternalForm(), 600, 649, 1, 120, 120));
		//background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/truck1Right.png").toExternalForm(), 720, 649, 1, 120, 120));

		//background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/car1Left.png").toExternalForm(), 100, 597, -1, 50, 50));
		//background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/car1Left.png").toExternalForm(), 250, 597, -1, 50, 50));
		//background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/car1Left.png").toExternalForm(), 400, 597, -1, 50, 50));
		//background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/car1Left.png").toExternalForm(), 550, 597, -1, 50, 50));

		//background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/truck2Right.png").toExternalForm(), 0, 540, 1, 200, 200));
		//background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/truck2Right.png").toExternalForm(), 500, 540, 1, 200, 200));

		//background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/redcar.png").toExternalForm(), 100, 480, -1, 50, 50));
		//background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/redcar.png").toExternalForm(), 400, 490, -1, 50, 50));
		//background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/redcar.png").toExternalForm(), 600, 490, -1, 50, 50));

		background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/car1Left.png").toExternalForm(), 100, 700, -1, 50, 50));
		//background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/car1right.png").toExternalForm(), 400, 455, 1, 50, 50));
		//background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/car1right.png").toExternalForm(), 700, 455, 1, 50, 50));

		/*Obstacle obstacle = new Obstacle(Objects.requireNonNull(getClass().getResource("/p4_group_8_repo/truck1Right.png")).toExternalForm(), 25, 25, 3,120,120);
		Obstacle obstacle1 = new Obstacle(Objects.requireNonNull(getClass().getResource("/p4_group_8_repo/truck2Right.png")).toExternalForm(), 100, 100, 2,150,150);
		Obstacle obstacle2 = new Obstacle(Objects.requireNonNull(getClass().getResource("/p4_group_8_repo/truck1Right.png")).toExternalForm(), 0, 150, 1,100,100);
		background.add(obstacle);
		background.add(obstacle1);
		background.add(obstacle2);
*/
		setNumber(0);
		setLevel(level);

		background.start();
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		start();  
	}

	public void createTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	if (animal.changeScore()) {
            		setNumber(animal.getPoints());
            	}

				if (!levelReset &&!animal.getIntersectingObjects(End.class).isEmpty()) {
					for (End end : animal.getIntersectingObjects(End.class)) {
						if (!end.isActivated()) {
							end.setEnd();
							onTargetReached();
						}
					}
				}

            	if (animal.getStop()) {
            		System.out.print("STOPP:");
            		background.stopMusic();
            		stop();
            		background.stop();
            		Alert alert = new Alert(AlertType.INFORMATION);
            		alert.setTitle("You Have Won The Game!");
            		alert.setHeaderText("Your High Score: "+animal.getPoints()+"!");
            		alert.setContentText("Highest Possible Score: 800");
            		alert.show();
            	}
            }
        };
    }

	public void start() {
		background.playMusic();
    	createTimer();
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

	public void setNumber(int n) {
		background.getChildren().removeIf(node -> node instanceof digit);
		Text scoreLabel = new Text("SCORE:");
		scoreLabel.setFont(Font.font("Arial", 30));
		scoreLabel.setFill(Color.WHITE);
		int labelX = 15;
		int labelY = 30;
		scoreLabel.setX(labelX);
		scoreLabel.setY(labelY);
		background.getChildren().add(scoreLabel);

		int shift = 0;
		Stack<Integer> digits = new Stack<>();
		if (n == 0) {
			digits.push(0);
		} else {
			while (n > 0) {
				digits.push(n % 10);
				n = n / 10;
			}
		}

		while (!digits.isEmpty()) {
			int d = digits.pop();
			background.add(new digit(d, 30, 140 + shift, 5));
			shift += 30;
		}
	}

}
