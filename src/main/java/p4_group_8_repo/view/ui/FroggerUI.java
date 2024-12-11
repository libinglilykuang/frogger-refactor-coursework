package p4_group_8_repo.view.ui;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import p4_group_8_repo.model.base.Sprite;
import p4_group_8_repo.model.entities.Car;
import p4_group_8_repo.model.entities.Frog;

/**
 * Provides the UI for the Frogger game, including levels and sprite statistics.
 */
public class FroggerUI extends UI {
    private static final String GAME_OVER_STYLE = "-fx-background-color: rgba(255, 102, 129, 0.73);";
    private static final String TRANSPARENT_STYLE = "-fx-background-color: transparent;";
    private static final String LEVEL_TEXT_PREFIX = "LEVEL: ";
    private static final String FONT_FAMILY = "Impact";
    private static final int GAME_OVER_FONT_SIZE = 60;

    private final StackPane base;
    private final BorderPane uiPane;
    private final StackPane gameOverPane;

    private Label levelLabel;
    private int level;

    private BorderPane statsContainer;
    private HBox statsPane;
    private Label frogStatsLabel;
    private Label carStatsLabel;

    public FroggerUI(Canvas canvas) {
        super(canvas);

        base = new StackPane();
        uiPane = new BorderPane(canvas);
        uiPane.setId("uiPane");

        level = 1;
        levelLabel = createLevelLabel();
        gameOverPane = createGameOverPane();
    }

    private Label createLevelLabel() {
        Label label = new Label(LEVEL_TEXT_PREFIX + level);
        label.setId("levelLabel");
        label.setAlignment(Pos.CENTER);
        return label;
    }

    private StackPane createGameOverPane() {
        StackPane pane = new StackPane();
        pane.setStyle(TRANSPARENT_STYLE);
        return pane;
    }

    @Override
    public void setupComponents() {
        initializeStatistics();
    }

    @Override
    public void render(Group root) {
        BorderPane.setAlignment(levelLabel, Pos.CENTER);
        uiPane.setTop(levelLabel);

        StackPane.setAlignment(getCanvas(), Pos.CENTER);
        StackPane.setAlignment(gameOverPane, Pos.CENTER);
        base.getChildren().addAll(uiPane, getCanvas(), gameOverPane);

        root.getChildren().add(base);
    }

    @Override
    public void refreshUI(Sprite... sprites) {
        for (Sprite sprite : sprites) {
            if (sprite instanceof Frog) {
                updateFrogStats((Frog) sprite);
            } else if (sprite instanceof Car) {
                updateCarStats((Car) sprite);
            }
        }
    }

    private void updateFrogStats(Frog frog) {
        frogStatsLabel.setText(formatSpriteStats("Frog", frog));
    }

    private void updateCarStats(Car car) {
        carStatsLabel.setText(formatSpriteStats("Car", car));
    }

    private String formatSpriteStats(String type, Sprite sprite) {
        return String.format(
                "%s: (%.2f, %.2f)\nCenter: (%.2f, %.2f)\nvX: %.2f - vY: %.2f\nWidth: %.2f\nHeight: %.2f\nHalfWidth: %.2f\nHalfHeight: %.2f",
                type,
                sprite.getX(), sprite.getY(),
                sprite.getCenterX(), sprite.getCenterY(),
                sprite.getvX(), sprite.getvY(),
                sprite.getWidth(), sprite.getHeight(),
                sprite.getHalfWidth(), sprite.getHalfHeight()
        );
    }

    @Override
    public void initializeStatistics() {
        statsContainer = new BorderPane();
        statsPane = new HBox();

        frogStatsLabel = createStatsLabel();
        carStatsLabel = createStatsLabel();

        statsPane.getChildren().addAll(frogStatsLabel, carStatsLabel);
        statsPane.setAlignment(Pos.CENTER);
        statsContainer.setTop(statsPane);
    }

    private Label createStatsLabel() {
        Label label = new Label();
        label.setAlignment(Pos.CENTER);
        return label;
    }

    public void increaseLevel() {
        level++;
        levelLabel.setText(LEVEL_TEXT_PREFIX + level);
    }

    public void showGameOver() {
        Text gameOverText = new Text("GAME OVER!\nPress SPACE to restart...");
        gameOverText.setTextAlignment(TextAlignment.CENTER);
        gameOverText.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, GAME_OVER_FONT_SIZE));
        StackPane.setAlignment(gameOverText, Pos.CENTER);

        gameOverPane.getChildren().add(gameOverText);
        gameOverPane.setStyle(GAME_OVER_STYLE);
        base.setStyle(GAME_OVER_STYLE);
    }

    public void hideGameOver() {
        gameOverPane.getChildren().clear();
        gameOverPane.setStyle(TRANSPARENT_STYLE);
        base.setStyle(TRANSPARENT_STYLE);
    }

    public Label getLevelLabel() {
        return levelLabel;
    }

    public StackPane getGameOverPane() {
        return gameOverPane;
    }
}
