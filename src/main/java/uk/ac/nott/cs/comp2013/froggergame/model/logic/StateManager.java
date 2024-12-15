package uk.ac.nott.cs.comp2013.froggergame.model.logic;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public interface StateManager {
    void reset();
    void update(long now);
    void update(double elapsedTime);
    void animateDeath(long now, String type, int frameLimit);
    boolean isNoMove();
    void addPoints(int value);
    void resetScoreChangedFlag();
    int getPoints();
    boolean isVictory();
    boolean hasScoreChanged();
    void move(double dx, double dy, Image img);
    Image getImageForKey(KeyCode key, boolean jumping);
}
