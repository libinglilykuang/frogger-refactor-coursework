package p4_group_8_repo.view.ui;

import javafx.scene.image.Image;
import p4_group_8_repo.model.base.Actor;
import p4_group_8_repo.model.base.ImageLoader;

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
	public void act(long now) {}
}
