package p4_group_8_repo.view.ui;

import javafx.scene.image.Image;
import p4_group_8_repo.model.base.Actor;

public class BackgroundImage extends Actor {

	@Override
	public void act(long now) {
		
		
	}
	
	public BackgroundImage(String imageLink) {
		//setImage(new Image(imageLink, 600, 800, true, true));
		Image image = new Image(imageLink);
		setImage(image);
		setFitWidth(600);
		setFitHeight(800);
		//setPreserveRatio(false);
	}

}
