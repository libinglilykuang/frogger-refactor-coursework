package p4_group_8_repo;

import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyStageTest {

    private MyStage myStage;

    @BeforeEach
    void setUp() {
        myStage = new MyStage();
    }

    @Test
    void testPlayMusic() {
        myStage.playMusic();

        MediaPlayer mediaPlayer = myStage.mediaPlayer;
        assertNotNull(mediaPlayer, "MediaPlayer should be initialized.");
        assertEquals(MediaPlayer.Status.PLAYING, mediaPlayer.getStatus(), "MediaPlayer should be playing.");
        assertEquals(MediaPlayer.INDEFINITE, mediaPlayer.getCycleCount(), "MediaPlayer should be set to loop indefinitely.");
    }

    @Test
    void testStopMusic() {
        myStage.playMusic();
        myStage.stopMusic();

        MediaPlayer mediaPlayer = myStage.mediaPlayer;
        assertNotNull(mediaPlayer, "MediaPlayer should be initialized.");
        assertEquals(MediaPlayer.Status.STOPPED, mediaPlayer.getStatus(), "MediaPlayer should be stopped.");
    }

    @Test
    void testMediaFileExists() {
        String musicFile = "/Snake_Charmer.mp3";
        assertNotNull(getClass().getResource(musicFile), "Music file should exist in the resources directory.");
    }
}
