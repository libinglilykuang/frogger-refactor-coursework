package uk.ac.nott.cs.comp2013.froggergame.controller;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MainMenuControllerTest extends ApplicationTest {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        // Create a simple JavaFX scene to test
        Label label = new Label("JavaFX Test");
        Scene scene = new Scene(label, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Test Stage");
        stage.show();
    }

    @Test
    void testStageInitialized() {
        // Verify that the stage and scene are not null
        assertNotNull(primaryStage);
        assertNotNull(primaryStage.getScene());
    }
}
