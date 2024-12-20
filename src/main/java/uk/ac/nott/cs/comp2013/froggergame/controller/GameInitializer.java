package uk.ac.nott.cs.comp2013.froggergame.controller;

import uk.ac.nott.cs.comp2013.froggergame.model.logic.GameObjectFactory;
import uk.ac.nott.cs.comp2013.froggergame.model.data.GameObjectData;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;
import uk.ac.nott.cs.comp2013.froggergame.view.components.BackgroundImage;
import uk.ac.nott.cs.comp2013.froggergame.view.components.End;

public class GameInitializer {
    private static final String BACKGROUND_IMAGE = "background.png";
    private static final int INITIAL_SCORE = 0;

    private final MyStage background;
    private final GameObjectFactory gameObjectFactory;
    private final ScoreManager scoreManager;

    public GameInitializer(MyStage background, ScoreManager scoreManager, GameObjectFactory gameObjectFactory) {
        this.background = background;
        this.scoreManager = scoreManager;
        this.gameObjectFactory = gameObjectFactory;
    }

    public void initializeComponents() {
        setupBackground();
        setupGameObjects();
        setupScoreDisplay();
    }

    private void setupBackground() {
        BackgroundImage bg = new BackgroundImage(BACKGROUND_IMAGE);
        background.getChildren().add(bg);
    }

    private void setupGameObjects() {
        addGameObjects(GameObjectData.LOG_DATA, "logs");
        addGameObjects(GameObjectData.TURTLE_DATA, "turtles");
        addGameObjects(GameObjectData.OBSTACLE_DATA, "obstacles");
        setupEndPoints();
    }

    private void addGameObjects(Object[][] data, String type) {
        switch (type) {
            case "logs" -> gameObjectFactory.setupLogs(background, data);
            case "turtles" -> gameObjectFactory.setupTurtles(background, data);
            case "obstacles" -> gameObjectFactory.setupObstacles(background, data);
            default -> throw new IllegalArgumentException("Unsupported game object type: " + type);
        }
    }

    private void setupEndPoints() {
        for (int[] position : GameObjectData.END_POINT_DATA) {
            background.getChildren().add(new End(position[0], position[1]));
        }
    }

    private void setupScoreDisplay() {
        scoreManager.createScoreLabel(background);
        scoreManager.updateScoreDisplay(background, INITIAL_SCORE);
    }

}