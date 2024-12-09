package p4_group_8_repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogTest {

    private Log log;

    @BeforeEach
    void setUp() {
        log = new Log("file:src/test/resources/log.png", 100, 300, 400, 2.0);
    }

    @Test
    void testInitialization() {
        assertEquals(300, log.getX(), "Initial X position should be 300.");
        assertEquals(400, log.getY(), "Initial Y position should be 400.");
        assertNotNull(log.getImage(), "Log image should not be null.");
    }

    @Test
    void testActMethodPositiveSpeed() {
        log.setX(500);
        log.act(0);
        assertEquals(502, log.getX(), "Log should move right when speed is positive.");

        log.setX(601);
        log.act(0);
        assertEquals(-180, log.getX(), "Log should reset to the left when moving out of bounds on the right.");
    }

    @Test
    void testActMethodNegativeSpeed() {
        log = new Log("file:src/test/resources/log.png", 100, 300, 400, -2.0);

        log.setX(0);
        log.act(0);
        assertEquals(-2, log.getX(), "Log should move left when speed is negative.");

        log.setX(-301);
        log.act(0);
        assertEquals(700, log.getX(), "Log should reset to the right when moving out of bounds on the left.");
    }

    @Test
    void testGetLeft() {
        log = new Log("file:src/test/resources/log.png", 100, 300, 400, -2.0);
        assertTrue(log.getLeft(), "getLeft should return true for negative speed.");

        log = new Log("file:src/test/resources/log.png", 100, 300, 400, 2.0);
        assertFalse(log.getLeft(), "getLeft should return false for positive speed.");
    }
}
