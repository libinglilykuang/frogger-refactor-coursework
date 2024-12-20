package uk.ac.nott.cs.comp2013.froggergame.model.logic.state;

import javafx.scene.input.KeyCode;

public interface StateManager {
    void update(double elapsedTime);
    void reset();
    boolean isVictory();
    void update(long now);
    void updateDeathAnimation(long now, String type, int frameLimit);
    boolean isNoMove();
    void moveAnimal(double dx, double dy, KeyCode key, boolean jumping);
    void addPoints(int points);
    int getPoints();
    boolean hasScoreChanged();
    void resetScoreChangedFlag();
}
