package p4_group_8_repo.model.logic;

import p4_group_8_repo.model.data.LevelConfig;
import p4_group_8_repo.view.LevelView;
import p4_group_8_repo.model.entities.Animal;

public class LevelManager {
    private boolean levelReset = false;
    private int level = 1;
    private int reachedEnds = 0;
    private final int totalEnds;
    private final Animal animal;
    private final LevelView levelView;

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

    private void resetLevel() {
        reachedEnds = 0;

        LevelConfig config = new LevelConfig(4, 96, 133);
        levelView.resetLevelLayout(totalEnds, config.getStartX(), config.getY(), config.getGap());
        animal.setX(300);
        animal.setY(733);
    }

}
