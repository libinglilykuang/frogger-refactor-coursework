package uk.ac.nott.cs.comp2013.froggergame.view.managers;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Sprite;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.LevelManager;

public class FroggerUI extends UI {
    private final LevelManager levelManager;

    public FroggerUI(Canvas canvas, LevelManager levelManager) {
        super(canvas);
        this.levelManager = levelManager;
    }

    @Override
    public void setupComponents() {
    }

    @Override
    public void render(Group root) {
        Pane gamePane = new Pane();
        gamePane.getChildren().add(getCanvas());
        root.getChildren().add(gamePane);
    }

    @Override
    public void refreshUI(Sprite... sprites) {

    }

    @Override
    public void initializeStatistics() {

    }

    public void updateLevel(int newLevel) {
        levelManager.setLevel(newLevel);
    }

    public void resetLevel() {
        levelManager.increaseLevel();
    }

    public void handleTargetReached() {
        levelManager.onTargetReached();
    }
}
