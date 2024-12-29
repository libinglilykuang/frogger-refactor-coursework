package uk.ac.nott.cs.comp2013.froggergame.controller.initializer;

import uk.ac.nott.cs.comp2013.froggergame.model.data.GameObjectType;
import uk.ac.nott.cs.comp2013.froggergame.model.factory.GameObjectFactory;
import uk.ac.nott.cs.comp2013.froggergame.model.data.GameObjectData;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Background;
import uk.ac.nott.cs.comp2013.froggergame.view.components.End;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class GameInitializer {
    private static final String BACKGROUND_IMAGE = "background.png";
    private static final int INITIAL_SCORE = 0;

    private final MyStage background;
    private final ScoreManager scoreManager;
    private final Map<GameObjectType, BiConsumer<MyStage, Object[][]>> setupMethods;

    public GameInitializer(MyStage background, ScoreManager scoreManager, GameObjectFactory gameObjectFactory) {
        this.background = background;
        this.scoreManager = scoreManager;

        // Map object types to their respective setup methods
        this.setupMethods = new HashMap<>();
        setupMethods.put(GameObjectType.LOG, gameObjectFactory::setupLogs);
        setupMethods.put(GameObjectType.TURTLE, gameObjectFactory::setupTurtles);
        setupMethods.put(GameObjectType.OBSTACLE, gameObjectFactory::setupObstacles);
    }

    public void initializeComponents(Score scoreView) {
        setupBackground();
        setupGameObjects();
        setupScoreDisplay(scoreView);
    }

    public void setupBackground() {
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
        // Dynamically dispatch setup logic
        setupMethods.getOrDefault(type, (bg, dt) -> {}).accept(background, data);
    }

    public void setupEndPoints() {
        for (int[] position : GameObjectData.END_POINT_DATA) {
            background.getChildren().add(new End(position[0], position[1]));
        }
    }

    private void setupScoreDisplay(Score scoreView) {
        scoreView.createScoreLabel(background);
        scoreView.updateScoreDisplay(background, INITIAL_SCORE, scoreManager);
    }
}
