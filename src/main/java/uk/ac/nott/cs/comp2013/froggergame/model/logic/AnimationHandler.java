package uk.ac.nott.cs.comp2013.froggergame.model.logic;

public class AnimationHandler {
    public static int getCurrentFrameIndex(long now, long animationInterval, int frameCount) {
        return (int) ((now / animationInterval) % frameCount);
    }

    public static boolean isFrameUpdate(long now, int frameInterval) {
        return now % frameInterval == 0;
    }
}
