package uk.ac.nott.cs.comp2013.froggergame.controller;

import uk.ac.nott.cs.comp2013.froggergame.controller.initializer.GameInitializer;
import uk.ac.nott.cs.comp2013.froggergame.controller.loop.GameLoopManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.GameStateChecker;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Level;
import uk.ac.nott.cs.comp2013.froggergame.view.managers.AlertManager;
import uk.ac.nott.cs.comp2013.froggergame.model.factory.GameObjectFactory;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.level.LevelManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;
import java.util.function.Supplier;

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

    public GameController(GameControllerConfig config) {
        this.background = config.getBackground();
        this.scoreManager = config.getScoreManager();
        this.gameObjectFactory = config.getGameObjectFactory();
        this.gameInitializer = config.getGameInitializer();
        this.gameStateChecker = config.getGameStateChecker();
        this.scoreView = config.getScoreView();
    }

    public void initializeGame() {
        initializeAnimal();
        initializeLevelManager();
        initializeGameLoopManager();
        gameInitializer.initializeComponents(scoreView);
        initializeAnimal();
    }

    private void initializeAnimal() {
        animal = gameObjectFactory.createAnimal();
        background.addActor(animal);
    }

    private void initializeLevelManager() {
        Level levelView = new Level(background);
        levelManager = new LevelManager(animal, levelView, 5);
        levelManager.setLevel(DEFAULT_INITIAL_LEVEL);
        levelView.updateLevelDisplay(DEFAULT_INITIAL_LEVEL);
    }

    private void initializeGameLoopManager() {
        gameLoopManager = new GameLoopManager(levelManager, scoreManager, animal, background, gameStateChecker, scoreView);
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

    public void updateGameState() {
        updateScoreIfChanged();
        updateLevelIfChanged();
    }

    private void updateIfChanged(Supplier<Boolean> hasChanged, Runnable update, Runnable resetFlag) {
        if (hasChanged.get()) {
            update.run();
            resetFlag.run();
        }
    }

    private void updateScoreIfChanged() {
        updateIfChanged(
                () -> animal.getStateManager().hasScoreChanged(),
                () -> {
                    int currentPoints = animal.getStateManager().getPoints();
                    scoreView.updateScoreDisplay(background, currentPoints, scoreManager);
                },
                () -> animal.getStateManager().resetScoreChangedFlag()
        );
    }

    private void updateLevelIfChanged() {
        updateIfChanged(
                () -> levelManager.hasLevelChanged(),
                () -> {
                    int currentLevel = levelManager.getCurrentLevel();
                    levelManager.getLevelView().updateLevelDisplay(currentLevel);
                },
                () -> levelManager.resetLevelChangedFlag()
        );
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
        updateGameState();
    }

    @Override
    public void update(double elapsedTime) {
        background.act(elapsedTime);
        updateGameState();
    }

}