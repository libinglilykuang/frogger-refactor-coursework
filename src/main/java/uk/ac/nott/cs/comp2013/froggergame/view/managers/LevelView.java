package uk.ac.nott.cs.comp2013.froggergame.view.managers;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import uk.ac.nott.cs.comp2013.froggergame.view.components.End;

public class LevelView {
    private static final String FONT_NAME = "Arial";
    private static final int FONT_SIZE = 30;
    private static final Color FONT_COLOR = Color.WHITE;
    private static final double LEVEL_LABEL_X = 250;
    private static final double LEVEL_LABEL_Y = 30;

    private final Pane background;

    public LevelView(Pane background) {
        if (background == null) {
            throw new IllegalArgumentException("Background pane cannot be null");
        }
        this.background = background;
    }

    public void updateLevelLabel(int level) {
        removeNodesByPredicate(node -> node instanceof Text && ((Text) node).getText().startsWith("LEVEL:"));

        Text levelLabel = createLevelLabel(level);
        background.getChildren().add(levelLabel);
    }

    public void resetLevelLayout(int totalEnds, int startX, int y, int gap) {
        if (totalEnds <= 0 || gap < 0) {
            throw new IllegalArgumentException("Total ends must be positive and gap must be non-negative");
        }

        removeNodesByPredicate(node -> node instanceof End);

        for (int i = 0; i < totalEnds; i++) {
            background.getChildren().add(new End(startX + i * gap, y));
        }
    }

    private Text createLevelLabel(int level) {
        Text levelLabel = new Text("LEVEL: " + level);
        levelLabel.setFont(Font.font(FONT_NAME, FONT_SIZE));
        levelLabel.setFill(FONT_COLOR);
        levelLabel.setX(LEVEL_LABEL_X);
        levelLabel.setY(LEVEL_LABEL_Y);
        return levelLabel;
    }

    private void removeNodesByPredicate(java.util.function.Predicate<javafx.scene.Node> predicate) {
        background.getChildren().removeIf(predicate);
    }
}
