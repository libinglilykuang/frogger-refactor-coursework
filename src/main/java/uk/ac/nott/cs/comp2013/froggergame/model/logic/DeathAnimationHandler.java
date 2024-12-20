package uk.ac.nott.cs.comp2013.froggergame.model.logic;

import javafx.scene.image.Image;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.view.resource.ImageManager;

public class DeathAnimationHandler {
    private int deathAnimationFrame = 0;
    private static final int FRAME_INTERVAL = 5;

    public boolean animateDeath(long now, Animal animal, ImageManager imageManager, String type, int frameLimit) {
        if (now % FRAME_INTERVAL == 0) {
            deathAnimationFrame++;
        }

        if (deathAnimationFrame < frameLimit) {
            Image deathImage = imageManager.getDeathImage(type, deathAnimationFrame);
            animal.setImage(deathImage);
            return true; // Animation in progress
        }

        resetAfterDeath(animal, imageManager);
        return false; // Animation complete
    }

    private void resetAfterDeath(Animal animal, ImageManager imageManager) {
        deathAnimationFrame = 0;
        animal.resetPosition();
        animal.setImage(imageManager.getDefaultImage());
    }

    public void reset() {
        deathAnimationFrame = 0;
    }
}
