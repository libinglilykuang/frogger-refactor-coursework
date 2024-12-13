package uk.ac.nott.cs.comp2013.froggergame.controller;

import uk.ac.nott.cs.comp2013.froggergame.view.managers.LevelView;
import uk.ac.nott.cs.comp2013.froggergame.view.managers.AlertManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.GameObjectFactory;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.LevelManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.model.base.ImageLoader;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;

public class GameController {
    private final MyStage background;
    private Animal animal;
    private LevelManager levelManager;
    private LevelView levelView;
    private final ScoreManager scoreManager;
    private final GameObjectFactory gameObjectFactory;
    private final GameInitializer gameInitializer;
    private final GameLoopManager gameLoopManager;

    public GameController(MyStage background) {
        this.background = background;

        this.scoreManager = new ScoreManager(30, 140, 5, 30);
        this.gameObjectFactory = new GameObjectFactory();
        this.levelView = new LevelView(background);
        this.gameInitializer = new GameInitializer(background, scoreManager);
        this.gameLoopManager = new GameLoopManager(background,scoreManager);
    }

    public void initializeGameComponents() {
        setupAnimal();
        setupLevelManager();
        gameInitializer.initializeComponents();
        gameLoopManager.setLevelManager(levelManager);
        gameLoopManager.setAnimal(animal);
        setupAnimal();
    }

    public void startGameLoop() {
        gameLoopManager.startGameLoop();
    }

    public void stopGame() {
        gameLoopManager.stopGameLoop();
    }

    private void setupAnimal() {
        animal = new Animal(ImageLoader.loadImage("froggerUp.png", 40, 40).getUrl());
        background.add(animal);
    }

    private void setupLevelManager() {
        int totalEnds = 5;
        levelManager = new LevelManager(animal, levelView, totalEnds);
        levelManager.setLevel(1);
    }


    public void handleGameWon() {
        stopGame();
        AlertManager.showGameWonAlert(animal.getPoints());
    }

    public void handleGameOver() {
        stopGame();
        AlertManager.showGameOverAlert();
    }

}
