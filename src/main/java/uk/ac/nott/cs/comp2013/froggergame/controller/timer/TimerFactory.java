package uk.ac.nott.cs.comp2013.froggergame.controller.timer;

import javafx.animation.AnimationTimer;

public interface TimerFactory {
    AnimationTimer createTimer(Runnable onTick);
}
