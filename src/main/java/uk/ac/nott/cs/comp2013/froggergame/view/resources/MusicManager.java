package uk.ac.nott.cs.comp2013.froggergame.view.resources;

import java.util.Objects;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicManager {
    private MediaPlayer mediaPlayer;
    private final String musicFilePath;

    public MusicManager(String musicFilePath) {
        this.musicFilePath = musicFilePath;
        initializeMediaPlayer();
    }

    private void initializeMediaPlayer() {
        try {
            String musicFile = Objects.requireNonNull(getClass().getResource(musicFilePath))
                    .toExternalForm();
            Media sound = new Media(musicFile);
            mediaPlayer = new MediaPlayer(sound);
        } catch (NullPointerException | IllegalArgumentException e) {
            System.err.println("Error loading music file: " + musicFilePath);
        }
    }

    public void playMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop music
            mediaPlayer.play();
            System.out.println("Music is now playing...");
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            System.out.println("Music has been stopped.");
        }
    }
}
