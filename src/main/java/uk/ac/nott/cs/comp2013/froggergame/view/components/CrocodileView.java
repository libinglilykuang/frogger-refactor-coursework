package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.scene.image.Image;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.Crocodile;

public class CrocodileView {
    public static void initializeImage(Crocodile crocodile, String imagePath) {
        crocodile.setImage(new Image(imagePath, 80, 80, true, true));
    }
}
