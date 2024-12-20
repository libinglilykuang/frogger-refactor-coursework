package uk.ac.nott.cs.comp2013.froggergame.model.logic.score;

import java.util.Stack;

public class ScoreManager {

    private int points = 0;
    private boolean scoreChanged = false;

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
}
