package uk.ac.nott.cs.comp2013.froggergame.controller;

import javafx.scene.Scene;
import java.util.function.Supplier;

import uk.ac.nott.cs.comp2013.froggergame.controller.initializer.GameInitializer;
import uk.ac.nott.cs.comp2013.froggergame.controller.input.InputHandler;
import uk.ac.nott.cs.comp2013.froggergame.controller.loop.GameLoopManager;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Crocodile;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.GameStateChecker;
import uk.ac.nott.cs.comp2013.froggergame.view.components.End;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Level;
import uk.ac.nott.cs.comp2013.froggergame.view.components.TimeBar;
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

    public GameController(GameControllerConfig config) {
        this.background = config.background();
        this.scoreManager = config.scoreManager();
        this.gameObjectFactory = config.gameObjectFactory();
        this.gameInitializer = config.gameInitializer();
        this.gameStateChecker = config.gameStateChecker();
        this.scoreView = config.scoreView();
    }

    public void initializeGame() {
        setupAnimal();
        setupLevelManager();
        setupGameLoopManager();
        gameInitializer.initializeComponents(scoreView);
        setupTimeoutListener();
        setupAnimal();
    }

    private void setupAnimal() {
        animal = gameObjectFactory.createAnimal();
        background.addActor(animal);
    }

    private void setupLevelManager() {
        Level levelView = new Level(background);
        TimeBar timeBar = background.getTimeBar();

        levelManager = new LevelManager(animal, levelView, 5, timeBar, this);
        levelManager.setLevel(DEFAULT_INITIAL_LEVEL);
        levelView.updateLevelDisplay(DEFAULT_INITIAL_LEVEL);
    }

    private void setupGameLoopManager() {
        gameLoopManager = new GameLoopManager(animal, gameStateChecker);
        gameLoopManager.setGameEventListener(this);
    }

    private void setupTimeoutListener() {
        background.getTimeBar().setTimeOutListener(() -> {
            stopGame();
            freezeAnimal();
            AlertManager.showGameOverAlert(background);
        });
    }

    public void configureInputHandling(Scene scene) {
        if (scene != null && animal != null) {
            InputHandler inputHandler = new InputHandler(animal);

            scene.setOnKeyPressed(event -> {
                if (animal.isActive()) {
                    inputHandler.handleKeyPressed(event);
                }
            });

            scene.setOnKeyReleased(event -> {
                if (animal.isActive()) {
                    inputHandler.handleKeyReleased(event);
                }
            });
        } else if (scene != null) {
            scene.setOnKeyPressed(null);
            scene.setOnKeyReleased(null);
        }
    }

    public void startGameLoop() {
        gameLoopManager.startGameLoopExecution();
        TimeBar timeBar = background.getTimeBar();
        if (timeBar != null) {
            timeBar.start();
        }
    }

    public void stopGame() {
        gameLoopManager.stopGameLoopExecution();
        TimeBar timeBar = background.getTimeBar();
        if (timeBar != null) {
            timeBar.stop();
        }
        background.stopMusic();
    }

    public void updateGameState() {
        updateIfChanged(
                () -> animal.getStateManager().hasScoreChanged(),
                () -> {
                    int currentPoints = animal.getStateManager().getPoints();
                    scoreView.updateScoreDisplay(background, currentPoints, scoreManager);
                },
                () -> animal.getStateManager().resetScoreChangedFlag()
        );

        updateIfChanged(
                () -> levelManager.hasLevelChanged(),
                () -> {
                    int currentLevel = levelManager.getCurrentLevel();
                    levelManager.getLevelView().updateLevelDisplay(currentLevel);
                },
                () -> levelManager.resetLevelChangedFlag()
        );
    }

    private void updateIfChanged(Supplier<Boolean> hasChanged, Runnable update, Runnable resetFlag) {
        if (hasChanged.get()) {
            update.run();
            resetFlag.run();
        }
    }

    private void freezeAnimal() {
        if (animal != null) {
            animal.setActive(false);
        }
        configureInputHandling(null);
    }

    @Override
    public void onGameWon(int points) {
        stopGame();
        AlertManager.showGameWonAlert(background, scoreManager.getPoints());
    }

    @Override
    public void onGameOver() {
        stopGame();
        background.stopTimeBar();
        freezeAnimal();
        AlertManager.showGameOverAlert(background);
    }

    @Override
    public void update(long now) {
        background.act(now);
        updateGameState();
    }

    @Override
    public void update(double elapsedTime) {
        background.act(elapsedTime);
        if (animal == null || !animal.isActive()) {
            return;
        }

        TimeBar timeBar = background.getTimeBar();
        if (timeBar != null) {
            timeBar.getRectangle().toFront();
            timeBar.getText().toFront();
        }

        background.getObjects(Crocodile.class).forEach(actor -> actor.act(System.nanoTime()));

        for (End end : background.getObjects(End.class)) {
            if (!end.isActivated() && animal.isActive() && animal.getBoundsInParent().intersects(end.getBoundsInParent())) {
                end.setEnd();
                levelManager.onTargetReached();
            }
        }

        updateGameState();
    }

    public Animal getAnimal() {
        return animal;
    }
}
