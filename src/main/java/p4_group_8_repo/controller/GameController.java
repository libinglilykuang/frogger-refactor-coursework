package p4_group_8_repo.controller;

import p4_group_8_repo.view.utils.AlertManager;
import p4_group_8_repo.model.GameObjectFactory;
import p4_group_8_repo.model.LevelManager;
import p4_group_8_repo.model.ScoreManager;
import p4_group_8_repo.model.base.ImageLoader;
import p4_group_8_repo.model.entities.Animal;
import p4_group_8_repo.view.scene.MyStage;

public class GameController {
    private final MyStage background;
    private Animal animal;
    private LevelManager levelManager;
    private final ScoreManager scoreManager;
    private final GameObjectFactory gameObjectFactory;
    private final GameInitializer gameInitializer;
    private final GameLoopManager gameLoopManager;

    public GameController(MyStage background) {
        this.background = background;

        this.scoreManager = new ScoreManager(30, 140, 5, 30);
        this.gameObjectFactory = new GameObjectFactory();

        this.gameInitializer = new GameInitializer(background, scoreManager);
        this.gameLoopManager = new GameLoopManager( scoreManager);
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
        levelManager = new LevelManager(background, animal);
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
