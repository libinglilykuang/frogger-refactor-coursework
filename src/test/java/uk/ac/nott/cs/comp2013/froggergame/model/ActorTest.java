package uk.ac.nott.cs.comp2013.froggergame.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;

class ActorTest {
    private Actor actor;

    @BeforeEach
    void setUp() {
        actor = new Actor(10, 20, 5.0) {
            @Override
            public void act(long now) {
            }

            @Override
            public void act(double elapsedTime) {
            }
        };
    }

    @Test
    void testInitialization() {
        assertEquals(10, actor.getX());
        assertEquals(20, actor.getY());
        assertEquals(5.0, actor.getSpeed());
    }

    @Test
    void testMove() {
        actor.move(5, -10);
        assertEquals(15, actor.getX());
        assertEquals(10, actor.getY());
    }

    @Test
    void testSetSpeedFactor() {
        actor.setSpeedFactor(2.0);
        assertEquals(10.0, actor.getSpeed());
    }

    @Test
    void testConfigureBoundsAndReset() {
        actor.configureBounds(0, 100, 0, 0);
        actor.setX(150);
        actor.resetPositionIfOutOfBounds(1.0);
        assertEquals(0, actor.getX());
    }

    @Test
    void testGetWorldWithoutSettingThrowsException() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, actor::getWorld);
        assertEquals("Actor is not attached to any World instance.", exception.getMessage());
    }
}
