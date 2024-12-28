package uk.ac.nott.cs.comp2013.froggergame.model.entities;

import java.util.Random;

import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.World;

public class Log extends Actor {
	private Spider spider;
	private static final Random RANDOM = new Random();

	public Log(String imageLink, int size, int xPos, int yPos, double speed) {
		super(xPos, yPos, speed);
		try {
			setImage(new javafx.scene.image.Image(imageLink, size, size, true, true));
		} catch (RuntimeException ignored) {
		}
		configureBounds(-300, 600, 700, -180);
	}


	public void addRandomSpider(String spiderImagePath) {
		if (spider == null && RANDOM.nextBoolean()) {
			spider = new Spider(this, spiderImagePath);
			if (this.getParent() instanceof World world) {
				world.addActor(spider);
			}
		}
	}

	public Spider getSpider() {
		return spider;
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
	}
}

