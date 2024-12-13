package p4_group_8_repo.view.scene;

import p4_group_8_repo.controller.MusicManager;

public class MyStage extends World {
	private static final String MUSIC_FILE_PATH = "/Snake_Charmer.mp3";
	private final MusicManager musicManager;

	public MyStage() {
		musicManager = new MusicManager(MUSIC_FILE_PATH);
//		mediaPlayer.play();
//		mediaPlayer.setOnEndOfMedia(new Runnable() {
//
//			@Override
//			public void run() {
//				mediaPlayer.seek(Duration.ZERO);
//				
//			}
//			
//		});
//		mediaPlayer.play();
	}

	public void playMusic() {
		musicManager.playMusic();
	}

	public void stopMusic() {
		musicManager.stopMusic();
	}

	@Override
	public void act(long now) {
		// Stage logic can be added here
	}

}
