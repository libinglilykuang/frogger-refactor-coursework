package uk.ac.nott.cs.comp2013.froggergame.view.resource;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ImageManager {

    private static final int IMAGE_WIDTH = 40;
    private static final int IMAGE_HEIGHT = 40;
    private static final String JUMP_SUFFIX = "Jump";

    private final Map<String, Image> images = new HashMap<>();

    public ImageManager() {
        preloadImages();
    }

    private void preloadImages() {
        loadDirectionalImages("frogger", "");
        loadDirectionalImages("frogger", JUMP_SUFFIX);
    }

    private void loadDirectionalImages(String baseName, String suffix) {
        for (String direction : new String[]{"Up", "Left", "Down", "Right"}) {
            String key = baseName + direction + suffix;
            String filePath = key + ".png";
            loadImage(key, filePath);
        }
    }

    private void loadImage(String key, String filePath) {
        images.put(key, ImageLoader.loadImage(filePath, IMAGE_WIDTH, IMAGE_HEIGHT));
    }

    public Image getImageForKey(KeyCode key, boolean jumping) {
        String direction = switch (key) {
            case W -> "Up";
            case A -> "Left";
            case S -> "Down";
            case D -> "Right";
            default -> null;
        };
        if (direction == null) {
            return null;
        }
        String imageKey = "frogger" + direction + (jumping ? JUMP_SUFFIX : "");
        return images.get(imageKey);
    }

    public Image getDeathImage(String type, int frame) {
        Objects.requireNonNull(type, "Type cannot be null");
        return ImageLoader.loadImage(type + frame + ".png", IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    public Image getDefaultImage() {
        return images.get("froggerUp");
    }
}
