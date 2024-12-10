package p4_group_8_repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import p4_group_8_repo.model.entities.WetTurtle;

import static org.junit.jupiter.api.Assertions.*;

class WetTurtleTest {

    private WetTurtle wetTurtle;

    @BeforeEach
    void setUp() {
        wetTurtle = new WetTurtle(100, 200, 2, 40, 40);
    }

    @Test
    void testInitialPosition() {
        assertEquals(100, wetTurtle.getX(), "Initial X position should be 100.");
        assertEquals(200, wetTurtle.getY(), "Initial Y position should be 200.");
    }

    @Test
    void testImageSwitchingAndSunkState() {
        // Test `act` logic at different time intervals
        long time = 0;

        // Test for t2
        time = 0;
        wetTurtle.act(time);
        assertEquals(false, wetTurtle.isSunk(), "WetTurtle should not be sunk at time t2.");

        // Test for t1
        time = 900_000_000L;
        wetTurtle.act(time);
        assertEquals(false, wetTurtle.isSunk(), "WetTurtle should not be sunk at time t1.");

        // Test for t3
        time = 1_800_000_000L;
        wetTurtle.act(time);
        assertEquals(false, wetTurtle.isSunk(), "WetTurtle should not be sunk at time t3.");

        // Test for t4
        time = 2_700_000_000L;
        wetTurtle.act(time);
        assertEquals(true, wetTurtle.isSunk(), "WetTurtle should be sunk at time t4.");
    }

    @Test
    void testMovement() {
        // Test initial movement to the right
        double initialX = wetTurtle.getX();
        wetTurtle.act(0);
        assertEquals(initialX + wetTurtle.speed, wetTurtle.getX(), "WetTurtle should move to the right by `speed` units.");

        // Test movement wrapping at right boundary
        wetTurtle.setX(601);
        wetTurtle.act(0);
        assertEquals(-200, wetTurtle.getX(), "WetTurtle should wrap to -200 when moving out of the right boundary.");

        // Test movement wrapping at left boundary
        wetTurtle.speed = -2; // Move to the left
        wetTurtle.setX(-76);
        wetTurtle.act(0);
        assertEquals(600, wetTurtle.getX(), "WetTurtle should wrap to 600 when moving out of the left boundary.");
    }

    @Test
    void testSunkState() {
        // Simulate time to verify sunk state
        long time = 2_700_000_000L; // t4
        wetTurtle.act(time);
        assertTrue(wetTurtle.isSunk(), "WetTurtle should be sunk at time corresponding to t4.");

        time = 0; // t2
        wetTurtle.act(time);
        assertFalse(wetTurtle.isSunk(), "WetTurtle should not be sunk at time corresponding to t2.");
    }

    @Test
    void testImageLoading() {
        // Check that images are loaded correctly
        assertNotNull(wetTurtle.t1, "Image t1 should be loaded.");
        assertNotNull(wetTurtle.t2, "Image t2 should be loaded.");
        assertNotNull(wetTurtle.t3, "Image t3 should be loaded.");
        assertNotNull(wetTurtle.t4, "Image t4 should be loaded.");
    }
}
