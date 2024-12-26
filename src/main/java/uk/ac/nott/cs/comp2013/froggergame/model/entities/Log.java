package uk.ac.nott.cs.comp2013.froggergame.model.entities;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.World;

import java.util.Random;

public class Log extends Actor {
	private Spider spider;
	private static final Random RANDOM = new Random();

	public Log(String imageLink, int size, int xpos, int ypos, double speed) {
		super( xpos, ypos, speed);
		setImage(new Image(imageLink, size, size, true, true));
		configureBounds(-300, 600, 700, -180);
	}


	public void addRandomSpider(String spiderImagePath) {
		if (spider == null && RANDOM.nextBoolean()) {
			spider = new Spider(this, spiderImagePath);
			if (this.getParent() instanceof World world) {
				world.addActor(spider);
				System.out.println("Spider added to world at position: " + spider.getX() + ", " + spider.getY());
			} else {
				System.err.println("Log is not attached to a World. Cannot add Spider.");
			}
		}
	}


	public void clearSpider() {
		this.spider = null;
	}

	@Override
	public void act(long now) {
		move(speed, 0);
		resetPositionIfOutOfBounds(speed);
		if (spider != null) {
			spider.updatePosition();
		}
	}


	@Override
	public void act(double elapsedTime) {
		resetPositionIfOutOfBounds(speed);
		if (spider != null) {
			spider.updatePosition();
		}
	}
}
