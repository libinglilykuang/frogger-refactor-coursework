package p4_group_8_repo.model;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import p4_group_8_repo.model.entities.End;
import p4_group_8_repo.model.entities.Animal;

public class LevelManager {
    private boolean levelReset = false;
    private int level = 1;
    private int reachedEnds = 0;
    private final int totalEnds = 5;
    private final Pane background;
    private final Animal animal;

    public LevelManager(Pane background, Animal animal) {
        this.background = background;
        this.animal = animal;
    }

    public void setLevel(int level) {
        this.level = level;
        updateLevelLabel();
    }

    public boolean isLevelReset() {
        return levelReset;
    }

    public void increaseLevel() {
        level++;
        updateLevelLabel();
        resetLevel();
    }

    public void onTargetReached() {
        reachedEnds++;
        if (reachedEnds == totalEnds) {
            increaseLevel();
        }
    }

    private void updateLevelLabel() {
        background.getChildren().removeIf(node -> node instanceof Text && ((Text) node).getText().startsWith("LEVEL:"));

        Text levelLabel = new Text("LEVEL: " + level);
        levelLabel.setFont(Font.font("Arial", 30));
        levelLabel.setFill(Color.WHITE);
        levelLabel.setX(250);
        levelLabel.setY(30);
        background.getChildren().add(levelLabel);
    }

    private void resetLevel() {
        reachedEnds = 0;
        background.getChildren().removeIf(node -> node instanceof End);

        int startX = 4;
        int y = 96;
        int gap = 133;

        for (int i = 0; i < totalEnds; i++) {
            background.getChildren().add(new End(startX + i * gap, y));
        }

        animal.setX(300);
        animal.setY(733);

        Platform.runLater(background::layout);
    }
}
