package p4_group_8_repo.view.scene;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import p4_group_8_repo.controller.KeyEventManager;
import p4_group_8_repo.controller.TimerManager;
import p4_group_8_repo.model.base.Actor;
import java.util.List;

public abstract class World extends Pane {
    private final TimerManager timerManager;
    private final KeyEventManager keyEventManager;

    public World() {
        timerManager = new TimerManager(this);
        keyEventManager = new KeyEventManager(this);
    }

    public void start() {
        timerManager.createAndStartTimer();
    }

    public void add(Actor actor) {
        validateActor(actor);
        getChildren().add(actor);
    }

    private void validateActor(Actor actor) {
        if (actor == null) {
            throw new IllegalArgumentException("Cannot add null Actor.");
        }
        if (actor.getImage() == null) {
            throw new IllegalArgumentException("Cannot add ImageView with null image to MyStage.");
        }
    }

    public void stop() {
        timerManager.stopTimer();
    }

    public void removeActor(Actor actor) {
        getChildren().remove(actor);
    }

    public <A extends Actor> List<A> getObjects(Class<A> cls) {
        return getChildren().stream()
                .filter(cls::isInstance)
                .map(cls::cast)
                .toList();
    }

    public abstract void act(long now);
}
