package uk.ac.nott.cs.comp2013.froggergame.model.logic;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uk.ac.nott.cs.comp2013.froggergame.model.base.ImageLoader;
import java.util.HashMap;
import java.util.Map;

public class ImageManager {
    private final Map<String, Image> images = new HashMap<>();

    public ImageManager() {
        preloadImages();
    }

    private void preloadImages() {
        loadImage("froggerUp", "froggerUp.png");
        loadImage("froggerLeft", "froggerLeft.png");
        loadImage("froggerDown", "froggerDown.png");
        loadImage("froggerRight", "froggerRight.png");
        loadImage("froggerUpJump", "froggerUpJump.png");
        loadImage("froggerLeftJump", "froggerLeftJump.png");
        loadImage("froggerDownJump", "froggerDownJump.png");
        loadImage("froggerRightJump", "froggerRightJump.png");
    }

    private void loadImage(String key, String filePath) {
        images.put(key, ImageLoader.loadImage(filePath, 40, 40));
    }

    public Image getImageForKey(KeyCode key, boolean jumping) {
        return switch (key) {
            case W -> jumping ? images.get("froggerUpJump") : images.get("froggerUp");
            case A -> jumping ? images.get("froggerLeftJump") : images.get("froggerLeft");
            case S -> jumping ? images.get("froggerDownJump") : images.get("froggerDown");
            case D -> jumping ? images.get("froggerRightJump") : images.get("froggerRight");
            default -> null;
        };
    }

    public Image getDeathImage(String type, int frame) {
        return ImageLoader.loadImage(type + frame + ".png", 40, 40);
    }

    public Image getDefaultImage() {
        return images.get("froggerUp");
    }
}
