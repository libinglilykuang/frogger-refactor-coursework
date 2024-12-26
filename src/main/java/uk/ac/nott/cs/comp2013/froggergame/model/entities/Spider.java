package uk.ac.nott.cs.comp2013.froggergame.model.entities;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;

public class Spider extends Actor {
    private final Log parentLog;

    public Spider(Log parentLog, String imagePath) {
        super((int) parentLog.getX(), (int) parentLog.getY(), 0);
        this.parentLog = parentLog;
        setImage(new Image(imagePath, 30, 30, true, true));
    }

    public Log getParentLog() {
        return parentLog;
    }

    public Pane getParentPane() {
        if (this.getParent() instanceof Pane pane) {
            return pane;
        }
        return null;
    }

    public void updatePosition() {
        setX(parentLog.getX() + 50);
        setY(parentLog.getY()+2);
    }

    @Override
    public void act(long now) {

    }

    @Override
    public void act(double elapsedTime) {
    }
}
