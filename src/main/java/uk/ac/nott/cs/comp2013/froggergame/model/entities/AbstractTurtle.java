package uk.ac.nott.cs.comp2013.froggergame.model.entities;

import javafx.scene.image.Image;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.view.resources.ImageLoader;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.AnimationHandler;

public abstract class AbstractTurtle extends Actor {
    private static final long ANIMATION_INTERVAL = 900_000_000;
    protected final Image[] animationFrames;
    protected final int speed;

    public AbstractTurtle(int xpos, int ypos, int speed, int width, int height, String[] framePaths) {
        this.animationFrames = loadAnimationFrames(framePaths, width, height);
        setX(xpos);
        setY(ypos);
        this.speed = speed;

        setImage(animationFrames[0]);
        configureBounds(-75, 600, 600, -200);
    }

    private Image[] loadAnimationFrames(String[] framePaths, int width, int height) {
        Image[] frames = new Image[framePaths.length];
        for (int i = 0; i < framePaths.length; i++) {
            frames[i] = ImageLoader.loadImage(framePaths[i], width, height);
        }
        return frames;
    }

    @Override
    public void act(long now) {
        int frame = AnimationHandler.getCurrentFrameIndex(now, ANIMATION_INTERVAL, animationFrames.length);
        updateStateBasedOnFrame(frame);
        setImage(animationFrames[frame]);
        move(speed, 0);
        resetPositionIfOutOfBounds(speed);
    }

    protected abstract void updateStateBasedOnFrame(int frame);

    @Override
    public void act(double elapsedTime) {
    }
}
