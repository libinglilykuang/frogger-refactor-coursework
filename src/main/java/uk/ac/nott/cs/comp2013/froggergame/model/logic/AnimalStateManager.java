package uk.ac.nott.cs.comp2013.froggergame.model.logic;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.view.resources.ImageManager;

public class AnimalStateManager implements StateManager{
    private final Animal animal;
    private boolean noMove = false;
    private int endCount = 0;
    private static final int GAME_WIN_END_COUNT = 5;
    private final ImageManager imageManager;
    private final ScoreManager scoreManager;
    private final DeathAnimationHandler deathAnimationHandler;

    public AnimalStateManager(Animal animal) {
        this.animal = animal;
        this.imageManager = new ImageManager();
        this.scoreManager = new ScoreManager();
        this.deathAnimationHandler = new DeathAnimationHandler();
    }

    @Override
    public void update(double elapsedTime) {

    }

    public void reset() {
        noMove = false;
        deathAnimationHandler.reset();
        scoreManager.resetScoreChangedFlag();
    }

    public void update(long now) {
        if (noMove) {
            noMove = deathAnimationHandler.animateDeath(now, animal, imageManager, "cardeath", 4);
        }
    }

    public boolean isVictory() {
        return endCount >= GAME_WIN_END_COUNT;
    }

    public boolean updateDeathAnimation(long now, String type, int frameLimit) {
        return deathAnimationHandler.animateDeath(now, animal, imageManager, type, frameLimit);
    }

    public boolean isNoMove() {
        return noMove;
    }

    public void move(double dx, double dy, KeyCode key, boolean jumping) {
        Image img = imageManager.getImageForKey(key, jumping);
        if (img != null) {
            animal.setImage(img);
            animal.move(dx, dy);
        }
    }

    public void addPoints(int points) {
        scoreManager.addPoints(points);
    }

    public int getPoints() {
        return scoreManager.getPoints();
    }

    public boolean hasScoreChanged() {
        return scoreManager.hasScoreChanged();
    }

    public void resetScoreChangedFlag() {
        scoreManager.resetScoreChangedFlag();
    }

}
