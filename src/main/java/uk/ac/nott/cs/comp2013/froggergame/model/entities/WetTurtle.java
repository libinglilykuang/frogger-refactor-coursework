package uk.ac.nott.cs.comp2013.froggergame.model.entities;

public class WetTurtle extends AbstractTurtle {
	private boolean sunk = false;

	public WetTurtle(int xpos, int ypos, int speed, int width, int height) {
		super(xpos, ypos, speed, width, height, new String[]{
				"TurtleAnimation1.png",
				"TurtleAnimation2Wet.png",
				"TurtleAnimation3Wet.png",
				"TurtleAnimation4Wet.png"
		});
	}

	public boolean isSunk() {
		return sunk;
	}

	@Override
    public void updateStateBasedOnFrame(int frame) {
		sunk = (frame == animationFrames.length - 1);
	}
}
