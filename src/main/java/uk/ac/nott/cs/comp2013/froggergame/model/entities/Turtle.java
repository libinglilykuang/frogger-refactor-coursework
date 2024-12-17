package uk.ac.nott.cs.comp2013.froggergame.model.entities;

import javafx.scene.image.Image;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.view.resources.ImageLoader;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.AnimationHandler;

public class Turtle extends Actor {
    private static final long ANIMATION_INTERVAL = 900_000_000;
    private final Image[] animationFrames;
    private final int speed;

    public Turtle(int xpos, int ypos, int speed, int width, int height) {
        this.animationFrames = new Image[]{
                ImageLoader.loadImage("TurtleAnimation1.png", width, height),
                ImageLoader.loadImage("TurtleAnimation2.png", width, height),
                ImageLoader.loadImage("TurtleAnimation3.png", width, height)
        };
        setX(xpos);
        setY(ypos);
        this.speed = speed;

        setImage(animationFrames[0]);
        configureBounds(-75, 600, 600, -200);
    }

    @Override
    public void act(long now) {
        setImage(AnimationHandler.updateAnimation(now, ANIMATION_INTERVAL, animationFrames));
        move(speed, 0);
        resetPositionIfOutOfBounds(speed);
    }

    @Override
    public void act(double elapsedTime) {
    }
}
