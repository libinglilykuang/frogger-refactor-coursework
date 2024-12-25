package uk.ac.nott.cs.comp2013.froggergame.view.scenes;

import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.model.data.LevelConfig;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Level;
import uk.ac.nott.cs.comp2013.froggergame.view.components.TimeBar;
import uk.ac.nott.cs.comp2013.froggergame.view.resource.MusicManager;

public class MyStage extends World {
	private final MusicManager musicManager = new MusicManager("/Snake_Charmer.mp3");
	private final Level level = new Level(this);
	private TimeBar timeBar;
	public MyStage() {
		this.initializeGame();
	}

	private void initializeGame() {
		this.level.updateLevelDisplay(1);
		LevelConfig levelConfig = new LevelConfig(1, 100, 50, 120,2);
		this.level.resetLevelLayout(levelConfig);

		this.timeBar = new TimeBar(this);
		this.timeBar.start();
	}

	public void playMusic() {
		this.musicManager.playMusic();
	}

	public void stopMusic() {
		this.musicManager.stopMusic();
	}
	public TimeBar getTimeBar() {
		return timeBar;
	}

	public void stopTimeBar() {
		if (timeBar != null) {
			timeBar.stop();
		}
	}

	public void act(long now) {
	}

	public void act(double elapsedTime) {
        for (Actor actor : this.getObjects(Actor.class)) {
            actor.act(elapsedTime);
        }

	}
}
