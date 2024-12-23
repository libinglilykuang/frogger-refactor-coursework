package uk.ac.nott.cs.comp2013.froggergame.controller.initializer;

import uk.ac.nott.cs.comp2013.froggergame.model.data.GameObjectType;
import uk.ac.nott.cs.comp2013.froggergame.model.factory.GameObjectFactory;
import uk.ac.nott.cs.comp2013.froggergame.model.data.GameObjectData;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Background;
import uk.ac.nott.cs.comp2013.froggergame.view.components.End;

public class GameInitializer {
    private static final String BACKGROUND_IMAGE = "background.png";
    private static final int INITIAL_SCORE = 0;

    private final Score scoreView;
    private final MyStage background;
    private final GameObjectFactory gameObjectFactory;
    private final ScoreManager scoreManager;

    public GameInitializer(MyStage background, ScoreManager scoreManager, GameObjectFactory gameObjectFactory, Score scoreView) {
        this.background = background;
        this.scoreManager = scoreManager;
        this.gameObjectFactory = gameObjectFactory;
        this.scoreView = scoreView;
    }

    public void initializeComponents() {
        setupBackground();
        setupGameObjects();
        setupScoreDisplay();
    }

    private void setupBackground() {
        Background bg = new Background(BACKGROUND_IMAGE);
        background.getChildren().add(bg);
    }

    private void setupGameObjects() {
        initializeGameObjects(GameObjectType.LOG, GameObjectData.LOG_DATA);
        initializeGameObjects(GameObjectType.TURTLE, GameObjectData.TURTLE_DATA);
        initializeGameObjects(GameObjectType.OBSTACLE, GameObjectData.OBSTACLE_DATA);
        setupEndPoints();
    }

    private void initializeGameObjects(GameObjectType type, Object[][] data) {
        switch (type) {
            case LOG:
                gameObjectFactory.setupLogs(background, data);
                break;
            case TURTLE:
                gameObjectFactory.setupTurtles(background, data);
                break;
            case OBSTACLE:
                gameObjectFactory.setupObstacles(background, data);
                break;
        }
    }

    private void setupEndPoints() {
        for (int[] position : GameObjectData.END_POINT_DATA) {
            background.getChildren().add(new End(position[0], position[1]));
        }
    }

    private void setupScoreDisplay() {
        scoreView.createScoreLabel(background);
        scoreView.updateScoreDisplay(background, INITIAL_SCORE, scoreManager);
    }
}
