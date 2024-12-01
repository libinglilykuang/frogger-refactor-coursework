package p4_group_8_repo;

import javafx.scene.image.Image;

import java.util.Objects;

public class digit extends Actor{
	int dim;
	Image im1;

	@Override
	public void act(long now) {
		// TODO Auto-generated method stub
		
	}
	
	public digit(int n, int dim, int x, int y) {
		im1 = new Image(Objects.requireNonNull(getClass().getResource("/p4_group_8_repo/\" + n + \".png")).toExternalForm(), dim, dim, true, true);////
		System.out.println(getClass().getResource("/p4_group_8_repo/" + n + ".png"));

		setImage(im1);
		setX(x);
		setY(y);
	}
	
}
