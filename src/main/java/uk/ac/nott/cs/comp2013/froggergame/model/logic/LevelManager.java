package uk.ac.nott.cs.comp2013.froggergame.model.logic;

import uk.ac.nott.cs.comp2013.froggergame.controller.GameEventListener;
import uk.ac.nott.cs.comp2013.froggergame.model.data.LevelConfig;
import uk.ac.nott.cs.comp2013.froggergame.view.components.End;
import uk.ac.nott.cs.comp2013.froggergame.view.managers.LevelView;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;

public class LevelManager {
    private static final int DEFAULT_ANIMAL_START_X = 300;
    private static final int DEFAULT_ANIMAL_START_Y = 733;
    private static final LevelConfig DEFAULT_LEVEL_CONFIG = new LevelConfig(4, 96, 133);

    private boolean levelReset = false;
    private int level = 1;
    private int reachedEnds = 0;
    private final int totalEnds;
    private final Animal animal;
    private final LevelView levelView;
    private GameEventListener eventListener;

    public LevelManager(Animal animal, LevelView levelView, int totalEnds) {
        this.animal = animal;
        this.levelView = levelView;
        this.totalEnds = totalEnds;
    }

    public void setLevel(int level) {
        this.level = level;
        levelView.updateLevelLabel(level);
    }

    public void increaseLevel() {
        level++;
        levelView.updateLevelLabel(level);
        resetLevel();
    }

    public void onTargetReached() {
        reachedEnds++;
        if (reachedEnds == totalEnds) {
            increaseLevel();
        }
    }

    private void resetLevel() {
        reachedEnds = 0;

        LevelConfig config = getLevelConfig(level);
        levelView.resetLevelLayout(totalEnds, config.getStartX(), config.getY(), config.getGap());
        resetAnimalPosition();
    }

    public boolean isLevelReset() {
        return levelReset;
    }

    private void resetAnimalPosition() {
        animal.setX(DEFAULT_ANIMAL_START_X);
        animal.setY(DEFAULT_ANIMAL_START_Y);
    }

    private LevelConfig getLevelConfig(int currentLevel) {
        return DEFAULT_LEVEL_CONFIG;
    }



    public boolean checkVictoryCondition() {
        if (animal.getStateManager().isVictory()) {
            if (eventListener != null) {
                eventListener.onGameWon(animal.getStateManager().getPoints());
            }
            return true;
        }
        return false;
    }

}
