package p4_group_8_repo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class Main extends Application {
	AnimationTimer timer;
	MyStage background;
	Animal animal;
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

		background.add(new Log(getClass().getResource("/p4_group_8_repo/log3.png").toExternalForm(), 150, 0, 166, 0.75));
		background.add(new Log(getClass().getResource("/p4_group_8_repo/log3.png").toExternalForm(), 150, 220, 166, 0.75));
		background.add(new Log(getClass().getResource("/p4_group_8_repo/log3.png").toExternalForm(), 150, 440, 166, 0.75));

		//background.add(new Log(getClass().getResource("/p4_group_8_repo/log3.png").toExternalForm(), 150, 0, 166, 0.75));

		background.add(new Log(getClass().getResource("/p4_group_8_repo/logs.png").toExternalForm(), 300, 0, 276, -2));
		background.add(new Log(getClass().getResource("/p4_group_8_repo/logs.png").toExternalForm(), 300, 400, 276, -2));

		//background.add(new Log(getClass().getResource("/p4_group_8_repo/logs.png").toExternalForm(), 300, 800, 276, -2));

		background.add(new Log(getClass().getResource("/p4_group_8_repo/log3.png").toExternalForm(), 150, 50, 329, 0.75));
		background.add(new Log(getClass().getResource("/p4_group_8_repo/log3.png").toExternalForm(), 150, 270, 329, 0.75));
		background.add(new Log(getClass().getResource("/p4_group_8_repo/log3.png").toExternalForm(), 150, 490, 329, 0.75));

		//background.add(new Log(getClass().getResource("/p4_group_8_repo/log3.png").toExternalForm(), 150, 570, 329, 0.75));

		background.add(new Turtle(500, 376, -1, 130, 130));
		background.add(new Turtle(300, 376, -1, 130, 130));
		background.add(new WetTurtle(700, 376, -1, 130, 130));
		background.add(new WetTurtle(600, 217, -1, 130, 130));
		background.add(new WetTurtle(400, 217, -1, 130, 130));
		background.add(new WetTurtle(200, 217, -1, 130, 130));
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
		//End end1 = new End(50, 50);
		//End end2 = new End(100, 100);
		//End end3 = new End(150, 150);
		//End end4 = new End(200, 200);
		//background.add(end1);
		//background.add(end2);
		//background.add(end3);

		background.add(new End(13,96));
		background.add(new End(141,96));
		background.add(new End(141 + 141-13,96));
		background.add(new End(141 + 141-13+141-13,96));
		background.add(new End(141 + 141-13+141-13+141-13,96));

		animal = new Animal(getClass().getResource("/p4_group_8_repo/froggerUp.png").toExternalForm());
		background.add(animal);

		background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/truck1Right.png").toExternalForm(), 0, 649, -1, 120, 120));
		background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/truck1Right.png").toExternalForm(), 300, 649, -1, 120, 120));
		background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/truck1Right.png").toExternalForm(), 600, 649, -1, 120, 120));
		background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/truck1Right.png").toExternalForm(), 720, 649, -1, 120, 120));

		background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/car1Left.png").toExternalForm(), 100, 597, 1, 50, 50));
		background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/car1Left.png").toExternalForm(), 250, 597, 1, 50, 50));
		background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/car1Left.png").toExternalForm(), 400, 597, 1, 50, 50));
		background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/car1Left.png").toExternalForm(), 550, 597, 1, 50, 50));

		background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/truck2Right.png").toExternalForm(), 0, 540, -1, 200, 200));
		background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/truck2Right.png").toExternalForm(), 500, 540, -1, 200, 200));
		//background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/car1Left.png").toExternalForm(), 500, 490, -5, 50, 50));

		background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/redcar.png").toExternalForm(), 100, 490, 1, 50, 50));
		background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/redcar.png").toExternalForm(), 400, 490, 1, 50, 50));
		background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/redcar.png").toExternalForm(), 600, 490, 1, 50, 50));

		background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/car1right.png").toExternalForm(), 100, 455, -1, 50, 50));
		background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/car1right.png").toExternalForm(), 400, 455, -1, 50, 50));
		background.add(new Obstacle(getClass().getResource("/p4_group_8_repo/car1right.png").toExternalForm(), 550, 455, -1, 50, 50));

		/*Obstacle obstacle = new Obstacle(Objects.requireNonNull(getClass().getResource("/p4_group_8_repo/truck1Right.png")).toExternalForm(), 25, 25, 3,120,120);
		Obstacle obstacle1 = new Obstacle(Objects.requireNonNull(getClass().getResource("/p4_group_8_repo/truck2Right.png")).toExternalForm(), 100, 100, 2,150,150);
		Obstacle obstacle2 = new Obstacle(Objects.requireNonNull(getClass().getResource("/p4_group_8_repo/truck1Right.png")).toExternalForm(), 0, 150, 1,100,100);
		background.add(obstacle);
		background.add(obstacle1);
		background.add(obstacle2);
*/

		background.add(new digit(0, 30, 360, 25));
		background.start();
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Frogger Game");////
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
    	int shift = 0;
    	while (n < 0) {
    		  int d = n / 10;
    		  int k = n - d * 10;
    		  n = d;
    		  background.add(new digit(k, 30, 360 - shift, 25));
    		  shift+=30;
    		}
		/*while (n > 0) {
			int d = n % 10;
			n = n / 10;
			background.add(new digit(d, 30, 360 - shift, 25));
			shift += 30;
		}*/
    }
}
