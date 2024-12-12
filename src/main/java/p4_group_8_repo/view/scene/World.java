package p4_group_8_repo.view.scene;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import p4_group_8_repo.model.base.Actor;

public abstract class World extends Pane {

    private AnimationTimer timer;

    public World() {
        sceneProperty().addListener(this::configureKeyEvents);
    }

    private void configureKeyEvents(ObservableValue<? extends Scene> observable, Scene oldScene, Scene newScene) {
        if (newScene != null) {
            newScene.setOnKeyPressed(event -> processKeyEvent(event, true));
            newScene.setOnKeyReleased(event -> processKeyEvent(event, false));
        }
    }

    private void processKeyEvent(KeyEvent event, boolean isKeyPressed) {
        handleSceneKeyEvent(event, isKeyPressed);
        handleActorsKeyEvent(event, isKeyPressed);
    }

    private void handleSceneKeyEvent(KeyEvent event, boolean isKeyPressed) {
        if (isKeyPressed && getOnKeyPressed() != null) {
            getOnKeyPressed().handle(event);
        } else if (!isKeyPressed && getOnKeyReleased() != null) {
            getOnKeyReleased().handle(event);
        }
    }

    private void handleActorsKeyEvent(KeyEvent event, boolean isKeyPressed) {
        for (Actor actor : getObjects(Actor.class)) {
            if (isKeyPressed && actor.getOnKeyPressed() != null) {
                actor.getOnKeyPressed().handle(event);
            } else if (!isKeyPressed && actor.getOnKeyReleased() != null) {
                actor.getOnKeyReleased().handle(event);
            }
        }
    }

    public void createTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                act(now);
                actOnActors(now);
            }
        };
    }

    private void actOnActors(long now) {
        for (Actor actor : getObjects(Actor.class)) {
            actor.act(now);
        }
    }

    public void start() {
        if (timer == null) {
            createTimer();
        }
        timer.start();
    }

    public void stop() {
        if (timer != null) {
            timer.stop();
        }
    }

    public void add(Actor actor) {
        if (actor == null) {
            throw new IllegalArgumentException("Cannot add null Actor to MyStage.");
        }

        if (((ImageView) actor).getImage() == null) {
            throw new IllegalArgumentException("Cannot add ImageView with null image to MyStage.");
        }

        getChildren().add(actor);
    }


    public void removeActor(Actor actor) {
        getChildren().remove(actor);
    }

    public <A extends Actor> List<A> getObjects(Class<A> cls) {
        List<A> result = new ArrayList<>();
        for (Node node : getChildren()) {
            if (cls.isInstance(node)) {
                result.add(cls.cast(node));
            }
        }
        return result;
    }

    public abstract void act(long now);
}
