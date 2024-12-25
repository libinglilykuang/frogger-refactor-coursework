package uk.ac.nott.cs.comp2013.froggergame.controller;

import javafx.scene.Scene;
import uk.ac.nott.cs.comp2013.froggergame.controller.initializer.GameInitializer;
import uk.ac.nott.cs.comp2013.froggergame.controller.input.InputHandler;
import uk.ac.nott.cs.comp2013.froggergame.controller.loop.GameLoopManager;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.model.data.LevelConfig;
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

        background.getTimeBar().setTimeOutListener(() -> {
            stopGame();
            freezeFrog();
            AlertManager.showGameOverAlert(background);
        });
    }

    private void initializeAnimal() {
        animal = gameObjectFactory.createAnimal();
        background.addActor(animal);
    }

    private void initializeLevelManager() {
        Level levelView = new Level(background);
        TimeBar timeBar = background.getTimeBar();

        levelManager = new LevelManager(animal, levelView, 1, timeBar,this);
        levelManager.setLevel(DEFAULT_INITIAL_LEVEL);
        levelView.updateLevelDisplay(DEFAULT_INITIAL_LEVEL);
    }

    private void initializeGameLoopManager() {
        gameLoopManager = new GameLoopManager(levelManager, scoreManager, animal, background, gameStateChecker, scoreView);
        gameLoopManager.setGameEventListener(this);
    }

    public void configureInputHandling(Scene scene) {
        if (scene != null && getAnimal() != null) {
            Animal animal = getAnimal();
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
        } else {
            if (scene != null) {
                scene.setOnKeyPressed(null);
                scene.setOnKeyReleased(null);
            }
        }
    }

    public void startGameLoop() {
        gameLoopManager.startGameLoopExecution();
        if (background.getTimeBar() != null) {
            background.getTimeBar().start();
        }
    }

    public void stopGame() {
        gameLoopManager.stopGameLoopExecution();
        if (background.getTimeBar() != null) {
            background.getTimeBar().stop();
        }
        background.stopMusic();
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

    private void freezeFrog() {
        if (animal != null) {
            animal.setActive(false);
        }
        configureInputHandling(null);
    }

    @Override
    public void onGameWon(int points) {
        //stopGame();
        //int point = scoreManager.getPoints();
        AlertManager.showGameWonAlert(background, scoreManager.getPoints());
        System.out.println("Displaying game won alert with points: " + scoreManager.getPoints());
    }

    @Override
    public void onGameOver() {
        stopGame();
        background.stopTimeBar();
        background.stopMusic();
        freezeFrog();
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
        if (animal != null && !animal.isActive()) {
            return;
        }

        if (background.getTimeBar() != null) {
            background.getTimeBar().getRectangle().toFront();
            background.getTimeBar().getText().toFront();
        }

        for (Actor actor : background.getObjects(Crocodile.class)) {
            actor.act(System.nanoTime());
        }

        for (End end : background.getObjects(End.class)) {
            if (!end.isActivated()&& animal.isActive() &&animal.getBoundsInParent().intersects(end.getBoundsInParent())) {
                end.setEnd();
                levelManager.onTargetReached();
            }
        }
        updateGameState();
    }

}