package uk.ac.nott.cs.comp2013.froggergame.model.logic.level;

import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.model.data.LevelConfig;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Animal;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Crocodile;
import uk.ac.nott.cs.comp2013.froggergame.view.components.End;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Level;
import uk.ac.nott.cs.comp2013.froggergame.view.components.TimeBar;

public class LevelManager {
    private static final int DEFAULT_ANIMAL_START_X = 300;
    private static final int DEFAULT_ANIMAL_START_Y = 733;
    private int level = 1;
    private int reachedEnds = 0;

    private int totalEnds;
    private final Animal animal;
    private final Level levelView;
    private boolean levelChanged = false;
    private final TimeBar timeBar;
    private boolean levelTransitioning = false;

    public LevelManager(Animal animal, Level levelView, int totalEnds, TimeBar timeBar) {
        if (animal == null || levelView == null) {
            throw new IllegalArgumentException("Animal and Level view cannot be null");
        }
        this.animal = animal;
        this.levelView = levelView;
        this.totalEnds = totalEnds;
        this.timeBar = timeBar;
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

    public boolean isAllLevelsComplete() {
        return this.level >= 3;
    }

    public void onTargetReached() {
        if (levelTransitioning) {
            return;
        }
        levelTransitioning = true;
        this.reachedEnds++;
        System.out.println("ends ++");
        if (this.reachedEnds == this.totalEnds) {
            if (isAllLevelsComplete()) {
                System.out.println("All levels completed! Triggering game won.");
                //GameController.getInstance().onGameWon(stateManager.getCurrentPoints());
            } else {
                this.increaseLevel();
                if (animal != null) {
                    animal.resetPosition();
                }
                if (this.timeBar != null) {
                    this.timeBar.reset();
                }
            }
        }
        levelTransitioning = false;
    }

    public Level getLevelView() {
        return this.levelView;
    }

    private void resetLevel() {
        this.reachedEnds = 0;
        LevelConfig config = this.getLevelConfig(this.level);
        this.levelView.resetLevelLayout(config);
        this.resetAnimalPosition();

        for (End end : this.levelView.getEnds()) {
            end.reset();
        }
        this.totalEnds = config.getTotalEnds();

        if (this.level == 2) {
            String crocodileImagePath = getClass().getResource("/images/crocodile.png").toExternalForm();
            Crocodile crocodile = new Crocodile(140, 440, 2.0, crocodileImagePath);
            this.levelView.addActor(crocodile);
        }

        double speedFactor = config.getSpeedFactor();
        this.updateObjectsSpeed(speedFactor);
    }

    private void resetAnimalPosition() {
        this.animal.setX(DEFAULT_ANIMAL_START_X);
        this.animal.setY(DEFAULT_ANIMAL_START_Y);
    }

    public LevelConfig getLevelConfig(int currentLevel) {
        return switch (currentLevel) {
            case 1 -> new LevelConfig(1, 4, 96, 133, 1.0);
            case 2 -> new LevelConfig(2, 4, 96, 133, 1.5);
            case 3 -> new LevelConfig(2, 4, 96, 133, 2.0);
            default -> new LevelConfig(5, 4, 96, 133, 1.0);
        };
    }

    private void updateObjectsSpeed(double speedFactor) {
        for (Actor actor : levelView.getActors()) {
            actor.setSpeedFactor(speedFactor);
        }
    }

}
