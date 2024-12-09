package p4_group_8_repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurtleTest {

    private Turtle turtle;

    @BeforeEach
    void setUp() {
        turtle = new Turtle(100, 200, 2, 50, 50);
    }

    @Test
    void testInitialization() {
        assertEquals(100, turtle.getX(), "Initial X position should be 100.");
        assertEquals(200, turtle.getY(), "Initial Y position should be 200.");
        assertEquals(2, turtle.speed, "Initial speed should be 2.");
        assertNotNull(turtle.getImage(), "Turtle image should not be null.");
    }

    @Test
    void testAnimationFrames() {
        long time1 = 0;
        long time2 = 900_000_000;
        long time3 = 1_800_000_000;

        turtle.act(time1);
        assertSame(turtle.turtle2, turtle.getImage(), "Turtle should display turtle2 image at time1.");

        turtle.act(time2);
        assertSame(turtle.turtle1, turtle.getImage(), "Turtle should display turtle1 image at time2.");

        turtle.act(time3);
        assertSame(turtle.turtle3, turtle.getImage(), "Turtle should display turtle3 image at time3.");
    }

    @Test
    void testMovementLogic() {
        turtle.setX(500);
        turtle.act(0);
        assertEquals(502, turtle.getX(), "Turtle should move right when speed is positive.");

        turtle.setX(601);
        turtle.act(0);
        assertEquals(-200, turtle.getX(), "Turtle should reset to the left when moving out of bounds on the right.");

        turtle = new Turtle(100, 200, -2, 50, 50);
        turtle.setX(0);
        turtle.act(0);
        assertEquals(-2, turtle.getX(), "Turtle should move left when speed is negative.");

        turtle.setX(-76);
        turtle.act(0);
        assertEquals(600, turtle.getX(), "Turtle should reset to the right when moving out of bounds on the left.");
    }
}
