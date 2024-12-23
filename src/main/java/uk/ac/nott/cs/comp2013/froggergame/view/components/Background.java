package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.scene.image.ImageView;
import uk.ac.nott.cs.comp2013.froggergame.view.resource.ImageViewFactory;

public class Background extends ImageView {
	private static final double DEFAULT_WIDTH = 600;
	private static final double DEFAULT_HEIGHT = 800;

	public Background(String imageLink) {
		super();
		ImageViewFactory.ConfiguredImageView configuredImage =
				ImageViewFactory.createConfiguredImageView(imageLink, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setImage(configuredImage.getImage());
		setFitWidth(configuredImage.getWidth());
		setFitHeight(configuredImage.getHeight());
	}
}
