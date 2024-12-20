package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import uk.ac.nott.cs.comp2013.froggergame.view.resource.ImageLoader;

public class Background extends ImageView {
	private static final double DEFAULT_WIDTH = 600;
	private static final double DEFAULT_HEIGHT = 800;

	public Background(String imageLink) {
		super();
		Image image = ImageLoader.loadImage(imageLink, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setImage(image);
		setFitWidth(DEFAULT_WIDTH);
		setFitHeight(DEFAULT_HEIGHT);
	}
}
