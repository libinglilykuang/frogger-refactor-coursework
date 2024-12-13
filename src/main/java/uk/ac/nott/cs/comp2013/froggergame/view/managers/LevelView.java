package uk.ac.nott.cs.comp2013.froggergame.view.managers;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.End;

public class LevelView {
    private final Pane background;

    public LevelView(Pane background) {
        this.background = background;
    }

    public void updateLevelLabel(int level) {
        background.getChildren().removeIf(node -> node instanceof Text && ((Text) node).getText().startsWith("LEVEL:"));

        Text levelLabel = new Text("LEVEL: " + level);
        levelLabel.setFont(Font.font("Arial", 30));
        levelLabel.setFill(Color.WHITE);
        levelLabel.setX(250);
        levelLabel.setY(30);
        background.getChildren().add(levelLabel);
    }

    public void resetLevelLayout(int totalEnds, int startX, int y, int gap) {
        background.getChildren().removeIf(node -> node instanceof End);

        for (int i = 0; i < totalEnds; i++) {
            background.getChildren().add(new End(startX + i * gap, y));
        }
    }
}
