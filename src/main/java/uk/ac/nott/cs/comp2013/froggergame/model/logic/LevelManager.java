package uk.ac.nott.cs.comp2013.froggergame.model.logic;

import uk.ac.nott.cs.comp2013.froggergame.controller.GameEventListener;
import uk.ac.nott.cs.comp2013.froggergame.model.data.LevelConfig;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.End;
import uk.ac.nott.cs.comp2013.froggergame.view.managers.LevelView;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;

public class LevelManager {
    private boolean levelReset = false;
    private int level = 1;
    private int reachedEnds = 0;
    private final int totalEnds;
    private final Animal animal;
    private final LevelView levelView;
    private GameEventListener eventListener;
    private int points = 0;

    public LevelManager(Animal animal, LevelView levelView, int totalEnds) {
        this.animal = animal;
        this.levelView = levelView;
        this.totalEnds = totalEnds;
    }

    public void setLevel(int level) {
        this.level = level;
        levelView.updateLevelLabel(level);
    }

    public boolean isLevelReset() {
        return levelReset;
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
    public boolean isGameWon() {
        return reachedEnds == totalEnds;
    }


    private void resetLevel() {
        reachedEnds = 0;

        LevelConfig config = new LevelConfig(4, 96, 133);
        levelView.resetLevelLayout(totalEnds, config.getStartX(), config.getY(), config.getGap());
        animal.setX(300);
        animal.setY(733);
    }

    public void processLevelProgress(Animal animal) {
        for (End end : animal.getIntersectingObjects(End.class)) {
            if (!end.isActivated()) {
                end.setEnd();
                onTargetReached();
            }
        }
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


    public int getPoints() {
        return animal.getStateManager().getPoints();
    }

}
