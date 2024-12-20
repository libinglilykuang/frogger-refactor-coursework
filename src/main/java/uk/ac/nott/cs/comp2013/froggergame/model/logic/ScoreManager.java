package uk.ac.nott.cs.comp2013.froggergame.model.logic;

import javafx.scene.layout.Pane;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Digit;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.Stack;

/**
 * ScoreManager is responsible for managing score-related operations,
 * including splitting scores into digits and updating score displays.
 */
public class ScoreManager {
    private final int digitSize=30;
    private final int digitStartX=140;
    private final int digitStartY=5;
    private final int digitSpacing=30;
    private final int SCORE_LABEL_X=15;
    private final int SCORE_LABEL_Y=30;
    private static final String SCORE_LABEL_TEXT = "SCORE:";
    private static final String SCORE_LABEL_FONT = "Arial";
    private static final int SCORE_LABEL_FONT_SIZE = 30;

    private int points = 0;
    private boolean scoreChanged = false;

    /**
     * Splits the given score into its individual digits.
     *
     * @param score The score to split.
     * @return A stack of digits representing the score.
     */
    public Stack<Integer> getDigits(int score) {
        if (score < 0) {
            throw new IllegalArgumentException("Score cannot be negative.");
        }

        Stack<Integer> digits = new Stack<>();
        if (score == 0) {
            digits.push(0);
        } else {
            while (score > 0) {
                digits.push(score % 10);
                score /= 10;
            }
        }
        return digits;
    }

    /**
     * Updates the score display on the given pane by clearing old digits
     * and adding new ones based on the provided score.
     *
     * @param pane  The pane where the score is displayed.
     * @param score The score to display.
     */
    public void updateScoreDisplay(Pane pane, int score) {
        // Remove old score digits
        pane.getChildren().removeIf(node -> node instanceof Digit);

        // Get digits for the new score
        Stack<Integer> digits = getDigits(score);

        // Display new digits
        int shift = 0;
        while (!digits.isEmpty()) {
            int digit = digits.pop();
            Digit digitNode = new Digit(digit, digitSize, digitStartX + shift, digitStartY);
            pane.getChildren().add(digitNode);
            shift += digitSpacing;
        }
    }

    public void createScoreLabel(MyStage background) {
        Text scoreLabel = new Text(SCORE_LABEL_TEXT);
        scoreLabel.setFont(Font.font(SCORE_LABEL_FONT, SCORE_LABEL_FONT_SIZE));
        scoreLabel.setFill(Color.WHITE);
        scoreLabel.setX(SCORE_LABEL_X);
        scoreLabel.setY(SCORE_LABEL_Y);
        background.getChildren().add(scoreLabel);
    }

    public void updateScoreIfChanged(Pane background, Animal animal) {
        if (animal.getStateManager().hasScoreChanged()) {
            int currentScore = animal.getStateManager().getPoints();
            updateScoreDisplay(background, currentScore);
            animal.getStateManager().resetScoreChangedFlag();
        }
    }

    public void addPoints(int value) {
        if (value != 0) {
            points = Math.max(0, points + value);
            scoreChanged = true;
        }
    }

    public int getPoints() {
        return points;
    }

    public boolean hasScoreChanged() {
        return scoreChanged;
    }

    public void resetScoreChangedFlag() {
        scoreChanged = false;
    }

}

