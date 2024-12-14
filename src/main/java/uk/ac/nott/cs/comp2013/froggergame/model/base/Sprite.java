package uk.ac.nott.cs.comp2013.froggergame.model.base;

/**
 * Represents a basic sprite in the game.
 * Provides position, velocity, and dimension properties for game objects.
 */
public abstract class Sprite {
    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;

    /**
     * Constructs a new Sprite with specified position and dimensions.
     *
     * @param x      The initial X-coordinate.
     * @param y      The initial Y-coordinate.
     * @param width  The width of the sprite.
     * @param height The height of the sprite.
     */
    protected Sprite(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Getters and Setters for position
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    // Getters and Setters for velocity
    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    // Getters and Setters for dimensions
    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    // Derived properties
    public double getCenterX() {
        return x + width / 2;
    }

    public double getCenterY() {
        return y + height / 2;
    }

    public double getHalfWidth() {
        return width / 2;
    }

    public double getHalfHeight() {
        return height / 2;
    }

    /**
     * Updates the sprite's position based on its velocity.
     */
    public void updatePosition() {
        x += velocityX;
        y += velocityY;
    }

    /**
     * Checks if this sprite intersects with another sprite (AABB collision detection).
     *
     * @param other The other sprite to check for collision.
     * @return True if the two sprites intersect, false otherwise.
     */
    public boolean intersects(Sprite other) {
        return x < other.x + other.width &&
                x + width > other.x &&
                y < other.y + other.height &&
                y + height > other.y;
    }

    /**
     * Stops the sprite's movement by resetting its velocity to zero.
     */
    public void stop() {
        velocityX = 0;
        velocityY = 0;
    }

    /**
     * Abstract method to define sprite-specific behavior.
     *
     * @param now The current timestamp of the game loop.
     */
    public abstract void act(long now);
}
