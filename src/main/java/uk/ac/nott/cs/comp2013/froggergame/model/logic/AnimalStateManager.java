package uk.ac.nott.cs.comp2013.froggergame.model.logic;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uk.ac.nott.cs.comp2013.froggergame.model.base.ImageLoader;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;

public class AnimalStateManager implements StateManager{
    private final Animal animal;
    private boolean noMove = false;
    private int points = 0;
    private int deathAnimationFrame = 0;
    private boolean scoreChanged = false;
    private int endCount = 0;
    private static final int GAME_WIN_END_COUNT = 5;
    private static final int DEATH_ANIMATION_FRAME_INTERVAL = 5;

    private final Image imgW1, imgA1, imgS1, imgD1;
    private final Image imgW2, imgA2, imgS2, imgD2;

    public AnimalStateManager(Animal animal) {
        this.animal = animal;
        // Load images
        imgW1 = ImageLoader.loadImage("froggerUp.png", 40, 40);
        imgA1 = ImageLoader.loadImage("froggerLeft.png", 40, 40);
        imgS1 = ImageLoader.loadImage("froggerDown.png", 40, 40);
        imgD1 = ImageLoader.loadImage("froggerRight.png", 40, 40);
        imgW2 = ImageLoader.loadImage("froggerUpJump.png", 40, 40);
        imgA2 = ImageLoader.loadImage("froggerLeftJump.png", 40, 40);
        imgS2 = ImageLoader.loadImage("froggerDownJump.png", 40, 40);
        imgD2 = ImageLoader.loadImage("froggerRightJump.png", 40, 40);
    }
    public boolean isVictory() {
        return endCount >= GAME_WIN_END_COUNT;
    }
    public boolean hasScoreChanged() {
        return scoreChanged;
    }

    public void resetScoreChangedFlag() {
        scoreChanged = false;
    }


    public int getPoints() {
        return points;
    }
    public void reset() {
        noMove = false;
        //points = 0;
        deathAnimationFrame = 0;
        scoreChanged = false;
    }

    public void update(long now) {
        if (isNoMove()) {
            animateDeath(now, "cardeath", 4);
        }
    }

    @Override
    public void update(double elapsedTime) {

    }

    public boolean isNoMove() {
        return false;
    }

    public void move(double dx, double dy, Image img) {
        animal.setImage(img);
        animal.move(dx, dy);
    }

    public void addPoints(int value) {
        if (value != 0) {
            points = Math.max(0, points + value);
            scoreChanged = true;
        }
    }


    public Image getImageForKey(KeyCode key, boolean jumping) {
        return switch (key) {
            case W -> jumping ? imgW2 : imgW1;
            case A -> jumping ? imgA2 : imgA1;
            case S -> jumping ? imgS2 : imgS1;
            case D -> jumping ? imgD2 : imgD1;
            default -> null;
        };
    }

    public void animateDeath(long now, String type, int frameLimit) {
        noMove = true;

        if (now % DEATH_ANIMATION_FRAME_INTERVAL == 0) {
            deathAnimationFrame++;
        }

        if (deathAnimationFrame < frameLimit) {
            String filePath = String.format("%s%d.png", type, deathAnimationFrame);
            Image deathImage = ImageLoader.loadImage(filePath, 40, 40);
            animal.setImage(deathImage);
        }

        if (deathAnimationFrame == frameLimit) {
            resetAfterDeath();
        }
    }

    private void resetAfterDeath() {
        noMove = false;
        deathAnimationFrame = 0;
        animal.resetPosition();
        animal.setImage(getDefaultImage());
        addPoints(-50);
    }

    public Image getDefaultImage() {
        return ImageLoader.loadImage("froggerUp.png", 40, 40);
    }


}
