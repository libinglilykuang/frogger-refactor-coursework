package uk.ac.nott.cs.comp2013.froggergame.model.logic.animation;

import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.view.resource.ImageManager;

public record DeathAnimationContext(Animal animal, ImageManager imageManager, String type, int frameLimit) {
}
