package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.scene.image.ImageView;
import uk.ac.nott.cs.comp2013.froggergame.view.resource.ImageViewFactory;

/**
 * Represents a digit image displayed in the game UI.
 */
public class Digit extends ImageView {

	public Digit(int value, int dimension, int x, int y) {
		super();
		if (value < 0 || value > 9) {
			throw new IllegalArgumentException("Digit value must be between 0 and 9.");
		}

		ImageViewFactory.ConfiguredImageView configuredImage =
				ImageViewFactory.createConfiguredImageView(value + ".png", dimension, dimension);
		setImage(configuredImage.getImage());
		setFitWidth(configuredImage.getWidth());
		setFitHeight(configuredImage.getHeight());
		setX(x);
		setY(y);
	}
}

