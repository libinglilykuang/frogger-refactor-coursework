package p4_group_8_repo;

import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import p4_group_8_repo.view.ui.digit;

import static org.junit.jupiter.api.Assertions.*;

class digitTest {

    private digit digitInstance;

    @BeforeEach
    void setUp() {
        digitInstance = new digit(1, 50, 100, 200);
    }

    @Test
    void testImageLoading() {
        Image image = digitInstance.getImage();
        assertNotNull(image, "Image should be loaded successfully for the digit.");
    }

    @Test
    void testImageDimensions() {
        Image image = digitInstance.getImage();
        assertEquals(50, image.getWidth(), "Image width should match the specified dimension.");
        assertEquals(50, image.getHeight(), "Image height should match the specified dimension.");
    }

    @Test
    void testPositionInitialization() {
        assertEquals(100, digitInstance.getX(), "X position should be initialized correctly.");
        assertEquals(200, digitInstance.getY(), "Y position should be initialized correctly.");
    }

    @Test
    void testInvalidDigit() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new digit(99, 50, 100, 200);
        });
        assertTrue(exception.getMessage().contains("/p4_group_8_repo/99.png"), "Exception should mention the missing resource.");
    }
}
