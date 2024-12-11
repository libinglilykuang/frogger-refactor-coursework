package p4_group_8_repo.controller;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Alert;
import p4_group_8_repo.model.LevelManager;
import p4_group_8_repo.model.ScoreManager;
import p4_group_8_repo.model.GameObjectFactory;
import p4_group_8_repo.model.base.ImageLoader;
import p4_group_8_repo.model.entities.*;
import p4_group_8_repo.view.scene.MyStage;

public class GameController {
    private final MyStage background;
    private Animal animal;
    private final LevelManager levelManager;
    private final ScoreManager scoreManager;
    private final GameObjectFactory gameObjectFactory;
    private AnimationTimer timer;

    public GameController(MyStage background, Animal animal, LevelManager levelManager) {
        this.background = background;
        this.animal = animal;
        this.levelManager = levelManager;
        this.scoreManager = new ScoreManager(30, 140, 5, 30);
        this.gameObjectFactory = new GameObjectFactory();
    }

    public void setupLogs() {
        addLog("log2.png", 200, 200, 200, 0, 176, 1);
        addLog("log2.png", 200, 200, 200, 220, 176, 1);
        addLog("log2.png", 200, 200, 200, 440, 176, 1);

        addLog("logs.png", 300, 300, 300, 0, 276, 1.5);
        addLog("logs.png", 300, 300, 300, 400, 276, 1.5);

        addLog("log3.png", 150, 150, 150, 50, 329, 0.75);
        addLog("log3.png", 150, 150, 150, 270, 329, 0.75);
        addLog("log3.png", 150, 150, 150, 490, 329, 0.75);
    }

    public void addLog(String imageName, int width, int height, int xpos, int ypos, int yLevel, double speed) {
        Log log = gameObjectFactory.createLog(imageName, width, height, xpos, ypos, yLevel, speed);
        background.add(log);
    }

    public void setupTurtles() {
        background.add(new Turtle(500, 376, -1, 130, 130));
        background.add(new Turtle(300, 376, -1, 130, 130));
        background.add(new Turtle(700, 376, -1, 130, 130));
        background.add(new WetTurtle(600, 217, -1, 130, 130));
        background.add(new WetTurtle(400, 217, -1, 130, 130));
        background.add(new WetTurtle(200, 217, -1, 130, 130));
    }

    public void setupObstacles() {
        addObstacle("truck1Right.png", 120, 120, 0, 649, 1);
        addObstacle("truck1Right.png", 120, 120, 300, 649, 1);
        addObstacle("truck1Right.png", 120, 120, 600, 649, 1);
        addObstacle("truck1Right.png", 120, 120, 720, 649, 1);

        addObstacle("car1Left.png", 50, 50, 100, 597, -1);
        addObstacle("car1Left.png", 50, 50, 250, 597, -1);
        addObstacle("car1Left.png", 50, 50, 400, 597, -1);
        addObstacle("car1Left.png", 50, 50, 550, 597, -1);

        addObstacle("truck2Right.png", 200, 200, 0, 540, 1);
        addObstacle("truck2Right.png", 200, 200, 500, 540, 1);

        addObstacle("redcar.png", 50, 50, 100, 480, -1);
        addObstacle("redcar.png", 50, 50, 400, 490, -1);
        addObstacle("redcar.png", 50, 50, 600, 490, -1);

        addObstacle("car1Left.png", 50, 50, 100, 700, -1);
        addObstacle("car1Left.png", 50, 50, 400, 700, -1);
        addObstacle("car1Left.png", 50, 50, 600, 700, -1);
    }

    public void addObstacle(String imageName, int width, int height, int xpos, int ypos, int speed) {
        background.add(new Obstacle(ImageLoader.loadImage(imageName, width, height).getUrl(), xpos, ypos, speed, width, height));
    }

    public void setupEndPoints() {
        int startX = 4;
        int y = 96;
        int gap = 133;
        for (int i = 0; i < 5; i++) {
            background.add(new End(startX + i * gap, y));
        }
    }

    public void updateScore() {
        if (animal.hasScoreChanged()) {
            int currentScore = animal.getPoints();
            scoreManager.updateScoreDisplay(background, currentScore);
        }
    }

    public void processTargetReached() {
        if (!levelManager.isLevelReset() && !animal.getIntersectingObjects(End.class).isEmpty()) {
            for (End end : animal.getIntersectingObjects(End.class)) {
                if (!end.isActivated()) {
                    end.setEnd();
                    levelManager.onTargetReached();
                }
            }
        }
    }

    public void checkGameWon() {
        if (animal.isGameWon()) {
            handleGameWon();
        }
    }

    private void handleGameWon() {
        background.stopMusic();
        stopGame();
        showGameWonAlert();
    }
    public void stopGame() {
        timer.stop();
    }

    private void showGameWonAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You Have Won The Game!");
        alert.setHeaderText("Your High Score: " + animal.getPoints() + "!");
        alert.setContentText("Highest Possible Score: 800");
        alert.show();
    }

    public void startGameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateScore();
                processTargetReached();
                checkGameWon();
            }
        };
        timer.start();
    }


}
