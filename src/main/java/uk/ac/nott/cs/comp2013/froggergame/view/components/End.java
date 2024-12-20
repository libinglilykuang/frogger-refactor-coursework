package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import uk.ac.nott.cs.comp2013.froggergame.view.resource.ImageLoader;

public class End extends ImageView {

	private static final String DEFAULT_IMAGE_PATH = "End.png";
	private static final String ACTIVATED_IMAGE_PATH = "FrogEnd.png";
	private static final int IMAGE_SIZE = 60;

	private boolean isActivated;
	private final Image defaultImage;
	private final Image activatedImage;

	public End(int x, int y) {
		this.defaultImage = ImageLoader.loadImage(DEFAULT_IMAGE_PATH, IMAGE_SIZE, IMAGE_SIZE);
		this.activatedImage = ImageLoader.loadImage(ACTIVATED_IMAGE_PATH, IMAGE_SIZE, IMAGE_SIZE);
		setImage(defaultImage);
		this.isActivated = false;

		setFitWidth(IMAGE_SIZE);
		setFitHeight(IMAGE_SIZE);
		setX(x);
		setY(y);
	}

	public void setEnd() {
		setImage(activatedImage);
		isActivated = true;
	}

	public boolean isActivated() {
		return isActivated;
	}
}