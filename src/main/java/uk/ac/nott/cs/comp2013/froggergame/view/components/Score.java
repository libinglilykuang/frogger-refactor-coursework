package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.Stack;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;

public class Score {

    private static final int DEFAULT_DIGIT_SIZE = 30;
    private static final int DEFAULT_DIGIT_START_X = 140;
    private static final int DEFAULT_DIGIT_START_Y = 5;
    private static final int DEFAULT_DIGIT_SPACING = 30;
    private static final String DEFAULT_SCORE_LABEL_TEXT = "SCORE:";
    private static final String DEFAULT_SCORE_LABEL_FONT = "Arial";
    private static final int DEFAULT_SCORE_LABEL_FONT_SIZE = 30;
    private static final int DEFAULT_SCORE_LABEL_X = 15;
    private static final int DEFAULT_SCORE_LABEL_Y = 30;

    public void updateScoreDisplay(Pane pane, int score, ScoreManager scoreManager) {
        pane.getChildren().removeIf(node -> node instanceof Digit);
        addScoreDigitsToPane(pane, scoreManager.getDigits(score));
    }

    private void addScoreDigitsToPane(Pane pane, Stack<Integer> digits) {
        int shift = 0;
        while (!digits.isEmpty()) {
            int digit = digits.pop();
            Digit digitNode = new Digit(digit, DEFAULT_DIGIT_SIZE, DEFAULT_DIGIT_START_X + shift, DEFAULT_DIGIT_START_Y);
            pane.getChildren().add(digitNode);
            shift += DEFAULT_DIGIT_SPACING;
        }
    }

    public void createScoreLabel(Pane background) {
        Text scoreLabel = new Text(DEFAULT_SCORE_LABEL_TEXT);
        scoreLabel.setFont(Font.font(DEFAULT_SCORE_LABEL_FONT, DEFAULT_SCORE_LABEL_FONT_SIZE));
        scoreLabel.setFill(Color.WHITE);
        scoreLabel.setX(DEFAULT_SCORE_LABEL_X);
        scoreLabel.setY(DEFAULT_SCORE_LABEL_Y);
        background.getChildren().add(scoreLabel);
    }
}
