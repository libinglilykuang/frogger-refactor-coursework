package p4_group_8_repo;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import p4_group_8_repo.model.entities.Animal;
import p4_group_8_repo.model.entities.End;
import p4_group_8_repo.model.entities.Obstacle;
import p4_group_8_repo.model.entities.WetTurtle;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    private Animal animal;

    @BeforeEach
    void setUp() {
        animal = new Animal("file:src/test/resources/froggerUp.png");
    }

    @Test
    void testInitialization() {
        assertEquals(300, animal.getX(), "Initial X position should be 300.");
        assertEquals(733 + animal.movement, animal.getY(), "Initial Y position should be correctly offset.");
        assertNotNull(animal.getImage(), "Animal image should not be null.");
    }

    @Test
    void testKeyPressW() {
        animal.setY(500);

        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.W, false, false, false, false);
        animal.getOnKeyPressed().handle(keyEvent);

        assertEquals(500 - animal.movement, animal.getY(), "Animal should move up when W is pressed.");
        assertEquals(animal.imgW2, animal.getImage(), "Animal should change to jumping up image.");
    }

    @Test
    void testKeyPressA() {
        animal.setX(400);

        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.A, false, false, false, false);
        animal.getOnKeyPressed().handle(keyEvent);

        assertEquals(400 - animal.movementX, animal.getX(), "Animal should move left when A is pressed.");
        assertEquals(animal.imgA2, animal.getImage(), "Animal should change to jumping left image.");
    }

    @Test
    void testKeyReleaseW() {
        animal.setY(500);

        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_RELEASED, "", "", KeyCode.W, false, false, false, false);
        animal.getOnKeyReleased().handle(keyEvent);

        assertEquals(500 - animal.movement, animal.getY(), "Animal should move up when W is released.");
        assertEquals(animal.imgW1, animal.getImage(), "Animal should change to standing up image.");
    }
    @Test
    void testCarDeathLogic() {
        animal.carDeath = true;

        for (int i = 0; i < 5; i++) {
            animal.act(0);
        }

        assertFalse(animal.carDeath, "carDeath should be reset after handling.");
        assertFalse(animal.noMove, "noMove should be false after carDeath handling.");
        assertEquals(300, animal.getX(), "Animal's X position should reset after carDeath.");
        assertEquals(733 + animal.movement, animal.getY(), "Animal's Y position should reset after carDeath.");
    }


    @Test
    void testWaterDeathLogic() {
        animal.waterDeath = true;

        for (int i = 0; i < 5; i++) {
            animal.act(0);
        }

        assertFalse(animal.waterDeath, "waterDeath should be reset after handling.");
        assertFalse(animal.noMove, "noMove should be false after waterDeath handling.");
        assertEquals(300, animal.getX(), "Animal's X position should reset after waterDeath.");
        assertEquals(733 + animal.movement, animal.getY(), "Animal's Y position should reset after waterDeath.");
    }

    @Test
    void testIntersectingWithWetTurtle() {
        WetTurtle wetTurtle = new WetTurtle(300, 400, 0, 50, 50);
        wetTurtle.setSunk(true);

        animal.setX(300);
        animal.setY(400);
        animal.getWorld().add(wetTurtle);
        animal.act(0);

        assertTrue(animal.waterDeath, "Animal should enter waterDeath state when intersecting with a sunk WetTurtle.");
    }

    @Test
    void testIntersectingWithFloatingWetTurtle() {
        WetTurtle wetTurtle = new WetTurtle(300, 400, 0, 50, 50);
        wetTurtle.setSunk(false);

        animal.setX(300);
        animal.setY(400);
        animal.getWorld().add(wetTurtle);
        animal.act(0);

        assertFalse(animal.waterDeath, "Animal should not enter waterDeath state when intersecting with a floating WetTurtle.");
    }
    @Test
    void testIntersectingWithObstacle() {
        Obstacle obstacle = new Obstacle("file:src/test/resources/obstacle.png", 100, 200, 2, 50, 50);
        obstacle.setX(animal.getX());
        obstacle.setY(animal.getY());
        animal.getWorld().add(obstacle);

        animal.act(0);
        assertTrue(animal.carDeath, "Animal should trigger 'carDeath' when intersecting with an obstacle.");
    }

    @Test
    void testScoreUpdateLogic() {
        int initialScore = animal.getPoints();
        double initialHeight = animal.w;

        animal.setY(initialHeight - 50);
        animal.act(0);

        assertTrue(animal.getPoints() > initialScore, "Score should increase when Animal moves to a higher position.");
        assertEquals(initialHeight - 50, animal.w, "The highest position should update to the new Y position.");
    }

    @Test
    void testNoScoreUpdateForLowerPosition() {
        int initialScore = animal.getPoints();
        double initialHeight = animal.w;

        animal.setY(initialHeight + 50);
        animal.act(0);

        assertEquals(initialScore, animal.getPoints(), "Score should not change when Animal moves to a lower position.");
        assertEquals(initialHeight, animal.w, "The highest position should remain unchanged.");
    }
    @Test
    void testGameEnd() {
        End endPoint = new End(240, 82);
        animal.getWorld().add(endPoint);
        animal.setX(240);
        animal.setY(82);
        animal.act(0);
        assertTrue(animal.getStop(), "Game should end when the animal reaches all end points.");
    }
}
