package p4_group_8_repo.model;

import p4_group_8_repo.model.base.ImageLoader;
import p4_group_8_repo.model.entities.Log;
import p4_group_8_repo.model.entities.Obstacle;
import p4_group_8_repo.model.entities.Turtle;
import javafx.scene.Node;
import java.util.ArrayList;
import java.util.List;

public class GameObjectFactory {

    /**
     * Creates a single Log object.
     */
    public Log createLog(String imageName, int width, int height, int xpos, int ypos, int yLevel, double speed) {
        String imageUrl = loadImage(imageName, width, height);
        return new Log(imageUrl, xpos, ypos, yLevel, speed);
    }

    /**
     * Creates a single Turtle object.
     */
    public Turtle createTurtle(int xpos, int ypos, int speed, int width, int height) {
        return new Turtle(xpos, ypos, speed, width, height);
    }

    /**
     * Creates a single Obstacle object.
     */
    public Obstacle createObstacle(String imageName, int width, int height, int xpos, int ypos, int speed) {
        String imageUrl = loadImage(imageName, width, height);
        return new Obstacle(imageUrl, xpos, ypos, speed, width, height);
    }

    /**
     * Creates multiple Log objects.
     */
    public List<Log> createLogs(String imageName, int width, int height, int[][] positions, int yLevel, double speed) {
        List<Log> logs = new ArrayList<>();
        for (int[] position : positions) {
            logs.add(createLog(imageName, width, height, position[0], position[1], yLevel, speed));
        }
        return logs;
    }

    /**
     * Creates multiple Obstacle objects.
     */
    public List<Obstacle> createObstacles(String imageName, int width, int height, int[][] positions, int speed) {
        List<Obstacle> obstacles = new ArrayList<>();
        for (int[] position : positions) {
            obstacles.add(createObstacle(imageName, width, height, position[0], position[1], speed));
        }
        return obstacles;
    }

    /**
     * Creates multiple Turtle objects.
     */
    public List<Turtle> createTurtles(int[][] positions, int speed, int width, int height) {
        List<Turtle> turtles = new ArrayList<>();
        for (int[] position : positions) {
            turtles.add(createTurtle(position[0], position[1], speed, width, height));
        }
        return turtles;
    }

    /**
     * Loads an image and returns its URL.
     */
    private String loadImage(String imageName, int width, int height) {
        return ImageLoader.loadImage(imageName, width, height).getUrl();
    }
}
