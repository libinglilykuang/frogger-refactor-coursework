package uk.ac.nott.cs.comp2013.froggergame.controller;

import uk.ac.nott.cs.comp2013.froggergame.view.managers.LevelView;
import uk.ac.nott.cs.comp2013.froggergame.view.managers.AlertManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.GameObjectFactory;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.LevelManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;

public class GameController implements GameEventListener{
    private final MyStage background;
    private final GameInitializer gameInitializer;
    private Animal animal;
    private LevelManager levelManager;
    private final ScoreManager scoreManager;
    private final GameObjectFactory gameObjectFactory;
    private GameLoopManager gameLoopManager;

    public GameController(MyStage background, ScoreManager scoreManager, GameObjectFactory gameObjectFactory) {
        this.background = background;
        this.scoreManager = scoreManager;
        this.gameObjectFactory = gameObjectFactory;
        this.gameInitializer = new GameInitializer(background, scoreManager, gameObjectFactory);
    }

    public void initializeGame() {
        initializeAnimal();
        initializeLevelManager();
        initializeGameLoopManager();
        gameInitializer.initializeComponents();
        initializeAnimal();
    }

    private void initializeAnimal() {
        animal = gameObjectFactory.createAnimal();
        background.add(animal);
    }

    private void initializeLevelManager() {
        LevelView levelView = new LevelView(background);
        levelManager = gameObjectFactory.createLevelManager(animal, levelView, 5);
        levelManager.setLevel(1);
    }

    private void initializeGameLoopManager() {
        gameLoopManager = new GameLoopManager(levelManager, scoreManager, animal, background,this);
        gameLoopManager.setGameEventListener(this);
    }

    public void startGameLoop() {
        gameLoopManager.startGameLoopExecution();
    }

    public void stopGame() {
        gameLoopManager.stopGameLoopExecution();
    }

    public Animal getAnimal() {
        return this.animal;
    }

    @Override
    public void onGameWon(int points) {
        stopGame();
        AlertManager.showGameWonAlert(points);
    }

    @Override
    public void onGameOver() {
        stopGame();
        AlertManager.showGameOverAlert();
    }

    @Override
    public void update(long now) {
        background.act(now);
    }

    @Override
    public void update(double elapsedTime) {
        background.act(elapsedTime);
        scoreManager.updateScoreIfChanged(background, animal);
        animal.getStateManager().resetScoreChangedFlag();
    }

}