package p4_group_8_repo;

import javafx.scene.image.Image;

public class BackgroundImage extends Actor{

	@Override
	public void act(long now) {
		
		
	}
	
	public BackgroundImage(String imageLink) {
		//setImage(new Image(imageLink, 600, 800, true, true));
		Image image = new Image(imageLink);
		setImage(image);
		setFitWidth(600);
		setFitHeight(800);
		setPreserveRatio(false);
	}

}
