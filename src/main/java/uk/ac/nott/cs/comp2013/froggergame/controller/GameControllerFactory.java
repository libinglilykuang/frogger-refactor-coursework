package uk.ac.nott.cs.comp2013.froggergame.controller;

import uk.ac.nott.cs.comp2013.froggergame.controller.initializer.GameInitializer;
import uk.ac.nott.cs.comp2013.froggergame.model.factory.GameObjectFactory;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.GameStateChecker;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;

public class GameControllerFactory {
    public static GameController createGameController(MyStage background) {
        ScoreManager scoreManager = new ScoreManager();
        Score scoreView = new Score();
        GameObjectFactory gameObjectFactory = new GameObjectFactory();
        GameStateChecker gameStateChecker = new GameStateChecker();
        GameInitializer gameInitializer = new GameInitializer(background, scoreManager, gameObjectFactory);

        GameControllerConfig config = new GameControllerConfig(
                background, scoreManager, gameObjectFactory, gameStateChecker, scoreView, gameInitializer
        );

        GameController controller = new GameController(config);
        controller.initializeGame();
        return controller;
    }
}
