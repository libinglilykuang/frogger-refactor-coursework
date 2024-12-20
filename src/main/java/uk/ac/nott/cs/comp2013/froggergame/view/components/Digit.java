package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import uk.ac.nott.cs.comp2013.froggergame.view.resource.ImageLoader;

/**
 * Represents a digit image displayed in the game UI.
 */
public class Digit extends ImageView {

	private final int dimension;

	public Digit(int value, int dimension, int x, int y) {
		super();
		if (value < 0 || value > 9) {
			throw new IllegalArgumentException("Digit value must be between 0 and 9.");
		}

		this.dimension = dimension;
		Image digitImage = loadDigitImage(value);
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
}
