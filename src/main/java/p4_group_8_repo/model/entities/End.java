package p4_group_8_repo.model.entities;

import javafx.scene.image.Image;
import p4_group_8_repo.model.base.Actor;

public class End extends Actor {
	private boolean isActivated;
	private Image defaultImage;
	private Image activatedImage;

	@Override
	public void act(long now) {
		// TODO Auto-generated method st
	}

	public End(int x, int y) {
		defaultImage = new Image(getClass().getResource("/p4_group_8_repo/End.png").toExternalForm(), 60, 60, true, true);
		activatedImage = new Image(getClass().getResource("/p4_group_8_repo/FrogEnd.png").toExternalForm(), 70, 70, true, true);
		setImage(defaultImage);
		setX(x);
		setY(y);
		isActivated = false;
	}

	public void setEnd() {
		setImage(activatedImage);
		isActivated = true;
	}

	public boolean isActivated() {
		return isActivated;
	}
	

}
