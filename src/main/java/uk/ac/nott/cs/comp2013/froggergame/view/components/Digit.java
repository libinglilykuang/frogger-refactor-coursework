package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.scene.image.Image;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.model.base.ImageLoader;

/**
 * Represents a digit image displayed in the game UI.
 */
public class Digit extends Actor {

	private final int dimension;
	private Image digitImage;

	public Digit(int value, int dimension, int x, int y) {
		if (value < 0 || value > 9) {
			throw new IllegalArgumentException("Digit value must be between 0 and 9.");
		}

		this.dimension = dimension;
		this.digitImage = loadDigitImage(value);
		setImage(digitImage);
		setFitWidth(dimension);
		setFitHeight(dimension);
		setX(x);
		setY(y);
	}

	private Image loadDigitImage(int value) {
		String resourcePath = String.format("%d.png", value);
		return ImageLoader.loadImage(resourcePath, dimension, dimension);
	}

	@Override
	public void act(long now) {
		// No behavior for static digits; can be extended if needed.
	}
}
