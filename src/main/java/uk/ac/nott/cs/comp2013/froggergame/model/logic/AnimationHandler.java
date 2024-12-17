package uk.ac.nott.cs.comp2013.froggergame.model.logic;

import javafx.scene.image.Image;

public class AnimationHandler {

    /**
     * Updates the animation frame.
     *
     * @param now             Current timestamp.
     * @param animationInterval Duration of each frame.
     * @param images          Array of images for animation frames.
     * @return The current frame's image.
     */
    public static Image updateAnimation(long now, long animationInterval, Image[] images) {
        int frame = (int) ((now / animationInterval) % images.length);
        return images[frame];
    }

    /**
     * Updates the animation frame and returns the current frame index.
     *
     * @param now             Current timestamp.
     * @param animationInterval Duration of each frame.
     * @param frameCount      Total number of frames.
     * @return The current frame index.
     */
    public static int getCurrentFrameIndex(long now, long animationInterval, int frameCount) {
        return (int) ((now / animationInterval) % frameCount);
    }
}
