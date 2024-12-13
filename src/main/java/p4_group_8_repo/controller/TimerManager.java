package p4_group_8_repo.controller;

import javafx.animation.AnimationTimer;
import p4_group_8_repo.model.base.Actor;
import p4_group_8_repo.view.scene.World;
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
