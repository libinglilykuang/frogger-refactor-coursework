package p4_group_8_repo.model.entities;

import javafx.scene.image.Image;
import p4_group_8_repo.model.base.Actor;

public class WetTurtle extends Actor {
	Image t1;
	Image t2;
	Image t3;
	Image t4;
	int speed;
	int i = 1;
	boolean bool = true;
	boolean sunk = false;
	@Override
	public void act(long now) {

				if (now/900000000  % 4 ==0) {
					setImage(t2);
					sunk = false;
					
				}
				else if (now/900000000 % 4 == 1) {
					setImage(t1);
					sunk = false;
				}
				else if (now/900000000 %4 == 2) {
					setImage(t3);
					sunk = false;
				} else if (now/900000000 %4 == 3) {
					setImage(t4);
					sunk = true;
				}
			
		move(speed , 0);
		if (getX() > 600 && speed>0)
			setX(-200);
		if (getX() < -75 && speed<0)
			setX(600);
	}
	public WetTurtle(int xpos, int ypos, int s, int w, int h) {
		t1 = new Image(getClass().getResource("/p4_group_8_repo/TurtleAnimation1.png").toExternalForm(), h, w, true, true);
		t2 = new Image(getClass().getResource("/p4_group_8_repo/TurtleAnimation2Wet.png").toExternalForm(), h, w, true, true);
		t3 = new Image(getClass().getResource("/p4_group_8_repo/TurtleAnimation3Wet.png").toExternalForm(), w, h, true, true);
		t4 = new Image(getClass().getResource("/p4_group_8_repo/TurtleAnimation4Wet.png").toExternalForm(), w, h, true, true);

		setX(xpos);
		setY(ypos);
		speed = s;
		setImage(t2);
	}
	public boolean isSunk() {
		return sunk;
	}

	public void setSunk(boolean sunk) {
		this.sunk = sunk;
	}
}
