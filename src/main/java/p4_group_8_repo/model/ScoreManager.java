package p4_group_8_repo.model;

import javafx.scene.layout.Pane;
import p4_group_8_repo.view.scene.MyStage;
import p4_group_8_repo.view.ui.Digit;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Stack;

/**
 * ScoreManager is responsible for managing score-related operations,
 * including splitting scores into digits and updating score displays.
 */
public class ScoreManager {
    private final int digitSize;
    private final int digitStartX;
    private final int digitStartY;
    private final int digitSpacing;
    private final int SCORE_LABEL_X;
    private final int SCORE_LABEL_Y;

    public ScoreManager(int digitSize, int digitStartX, int digitStartY, int digitSpacing) {
        this.digitSize = digitSize;
        this.digitStartX = digitStartX;
        this.digitStartY = digitStartY;
        this.digitSpacing = digitSpacing;
        this.SCORE_LABEL_X = 15; // Default X position for score label
        this.SCORE_LABEL_Y = 30; // Default Y position for score label
    }

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
        Text scoreLabel = new Text("SCORE:");
        scoreLabel.setFont(Font.font("Arial", 30));
        scoreLabel.setFill(Color.WHITE);
        scoreLabel.setX(SCORE_LABEL_X);
        scoreLabel.setY(SCORE_LABEL_Y);
        background.getChildren().add(scoreLabel);
    }
}
