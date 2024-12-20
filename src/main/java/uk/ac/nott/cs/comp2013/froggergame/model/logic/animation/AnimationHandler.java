package uk.ac.nott.cs.comp2013.froggergame.model.logic.animation;

public class AnimationHandler {
    public static int getCurrentFrameIndex(long now, long animationInterval, int frameCount) {
        return (int) ((now / animationInterval) % frameCount);
    }
}
