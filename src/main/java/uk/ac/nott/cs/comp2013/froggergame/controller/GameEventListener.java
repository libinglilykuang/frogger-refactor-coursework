package uk.ac.nott.cs.comp2013.froggergame.controller;

public interface GameEventListener {
    void onGameWon(int points);
    void onGameOver();
}
