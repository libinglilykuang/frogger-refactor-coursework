package p4_group_8_repo.view.ui;

import javafx.scene.image.Image;
import p4_group_8_repo.model.base.Actor;

public class BackgroundImage extends Actor {

	private static final double DEFAULT_WIDTH = 600;
	private static final double DEFAULT_HEIGHT = 800;

	public BackgroundImage(String imageLink) {
		Image image = loadImage(imageLink);
		setImage(image);
		setFitWidth(DEFAULT_WIDTH);
		setFitHeight(DEFAULT_HEIGHT);
	}

	@Override
	public void act(long now) {}

	private Image loadImage(String imageLink) {
		try {
			return new Image(imageLink, DEFAULT_WIDTH, DEFAULT_HEIGHT, false, true);
		} catch (IllegalArgumentException e) {
			System.err.println("Error loading image: " + imageLink);
			throw e; // Propagate the exception to ensure the issue is visible
		}
	}
}
