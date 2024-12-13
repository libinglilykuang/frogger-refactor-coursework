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

    public void createAndStartTimer() {
        if (timer == null) {
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    world.act(now);
                    actOnActors(now);
                }
            };
        }
        timer.start();
    }

    public void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    private void actOnActors(long now) {
        List<Actor> actors = world.getObjects(Actor.class);
        for (Actor actor : actors) {
            actor.act(now);
        }
    }
}
