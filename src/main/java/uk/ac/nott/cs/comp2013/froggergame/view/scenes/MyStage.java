package uk.ac.nott.cs.comp2013.froggergame.view.scenes;

import uk.ac.nott.cs.comp2013.froggergame.view.resource.MusicManager;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;

public class MyStage extends World {
	private static final String MUSIC_FILE_PATH = "/Snake_Charmer.mp3";
	private final MusicManager musicManager;

	public MyStage() {
		musicManager = new MusicManager(MUSIC_FILE_PATH);
	}

	public void playMusic() {
		musicManager.playMusic();
	}

	public void stopMusic() {
		musicManager.stopMusic();
	}

	@Override
	public void act(long now) {
	}

	@Override
	public void act(double elapsedTime) {
		// Stage logic can be added here
		for (Actor actor : getObjects(Actor.class)) {
			actor.act(elapsedTime);
		}
	}

}
