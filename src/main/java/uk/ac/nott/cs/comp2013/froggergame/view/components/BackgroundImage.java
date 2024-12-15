package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.scene.image.Image;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.model.base.ImageLoader;

public class BackgroundImage extends Actor {

	private static final double DEFAULT_WIDTH = 600;
	private static final double DEFAULT_HEIGHT = 800;

	public BackgroundImage(String imageLink) {
		Image image = ImageLoader.loadImage(imageLink, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setImage(image);
		setFitWidth(DEFAULT_WIDTH);
		setFitHeight(DEFAULT_HEIGHT);
	}

	@Override
	public void act(long now) {

	}

	@Override
	public void act(double elapsedTime) {}
}
