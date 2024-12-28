package uk.ac.nott.cs.comp2013.froggergame;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;

public abstract class JavaFXTestBase {
    private static boolean toolkitInitialized = false;

    @BeforeAll
    static void initToolkit() {
        if (!toolkitInitialized) {
            Platform.startup(() -> {});
            toolkitInitialized = true;
        }
    }
}
