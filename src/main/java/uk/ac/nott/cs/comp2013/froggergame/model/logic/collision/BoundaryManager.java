package uk.ac.nott.cs.comp2013.froggergame.model.logic.collision;

public class BoundaryManager {
    private final int screenLeftBound;
    private final int screenRightBound;
    private final int resetLeftPosition;
    private final int resetRightPosition;

    public BoundaryManager(int screenLeftBound, int screenRightBound, int resetLeftPosition, int resetRightPosition) {
        this.screenLeftBound = screenLeftBound;
        this.screenRightBound = screenRightBound;
        this.resetLeftPosition = resetLeftPosition;
        this.resetRightPosition = resetRightPosition;
    }

    public boolean isOutOfBounds(double posX, double speed) {
        return (posX > screenRightBound && speed > 0) || (posX < screenLeftBound && speed < 0);
    }

    public double resetPositionBasedOnSpeed(double speed) {
        if (speed > 0) {
            return resetRightPosition;
        } else if (speed < 0) {
            return resetLeftPosition;
        }
        return 0;
    }

}

