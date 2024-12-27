package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.util.Objects;
import uk.ac.nott.cs.comp2013.froggergame.view.managers.GameComponent;
import uk.ac.nott.cs.comp2013.froggergame.view.resource.ImageLoader;

public class End extends Pane implements GameComponent {
	private static final String DEFAULT_IMAGE_PATH = "End.png";
	private static final String ACTIVATED_IMAGE_PATH = "FrogEnd.png";

	private static final int IMAGE_SIZE = 60;
	private static final int EASTER_EGG_SIZE = 40;
	private static final double EASTER_EGG_DURATION = 5.0;
	private static final double EASTER_EGG_SPAWN_CHANCE = 0.3;

	private boolean isActivated;
	private final ImageView endImageView;
	private boolean hasEasterEgg;
	private ImageView easterEggImageView;
	private PauseTransition easterEggTimer;
	private PauseTransition spawnTimer;

	public End(int x, int y) {
		Image defaultImage = ImageLoader.loadImage(DEFAULT_IMAGE_PATH, IMAGE_SIZE, IMAGE_SIZE);
		endImageView = new ImageView(defaultImage);
		endImageView.setFitWidth(IMAGE_SIZE);
		endImageView.setFitHeight(IMAGE_SIZE);
		setLayoutX(x);
		setLayoutY(y);
		getChildren().add(endImageView);

		this.isActivated = false;
		this.hasEasterEgg = false;

		initializeTimers();
		scheduleNextEasterEgg();
	}

	public void setEnd() {
		if (!isActivated) {
			if (easterEggTimer != null) {
				easterEggTimer.stop();
			}
			if (spawnTimer != null) {
				spawnTimer.stop();
			}
			clearEasterEgg();
			Image activatedImage = ImageLoader.loadImage(ACTIVATED_IMAGE_PATH, IMAGE_SIZE, IMAGE_SIZE);
			endImageView.setImage(activatedImage);
			isActivated = true;
		}
	}

	private void initializeTimers() {
		easterEggTimer = new PauseTransition(Duration.seconds(EASTER_EGG_DURATION));
		easterEggTimer.setOnFinished(event -> {
			clearEasterEgg();
			scheduleNextEasterEgg();
		});
	}

	public void reset() {
		if (easterEggTimer != null) {
			easterEggTimer.stop();
		}
		if (spawnTimer != null) {
			spawnTimer.stop();
		}

		Image defaultImage = ImageLoader.loadImage(DEFAULT_IMAGE_PATH, IMAGE_SIZE, IMAGE_SIZE);
		endImageView.setImage(defaultImage);
		isActivated = false;
		hasEasterEgg = false;
		if (easterEggImageView != null) {
			getChildren().remove(easterEggImageView);
			easterEggImageView = null;
		}

		initializeTimers();
		scheduleNextEasterEgg();
	}

	public boolean isActivated() {
		return isActivated;
	}

	private void scheduleNextEasterEgg() {
		if (isActivated) return;

		double randomDelay = 1 + Math.random() * 9;
		if (spawnTimer != null) {
			spawnTimer.stop();
		}
		this.spawnTimer = new PauseTransition(Duration.seconds(randomDelay));
		this.spawnTimer.setOnFinished(event -> {
			if (!isActivated) {
				trySpawnEasterEgg();
			}
		});
		this.spawnTimer.play();
	}

	private void trySpawnEasterEgg() {
		if (!hasEasterEgg && !isActivated && Math.random() < EASTER_EGG_SPAWN_CHANCE) {
			initializeEasterEgg();
		} else {
			scheduleNextEasterEgg();
		}
	}

	private void initializeEasterEgg() {
		if (isActivated) return;
		String easterEggImagePath = Objects.requireNonNull(getClass().getResource("/images/egg.png")).toExternalForm();
		Image easterEggImage = new Image(easterEggImagePath, EASTER_EGG_SIZE, EASTER_EGG_SIZE, true, true);
		easterEggImageView = new ImageView(easterEggImage);
		easterEggImageView.setFitWidth(EASTER_EGG_SIZE);
		easterEggImageView.setFitHeight(EASTER_EGG_SIZE);
		double centerX = (double) (IMAGE_SIZE - EASTER_EGG_SIZE) / 2;
		double centerY = (double) (IMAGE_SIZE - EASTER_EGG_SIZE) / 2;
		easterEggImageView.setLayoutX(centerX);
		easterEggImageView.setLayoutY(centerY);
		getChildren().add(easterEggImageView);
		hasEasterEgg = true;

		if (easterEggTimer != null) {
			easterEggTimer.playFromStart();
		}
	}

	public void clearEasterEgg() {
		if (hasEasterEgg && easterEggImageView != null) {
			getChildren().remove(easterEggImageView);
			easterEggImageView = null;
			hasEasterEgg = false;
		}
	}

	public boolean hasEasterEgg() {
		return hasEasterEgg;
	}

	@Override
	public void render(Pane pane) {
		pane.getChildren().add(this);
	}
}

