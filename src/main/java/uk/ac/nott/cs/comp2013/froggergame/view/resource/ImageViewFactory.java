package uk.ac.nott.cs.comp2013.froggergame.view.resource;

import javafx.scene.image.Image;

public class ImageViewFactory {
    public static ConfiguredImageView createConfiguredImageView(String filePath, double width, double height) {
        Image image = ImageLoader.loadImage(filePath, width, height);
        return new ConfiguredImageView(image, width, height);
    }

    public static class ConfiguredImageView {
        private final Image image;
        private final double width;
        private final double height;

        public ConfiguredImageView(Image image, double width, double height) {
            this.image = image;
            this.width = width;
            this.height = height;
        }

        public Image getImage() {
            return image;
        }

        public double getWidth() {
            return width;
        }

        public double getHeight() {
            return height;
        }
    }
}
