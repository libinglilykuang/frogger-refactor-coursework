package uk.ac.nott.cs.comp2013.froggergame.controller;

import javafx.animation.AnimationTimer;
import uk.ac.nott.cs.comp2013.froggergame.controller.processor.ActorProcessor;
import uk.ac.nott.cs.comp2013.froggergame.controller.timer.TimerFactory;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.World;
import java.util.List;

public class TimerManager {
    private final World world;
    private final ActorProcessor actorProcessor;
    private final TimerFactory timerFactory;
    private AnimationTimer timer;

    public TimerManager(World world, ActorProcessor actorProcessor, TimerFactory timerFactory) {
        this.world = world;
        this.actorProcessor = actorProcessor;
        this.timerFactory = timerFactory;
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
        return timerFactory.createTimer(() -> {
            try {
                long now = System.nanoTime();
                if (world != null) {
                    world.act(now);
                }
                processActors(now);
            } catch (Exception e) {
                stopTimer();
            }
        });
    }

    private void processActors(long now) {
        List<Actor> actors = world.getObjects(Actor.class);
        for (Actor actor : actors) {
            actorProcessor.process(now, actor);
        }
    }
}
