package p4_group_8_repo.view.scene;

import java.util.Objects;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MyStage extends World {
	MediaPlayer mediaPlayer;
	private static final String MUSIC_FILE_PATH = "/Snake_Charmer.mp3";

	public MyStage() {
		
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
		if (mediaPlayer == null) {
			initializeMediaPlayer();
		}
		if (mediaPlayer != null) {
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop music
			mediaPlayer.play();
		}
	}

	public void stopMusic() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
		}
	}

	private void initializeMediaPlayer() {
		try {
			String musicFile = Objects.requireNonNull(getClass().getResource(MUSIC_FILE_PATH))
					.toExternalForm();
			Media sound = new Media(musicFile);
			mediaPlayer = new MediaPlayer(sound);
		} catch (NullPointerException | IllegalArgumentException e) {
			System.err.println("Error loading music file: " + MUSIC_FILE_PATH);
		}
	}

	@Override
	public void act(long now) {
		// Stage logic can be added here
	}

}
