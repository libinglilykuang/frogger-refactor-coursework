package p4_group_8_repo;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import p4_group_8_repo.model.base.Sprite;
import p4_group_8_repo.model.entities.Frog;
import p4_group_8_repo.model.entities.Car;
import p4_group_8_repo.view.ui.FroggerUI;

import static org.junit.jupiter.api.Assertions.*;

class FroggerUITest {

    private FroggerUI froggerUI;

    @BeforeEach
    void setUp() {
        Canvas canvas = new Canvas(800, 600);
        froggerUI = new FroggerUI(canvas);
        froggerUI.initStats();
    }

    @Test
    void testIncreaseLevel() {
        Label levelLabel = froggerUI.getLevelLabel();
        assertEquals("LEVEL: 1", levelLabel.getText(), "Initial level should be 1.");

        froggerUI.increaseLevel();
        assertEquals("LEVEL: 2", levelLabel.getText(), "Level should be increased to 2.");
    }

    @Test
    void testUpdateUIWithFrog() {
        Frog frog = new Frog();
        setSpriteAttributes(frog, 100, 200, 10, 5, 50, 50);

        froggerUI.updateUI(frog);

        String frogStats = froggerUI.getLevelLabel().getText();
        assertTrue(frogStats.contains("Frog: (100.0, 200.0)"), "Frog stats should include position.");
        assertTrue(frogStats.contains("vX: 10.0 - vY: 5.0"), "Frog stats should include velocity.");
    }

    @Test
    void testUpdateUIWithCar() {
        Car car = new Car();
        setSpriteAttributes(car, 300, 400, -15, 0, 80, 40);

        froggerUI.updateUI(car);

        String carStats = froggerUI.getLevelLabel().getText();
        assertTrue(carStats.contains("Car: (300.0, 400.0)"), "Car stats should include position.");
        assertTrue(carStats.contains("vX: -15.0 - vY: 0.0"), "Car stats should include velocity.");
    }

    @Test
    void testCreateGameOver() {
        froggerUI.createGameOver();

        Text gameOverText = (Text) froggerUI.getGameOverPane().getChildren().get(0);
        assertNotNull(gameOverText, "Game over text should be added to the pane.");
        assertEquals("GAME OVER!\nPress SPACE to restart...", gameOverText.getText(), "Game over text content should be correct.");
    }

    @Test
    void testRemoveGameOver() {
        froggerUI.createGameOver();

        froggerUI.removeGameOver();

        assertTrue(froggerUI.getGameOverPane().getChildren().isEmpty(), "Game over pane should be empty after removeGameOver.");
    }

    /**
     * 使用反射设置 Sprite 的私有属性。
     */
    private void setSpriteAttributes(Sprite sprite, double x, double y, double vX, double vY, double width, double height) {
        setPrivateField(sprite, "x", x);
        setPrivateField(sprite, "y", y);
        setPrivateField(sprite, "vX", vX);
        setPrivateField(sprite, "vY", vY);
        setPrivateField(sprite, "width", width);
        setPrivateField(sprite, "height", height);
    }

    /**
     * 使用反射设置对象的私有字段值。
     */
    private void setPrivateField(Object object, String fieldName, Object value) {
        try {
            var field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
