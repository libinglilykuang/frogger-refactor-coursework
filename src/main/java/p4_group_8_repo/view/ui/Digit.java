package p4_group_8_repo.view.ui;

import javafx.scene.image.Image;
import java.util.Objects;
import p4_group_8_repo.model.base.Actor;

/**
 * Represents a digit image displayed in the game UI.
 */
public class Digit extends Actor {

	private final int dimension;
	private Image digitImage;

	/**
	 * Creates a Digit instance with the specified value, dimension, and position.
	 *
	 * @param value The numerical value (0-9) to be represented.
	 * @param dimension The width and height of the digit image.
	 * @param x The X-coordinate of the digit's position.
	 * @param y The Y-coordinate of the digit's position.
	 * @throws IllegalArgumentException If the value is not between 0 and 9.
	 */
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

	/**
	 * Loads the image representing the specified digit value.
	 *
	 * @param value The numerical value (0-9) to be represented.
	 * @return The loaded Image instance.
	 * @throws IllegalArgumentException If the image resource is not found.
	 */
	private Image loadDigitImage(int value) {
		try {
			String resourcePath = String.format("/p4_group_8_repo/%d.png", value);
			return new Image(Objects.requireNonNull(getClass().getResource(resourcePath)).toExternalForm(), dimension, dimension, true, true);
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("Image resource for digit " + value + " not found.", e);
		}
	}

	/**
	 * Updates the behavior of the digit actor.
	 * Currently, the digit is static and does not have any dynamic behavior.
	 *
	 * @param now The current timestamp of the animation timer.
	 */
	@Override
	public void act(long now) {
		// No behavior for static digits; can be extended if needed.
	}
}
