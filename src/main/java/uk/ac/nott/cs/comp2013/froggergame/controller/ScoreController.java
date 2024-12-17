package uk.ac.nott.cs.comp2013.froggergame.controller;

public class ScoreController {
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
}
