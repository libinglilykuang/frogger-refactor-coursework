package uk.ac.nott.cs.comp2013.froggergame.controller;

public interface GameEventListener {
    void onGameWon(int points);
    void onGameOver();
    void update(long now);
    void update(double elapsedTime);
}
