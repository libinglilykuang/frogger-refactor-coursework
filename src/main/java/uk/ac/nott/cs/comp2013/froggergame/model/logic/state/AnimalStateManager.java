package uk.ac.nott.cs.comp2013.froggergame.model.logic.state;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.animation.DeathAnimationContext;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.animation.DeathAnimationHandler;
import uk.ac.nott.cs.comp2013.froggergame.view.resource.ImageManager;

public class AnimalStateManager implements StateManager {

    private final Animal animal;
    private boolean noMove = false;
    private int endCount = 0;

    private static final int DEFAULT_GAME_WIN_END_COUNT = 5;
    private static final String DEFAULT_DEATH_TYPE = "cardeath";
    private static final int DEFAULT_DEATH_FRAMES = 4;

    private final int gameWinEndCount;
    private final ImageManager imageManager;
    private final ScoreManager scoreManager;
    private final DeathAnimationHandler deathAnimationHandler;

    public AnimalStateManager(Animal animal) {
        this(animal, DEFAULT_GAME_WIN_END_COUNT);
    }

    public AnimalStateManager(Animal animal, int gameWinEndCount) {
        this.animal = animal;
        this.gameWinEndCount = gameWinEndCount;
        this.imageManager = new ImageManager();
        this.scoreManager = new ScoreManager();
        this.deathAnimationHandler = new DeathAnimationHandler();
    }

    public void reset() {
        noMove = false;
        endCount = 0;
        deathAnimationHandler.reset();
        scoreManager.resetScoreChangedFlag();
    }

    public boolean isVictory() {
        return endCount >= gameWinEndCount;
    }

    public void updateDeathAnimation(long now, String type, int frameLimit) {
        DeathAnimationHandler.AnimationState state = deathAnimationHandler.animateDeath(now, new DeathAnimationContext(animal, imageManager, type, frameLimit));
        noMove = (state == DeathAnimationHandler.AnimationState.IN_PROGRESS);
    }

    public boolean isNoMove() {
        return noMove;
    }

    public void moveAnimal(double dx, double dy, KeyCode key, boolean jumping) {
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
        int points = scoreManager.getPoints();
        return points;
    }


    public boolean hasScoreChanged() {
        return scoreManager.hasScoreChanged();
    }

    public void resetScoreChangedFlag() {
        scoreManager.resetScoreChangedFlag();
    }

    @Override
    public void update(double elapsedTime) {
        if (noMove) {
            DeathAnimationHandler.AnimationState state = deathAnimationHandler.animateDeath(System.nanoTime(), new DeathAnimationContext(animal, imageManager, DEFAULT_DEATH_TYPE, DEFAULT_DEATH_FRAMES));
            noMove = (state == DeathAnimationHandler.AnimationState.IN_PROGRESS);
        }
    }

    @Override
    public void update(long now) {
        if (noMove) {
            DeathAnimationHandler.AnimationState state = deathAnimationHandler.animateDeath(now, new DeathAnimationContext(animal, imageManager, DEFAULT_DEATH_TYPE, DEFAULT_DEATH_FRAMES));
            noMove = (state == DeathAnimationHandler.AnimationState.IN_PROGRESS);
        }
    }
}
