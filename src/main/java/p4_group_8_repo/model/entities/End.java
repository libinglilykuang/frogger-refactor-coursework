package p4_group_8_repo.model.entities;

import javafx.scene.image.Image;
import p4_group_8_repo.model.base.Actor;

public class End extends Actor {

	private static final String DEFAULT_IMAGE_PATH = "/p4_group_8_repo/End.png";
	private static final String ACTIVATED_IMAGE_PATH = "/p4_group_8_repo/FrogEnd.png";
	private static final int DEFAULT_IMAGE_SIZE = 60;
	private static final int ACTIVATED_IMAGE_SIZE = 70;

	private boolean isActivated;
	private final Image defaultImage;
	private final Image activatedImage;

	public End(int x, int y) {
		this.defaultImage = loadImage(DEFAULT_IMAGE_PATH, DEFAULT_IMAGE_SIZE);
		this.activatedImage = loadImage(ACTIVATED_IMAGE_PATH, ACTIVATED_IMAGE_SIZE);
		setImage(defaultImage);
		setX(x);
		setY(y);
		this.isActivated = false;
	}

	@Override
	public void act(long now) {
		// TODO Auto-generated method st
	}

	public void setEnd() {
		setImage(activatedImage);
		isActivated = true;
	}

	public boolean isActivated() {
		return isActivated;
	}

	private Image loadImage(String path, int size) {
		if (getClass().getResource(path) == null) {
			throw new IllegalArgumentException("Resource not found: " + path);
		}
		return new Image(getClass().getResource(path).toExternalForm(), size, size, true, true);
	}
}