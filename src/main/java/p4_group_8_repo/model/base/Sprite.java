package p4_group_8_repo.model.base;

public class Sprite {
    private double x, y, vX, vY, width, height;

    public double getX() { return x; }
    public double getY() { return y; }
    public double getCenterX() { return x + width / 2; }
    public double getCenterY() { return y + height / 2; }
    public double getvX() { return vX; }
    public double getvY() { return vY; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getHalfWidth() { return width / 2; }
    public double getHalfHeight() { return height / 2; }
}
