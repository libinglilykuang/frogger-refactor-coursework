package uk.ac.nott.cs.comp2013.froggergame.view.managers;

import uk.ac.nott.cs.comp2013.froggergame.model.base.Sprite;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;

/**
 * Abstract base class for UI components in the game.
 * Provides a structure for initializing, rendering, and updating UI elements.
 */
public abstract class UI {

    private final Canvas canvas;

    /**
     * Constructor to initialize the UI with a canvas.
     *
     * @param canvas The canvas used by the UI for rendering elements.
     */
    protected UI(Canvas canvas) {
        if (canvas == null) {
            throw new IllegalArgumentException("Canvas cannot be null.");
        }
        this.canvas = canvas;
    }

    /**
     * Gets the canvas associated with this UI.
     *
     * @return The canvas used by this UI.
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Abstract method to set up UI components.
     * Subclasses must implement this to define how UI elements are initialized.
     */
    public abstract void setupComponents();

    /**
     * Abstract method to render the canvas and other UI components in the scene graph.
     * Subclasses must implement this to define how the UI is added to the root group.
     *
     * @param root The root group where the components will be rendered.
     */
    public abstract void render(Group root);

    /**
     * Abstract method to refresh the UI elements based on the current game state.
     * Subclasses must implement this to define how the UI reflects changes in the sprites.
     *
     * @param sprites The array of sprites whose state needs to be reflected in the UI.
     */
    public abstract void refreshUI(Sprite... sprites);

    /**
     * Abstract method to initialize statistics-related UI components.
     * Subclasses must implement this to define how statistics are presented in the UI.
     */
    public abstract void initializeStatistics();

    /**
     * Clears the canvas by resetting its graphical context.
     * This is a helper method for subclasses to ensure the canvas is cleared before rendering.
     */
    protected void clearCanvas() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Lifecycle hook to perform actions when the UI starts.
     * Subclasses can optionally override this to define specific start behavior.
     */
    public void onStart() {
        // Optional start logic for subclasses
    }

    /**
     * Lifecycle hook to perform actions when the UI stops.
     * Subclasses can optionally override this to define specific stop behavior.
     */
    public void onStop() {
        // Optional stop logic for subclasses
    }
}
