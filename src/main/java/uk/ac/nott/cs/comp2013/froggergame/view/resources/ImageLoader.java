package uk.ac.nott.cs.comp2013.froggergame.view.resources;

import javafx.scene.image.Image;
import java.util.Objects;

/**
 * Utility class for loading images with a predefined base path.
 */
public class ImageLoader {
    private static final String BASE_PATH = "/images/";

    public static Image loadImage(String filePath, double width, double height) {
        Objects.requireNonNull(filePath, "Image file path cannot be null.");
        String fullPath = BASE_PATH + filePath;
        try {
            return new Image(Objects.requireNonNull(ImageLoader.class.getResource(fullPath)).toExternalForm(), width, height, true, true);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Image resource not found: " + fullPath, e);
        }
    }
}
