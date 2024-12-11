package p4_group_8_repo.model;

import javafx.scene.layout.Pane;
import p4_group_8_repo.view.ui.Digit;

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

    public ScoreManager(int digitSize, int digitStartX, int digitStartY, int digitSpacing) {
        this.digitSize = digitSize;
        this.digitStartX = digitStartX;
        this.digitStartY = digitStartY;
        this.digitSpacing = digitSpacing;
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
}
