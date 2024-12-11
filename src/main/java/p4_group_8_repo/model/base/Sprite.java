package p4_group_8_repo.model.base;

/**
 * Represents a basic sprite in the game.
 * Provides position, velocity, and dimension properties for game objects.
 */
public class Sprite {
    private double x;
    private double y;
    private double vX;
    private double vY;
    private double width;
    private double height;

    /**
     * Gets the X-coordinate of the sprite.
     *
     * @return The X-coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the X-coordinate of the sprite.
     *
     * @param x The X-coordinate to set.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the Y-coordinate of the sprite.
     *
     * @return The Y-coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the Y-coordinate of the sprite.
     *
     * @param y The Y-coordinate to set.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets the center X-coordinate of the sprite.
     *
     * @return The center X-coordinate.
     */
    public double getCenterX() {
        return x + width / 2;
    }

    /**
     * Gets the center Y-coordinate of the sprite.
     *
     * @return The center Y-coordinate.
     */
    public double getCenterY() {
        return y + height / 2;
    }

    /**
     * Gets the velocity in the X direction.
     *
     * @return The X velocity.
     */
    public double getvX() {
        return vX;
    }

    /**
     * Sets the velocity in the X direction.
     *
     * @param vX The X velocity to set.
     */
    public void setvX(double vX) {
        this.vX = vX;
    }

    /**
     * Gets the velocity in the Y direction.
     *
     * @return The Y velocity.
     */
    public double getvY() {
        return vY;
    }

    /**
     * Sets the velocity in the Y direction.
     *
     * @param vY The Y velocity to set.
     */
    public void setvY(double vY) {
        this.vY = vY;
    }

    /**
     * Gets the width of the sprite.
     *
     * @return The width.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets the width of the sprite.
     *
     * @param width The width to set.
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Gets the height of the sprite.
     *
     * @return The height.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height of the sprite.
     *
     * @param height The height to set.
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Gets half the width of the sprite.
     *
     * @return Half the width.
     */
    public double getHalfWidth() {
        return width / 2;
    }

    /**
     * Gets half the height of the sprite.
     *
     * @return Half the height.
     */
    public double getHalfHeight() {
        return height / 2;
    }
}
