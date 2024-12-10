package p4_group_8_repo;

import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import p4_group_8_repo.model.entities.End;

import static org.junit.jupiter.api.Assertions.*;

class EndTest {

    private End endInstance;

    @BeforeEach
    void setUp() {
        endInstance = new End(100, 200);
    }

    @Test
    void testInitialization() {
        assertEquals(100, endInstance.getX(), "X position should be initialized to 100.");
        assertEquals(200, endInstance.getY(), "Y position should be initialized to 200.");

        Image image = endInstance.getImage();
        assertNotNull(image, "Initial image should be loaded.");
        assertEquals(60, image.getWidth(), "Initial image width should be 60.");
        assertEquals(60, image.getHeight(), "Initial image height should be 60.");

        assertFalse(endInstance.isActivated(), "End should not be activated initially.");
    }

    @Test
    void testSetEnd() {
        endInstance.setEnd();

        Image image = endInstance.getImage();
        assertNotNull(image, "Image should be updated after setEnd is called.");
        assertEquals(70, image.getWidth(), "Updated image width should be 70.");
        assertEquals(70, image.getHeight(), "Updated image height should be 70.");

        assertTrue(endInstance.isActivated(), "End should be activated after setEnd is called.");
    }

    @Test
    void testActivationStatus() {
        assertFalse(endInstance.isActivated(), "End should not be activated initially.");
        endInstance.setEnd();
        assertTrue(endInstance.isActivated(), "End should be activated after setEnd.");
    }
}
