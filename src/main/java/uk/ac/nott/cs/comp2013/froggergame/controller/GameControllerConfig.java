package uk.ac.nott.cs.comp2013.froggergame.controller;

import uk.ac.nott.cs.comp2013.froggergame.controller.initializer.GameInitializer;
import uk.ac.nott.cs.comp2013.froggergame.model.factory.GameObjectFactory;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.GameStateChecker;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;

public class GameControllerConfig {
    private final MyStage background;
    private final ScoreManager scoreManager;
    private final GameObjectFactory gameObjectFactory;
    private final GameStateChecker gameStateChecker;
    private final Score scoreView;
    private final GameInitializer gameInitializer;

    public GameControllerConfig(MyStage background, ScoreManager scoreManager,
                                GameObjectFactory gameObjectFactory,
                                GameStateChecker gameStateChecker,
                                Score scoreView, GameInitializer gameInitializer) {
        this.background = background;
        this.scoreManager = scoreManager;
        this.gameObjectFactory = gameObjectFactory;
        this.gameStateChecker = gameStateChecker;
        this.scoreView = scoreView;
        this.gameInitializer = gameInitializer;
    }

    public MyStage getBackground() { return background; }
    public ScoreManager getScoreManager() { return scoreManager; }
    public GameObjectFactory getGameObjectFactory() { return gameObjectFactory; }
    public GameStateChecker getGameStateChecker() { return gameStateChecker; }
    public Score getScoreView() { return scoreView; }
    public GameInitializer getGameInitializer() { return gameInitializer; }
}

