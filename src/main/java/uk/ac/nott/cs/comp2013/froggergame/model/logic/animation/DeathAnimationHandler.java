package uk.ac.nott.cs.comp2013.froggergame.model.logic.animation;

import javafx.scene.image.Image;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.view.resource.ImageManager;

public class DeathAnimationHandler {
    private int deathAnimationFrame = 0;
    private static final int DEFAULT_FRAME_INTERVAL = 5;

    private final int frameInterval;

    public DeathAnimationHandler() {
        this(DEFAULT_FRAME_INTERVAL);
    }

    public DeathAnimationHandler(int frameInterval) {
        this.frameInterval = frameInterval;
    }

    public AnimationState animateDeath(long now, DeathAnimationContext context) {
        if (shouldUpdateFrame(now)) {
            deathAnimationFrame++;
        }

        if (deathAnimationFrame < context.frameLimit()) {
            updateDeathImage(context.animal(), context.imageManager(), context.type());
            return AnimationState.IN_PROGRESS;
        }

        resetAfterDeath(context.animal(), context.imageManager());
        return AnimationState.COMPLETE;
    }

    private boolean shouldUpdateFrame(long now) {
        return now % frameInterval == 0;
    }

    private void updateDeathImage(Animal animal, ImageManager imageManager, String type) {
        Image deathImage = imageManager.getDeathImage(type, deathAnimationFrame);
        animal.setImage(deathImage);
    }

    private void resetAfterDeath(Animal animal, ImageManager imageManager) {
        deathAnimationFrame = 0;
        animal.resetPosition();
        animal.setImage(imageManager.getDefaultImage());
    }

    public void reset() {
        deathAnimationFrame = 0;
    }

    public enum AnimationState {
        IN_PROGRESS,
        COMPLETE
    }
}
