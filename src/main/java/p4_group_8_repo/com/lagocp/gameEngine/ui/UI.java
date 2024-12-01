package p4_group_8_repo.com.lagocp.gameEngine.ui;


import p4_group_8_repo.com.lagocp.gameEngine.sprite.Sprite;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;

/**
 * Abstract class for UI components in the game. Provides a canvas and
 * structure for initializing, placing, and updating UI elements.
 */
public abstract class UI {
    private Canvas canvas;

    /**
     * Constructor to initialize the UI with a canvas.
     *
     * @param canvas The canvas for the UI to use.
     */
    public UI(Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Gets the canvas associated with this UI.
     *
     * @return The canvas.
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Method to initialize UI components.
     */
    public abstract void create();

    /**
     * Method to place the canvas and other UI components in the scene graph.
     *
     * @param root The root group to place the components in.
     */
    public abstract void placeCanvas(Group root);

    /**
     * Method to update the UI elements with the current game state.
     *
     * @param sprites The sprites whose state needs to be reflected in the UI.
     */
    public abstract void updateUI(Sprite... sprites);

    /**
     * Method to initialize statistics-related UI components.
     */
    public abstract void initStats();
}
