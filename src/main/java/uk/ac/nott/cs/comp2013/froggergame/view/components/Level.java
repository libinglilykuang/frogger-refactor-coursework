package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import uk.ac.nott.cs.comp2013.froggergame.view.managers.LevelComposite;

public class Level {

    private static final String FONT_NAME = "Arial";
    private static final int FONT_SIZE = 30;
    private static final Color FONT_COLOR = Color.WHITE;
    private static final double LEVEL_LABEL_X = 250;
    private static final double LEVEL_LABEL_Y = 30;

    private final Pane background;
    private Text levelLabel;

    public Level(Pane background) {
        if (background == null) {
            throw new IllegalArgumentException("Background pane cannot be null");
        }
        this.background = background;
        this.levelLabel = null;
    }

    public void updateLevelDisplay(int level) {
        removeExistingLevelLabel();
        levelLabel = createLevelLabel(level);
        background.getChildren().add(levelLabel);
    }

    public void resetLevelLayout(int totalEnds, int startX, int y, int gap) {
        LevelComposite levelComposite = new LevelComposite();
        for (int i = 0; i < totalEnds; i++) {
            levelComposite.addComponent(new End(startX + i * gap, y));
        }
        levelComposite.render(background);
    }

    private void removeExistingLevelLabel() {
        if (levelLabel != null) {
            background.getChildren().remove(levelLabel);
            levelLabel = null;
        }
    }

    private Text createLevelLabel(int level) {
        Text text = new Text("LEVEL: " + level);
        text.setFont(Font.font(FONT_NAME, FONT_SIZE));
        text.setFill(FONT_COLOR);
        text.setX(LEVEL_LABEL_X);
        text.setY(LEVEL_LABEL_Y);
        return text;
    }
}
