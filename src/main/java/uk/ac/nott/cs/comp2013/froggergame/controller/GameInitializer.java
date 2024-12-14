package uk.ac.nott.cs.comp2013.froggergame.controller;

import javafx.scene.image.Image;
import uk.ac.nott.cs.comp2013.froggergame.model.base.ImageLoader;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.GameObjectFactory;
import uk.ac.nott.cs.comp2013.froggergame.model.data.GameObjectData;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.LevelManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.view.managers.LevelView;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;
import uk.ac.nott.cs.comp2013.froggergame.view.components.BackgroundImage;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.End;

public class GameInitializer {
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

    public Animal createAnimal() {
        return new Animal(ImageLoader.loadImage("froggerUp.png", 40, 40).getUrl());
    }

    public LevelManager createLevelManager(Animal animal, LevelView levelView, int totalEnds) {
        return new LevelManager(animal, levelView, totalEnds);
    }

    private void setupBackground() {
        BackgroundImage bg = new BackgroundImage("iKogsKW.png");
        background.add(bg);
    }

    private void setupGameObjects() {
        Object[][] logData = GameObjectData.LOG_DATA;
        gameObjectFactory.setupLogs(background, logData);

        Object[][] turtleData = GameObjectData.TURTLE_DATA;
        gameObjectFactory.setupTurtles(background, turtleData);

        Object[][] obstacleData = GameObjectData.OBSTACLE_DATA;
        gameObjectFactory.setupObstacles(background, obstacleData);

        setupEndPoints();
    }

    private void setupEndPoints() {
        for (int[] position : GameObjectData.END_POINT_DATA) {
            background.add(new End(position[0], position[1]));
        }
    }

    private void setupScoreDisplay() {
        // Initialize the score display with a starting score of 0
        scoreManager.createScoreLabel(background);
        scoreManager.updateScoreDisplay(background, 0);
    }

    public void resetGameState() {
        background.getChildren().clear();
        initializeComponents();
    }
}