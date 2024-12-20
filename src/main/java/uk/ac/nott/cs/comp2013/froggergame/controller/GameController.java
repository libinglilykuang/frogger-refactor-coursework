package uk.ac.nott.cs.comp2013.froggergame.controller;

import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.GameStateChecker;
import uk.ac.nott.cs.comp2013.froggergame.view.managers.LevelView;
import uk.ac.nott.cs.comp2013.froggergame.view.managers.AlertManager;
import uk.ac.nott.cs.comp2013.froggergame.model.factory.GameObjectFactory;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.level.LevelManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;

public class GameController implements GameEventListener {
    private final MyStage background;
    private final GameInitializer gameInitializer;
    private Animal animal;
    private LevelManager levelManager;
    private final ScoreManager scoreManager;
    private final GameObjectFactory gameObjectFactory;
    private GameLoopManager gameLoopManager;
    private final GameStateChecker gameStateChecker;
    private final Score scoreView;

    private static final int DEFAULT_INITIAL_LEVEL = 1;
    private static final int MAX_LIVES = 5;

    public GameController(MyStage background, ScoreManager scoreManager, GameObjectFactory gameObjectFactory, GameStateChecker gameStateChecker, Score scoreView) {
        this.background = background;
        this.scoreManager = scoreManager;
        this.gameObjectFactory = gameObjectFactory;
        this.gameInitializer = new GameInitializer(background, scoreManager, gameObjectFactory, scoreView); // 传入 ScoreView
        this.gameStateChecker = gameStateChecker;
        this.scoreView = scoreView;
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
        background.addActor(animal);
    }

    private void initializeLevelManager() {
        LevelView levelView = new LevelView(background);
        levelManager = gameObjectFactory.createLevelManager(animal, levelView, MAX_LIVES);
        levelManager.setLevel(DEFAULT_INITIAL_LEVEL);
    }

    private void initializeGameLoopManager() {
        gameLoopManager = new GameLoopManager(levelManager, scoreManager, animal, background, gameStateChecker, scoreView); // 添加 scoreView 参数
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
        updateScoreIfChanged();
    }

    @Override
    public void update(double elapsedTime) {
        background.act(elapsedTime);
        updateScoreIfChanged();
    }

    private void updateScoreIfChanged() {
        if (animal.getStateManager().hasScoreChanged()) {
            int currentPoints = animal.getStateManager().getPoints();
            scoreView.updateScoreDisplay(background, currentPoints, scoreManager);
            animal.getStateManager().resetScoreChangedFlag();
        }
    }

}
