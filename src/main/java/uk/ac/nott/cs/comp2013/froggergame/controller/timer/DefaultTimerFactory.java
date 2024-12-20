package uk.ac.nott.cs.comp2013.froggergame.controller.timer;

import javafx.animation.AnimationTimer;

public class DefaultTimerFactory implements TimerFactory {

    @Override
    public AnimationTimer createTimer(Runnable onTick) {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    onTick.run();
                } catch (Exception e) {
                    handleException(e);
                }
            }
        };
    }

    private void handleException(Exception e) {
        System.err.println("Error in AnimationTimer: " + e.getMessage());
        e.printStackTrace();
    }
}
