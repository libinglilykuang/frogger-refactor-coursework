package uk.ac.nott.cs.comp2013.froggergame.model.logic.level;

import uk.ac.nott.cs.comp2013.froggergame.controller.GameEventListener;
import uk.ac.nott.cs.comp2013.froggergame.model.data.LevelConfig;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Level;

public class LevelManager {
    private static final int DEFAULT_ANIMAL_START_X = 300;
    private static final int DEFAULT_ANIMAL_START_Y = 733;
    private static final LevelConfig DEFAULT_LEVEL_CONFIG = new LevelConfig(5,4, 96, 133);

    private boolean levelReset = false;
    private int level = 1;
    private int reachedEnds = 0;
    private final int totalEnds;
    private final Animal animal;
    private final Level levelView;
    private GameEventListener eventListener;
    private boolean levelChanged = false;

    public LevelManager(Animal animal, Level levelView, int totalEnds) {
        if (animal == null || levelView == null) {
            throw new IllegalArgumentException("Animal and Level view cannot be null");
        }
        this.animal = animal;
        this.levelView = levelView;
        this.totalEnds = totalEnds;
        this.levelView.updateLevelDisplay(this.level);
    }

    public boolean hasLevelChanged() {
        return this.levelChanged;
    }

    public void resetLevelChangedFlag() {
        this.levelChanged = false;
    }

    public void setLevel(int level) {
        this.level = level;
        this.levelChanged = true;
    }

    public int getCurrentLevel() {
        return this.level;
    }

    public void increaseLevel() {
        this.level++;
        this.levelView.updateLevelDisplay(this.level);
        this.resetLevel();
    }

    public void onTargetReached() {
        this.reachedEnds++;
        if (this.reachedEnds == this.totalEnds) {
            this.increaseLevel();
        }
    }
    public Level getLevelView() {
        return this.levelView;
    }

    private void resetLevel() {
        this.reachedEnds = 0;
        LevelConfig config = this.getLevelConfig(this.level);
        this.levelView.resetLevelLayout(config);
        this.resetAnimalPosition();
    }

    public boolean isLevelReset() {
        return this.levelReset;
    }

    private void resetAnimalPosition() {
        this.animal.setX(DEFAULT_ANIMAL_START_X);
        this.animal.setY(DEFAULT_ANIMAL_START_Y);
    }

    private LevelConfig getLevelConfig(int currentLevel) {
        return DEFAULT_LEVEL_CONFIG;
    }

}
