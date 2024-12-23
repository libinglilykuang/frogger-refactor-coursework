package uk.ac.nott.cs.comp2013.froggergame.controller.timer;

import javafx.animation.AnimationTimer;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.World;
import java.util.List;
import java.util.function.BiConsumer;

public class TimerManager {
    private final World world;
    private AnimationTimer timer;
    private final BiConsumer<Long, Actor> actorProcessor;

    public TimerManager(World world, BiConsumer<Long, Actor> actorProcessor) {
        this.world = world;
        this.actorProcessor = actorProcessor;
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
                    if (world != null) {
                        world.act(now);
                        List<Actor> actors = world.getActors();
                        processActors(now, actors);
                    }
                } catch (Exception e) {
                    stopTimer();
                }
            }
        };
    }

    public void processActors(long now, List<Actor> actors) {
        if (actors == null || actors.isEmpty()) {
            return;
        }
        for (Actor actor : actors) {
            if (actor == null) {
                continue;
            }
            actorProcessor.accept(now, actor);
        }
    }

}
