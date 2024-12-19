package uk.ac.nott.cs.comp2013.froggergame.controller;

import javafx.animation.AnimationTimer;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.World;
import java.util.List;

public class TimerManager {
    private final World world;
    private AnimationTimer timer;

    public TimerManager(World world) {
        this.world = world;
    }

    public void initializeTimer() {
        if (timer == null) {
            timer = createTimer();
        }
    }

    public void startTimer() {
        if (timer == null) {
            throw new IllegalStateException("Timer not initialized. Call initializeTimer() first.");
        }
        timer.start();
    }

    public void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    private AnimationTimer createTimer() {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    world.act(now);
                    processActors(now);
                } catch (Exception e) {
                    stopTimer();
                }
            }
        };
    }

    private void processActors(long now) {
        List<Actor> actors = world.getObjects(Actor.class);
        for (Actor actor : actors) actor.act(now);
    }

}
