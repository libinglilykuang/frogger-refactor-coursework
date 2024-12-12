package p4_group_8_repo.controller;

import p4_group_8_repo.model.GameObjectFactory;
import p4_group_8_repo.model.ScoreManager;
import p4_group_8_repo.view.scene.MyStage;
import p4_group_8_repo.view.ui.BackgroundImage;

public class GameInitializer {
    private final MyStage background;
    private final GameObjectFactory gameObjectFactory;
    private final ScoreManager scoreManager;

    public GameInitializer(MyStage background, ScoreManager scoreManager) {
        this.background = background;
        this.scoreManager = scoreManager;
        this.gameObjectFactory = new GameObjectFactory();
    }

    public void initializeComponents() {
        setupBackground();
        setupGameObjects();
        setupScoreDisplay();
    }

    private void setupBackground() {
        BackgroundImage bg = new BackgroundImage("iKogsKW.png");
        background.add(bg);
    }

    private void setupGameObjects() {
        Object[][] logData = p4_group_8_repo.model.GameObjectData.LOG_DATA;
        gameObjectFactory.setupLogs(background, logData);

        Object[][] turtleData = p4_group_8_repo.model.GameObjectData.TURTLE_DATA;
        gameObjectFactory.setupTurtles(background, turtleData);

        Object[][] obstacleData = p4_group_8_repo.model.GameObjectData.OBSTACLE_DATA;
        gameObjectFactory.setupObstacles(background, obstacleData);

        setupEndPoints();
    }

    private void setupEndPoints() {
        for (int[] position : p4_group_8_repo.model.GameObjectData.END_POINT_DATA) {
            background.add(new p4_group_8_repo.model.entities.End(position[0], position[1]));
        }
    }

    private void setupScoreDisplay() {
        // Initialize the score display with a starting score of 0
        scoreManager.createScoreLabel(background);
        scoreManager.updateScoreDisplay(background, 0);
    }
}
