package uk.ac.nott.cs.comp2013.froggergame.model.logic;

import uk.ac.nott.cs.comp2013.froggergame.view.resource.ImageLoader;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.*;
import uk.ac.nott.cs.comp2013.froggergame.view.managers.LevelView;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class GameObjectFactory {
    private static final String FROG_IMAGE = "froggerUp.png";

    public Animal createAnimal() {
        return new Animal(ImageLoader.loadImage(FROG_IMAGE, 40, 40).getUrl());
    }

    public LevelManager createLevelManager(Animal animal, LevelView levelView, int maxLevel) {
        return new LevelManager(animal, levelView, maxLevel);
    }

    public Log createLog(String imageName, int width, int height, int xpos, int ypos, int yLevel, double speed) {
        String imageUrl = ImageLoader.loadImage(imageName, width, height).getUrl();
        return new Log(imageUrl, xpos, ypos, yLevel, speed);
    }

    public Obstacle createObstacle(String imageName, int width, int height, int xpos, int ypos, int speed) {
        String imageUrl = ImageLoader.loadImage(imageName, width, height).getUrl();
        return new Obstacle(imageUrl, xpos, ypos, speed, width, height);
    }

    public AbstractTurtle createTurtle(int xpos, int ypos, int speed, int width, int height, boolean isWet) {
        if (isWet) {
            return new WetTurtle(xpos, ypos, speed, width, height);
        } else {
            return new Turtle(xpos, ypos, speed, width, height);
        }
    }

    public <T> void setupGameObjects(MyStage background, Object[][] data, Function<Object[], T> mapper, BiConsumer<MyStage, T> setupAction) {
        for (Object[] rawData : data) {
            T gameObject = mapper.apply(rawData);
            setupAction.accept(background, gameObject);
        }
    }

    public void setupLogs(MyStage background, Object[][] logData) {
        setupGameObjects(
                background,
                logData,
                rawData -> createLog(
                        (String) rawData[0], (int) rawData[1], (int) rawData[2], (int) rawData[3],
                        (int) rawData[4], (int) rawData[5], (double) rawData[6]
                ),
                MyStage::add
        );
    }

    public void setupObstacles(MyStage background, Object[][] obstacleData) {
        setupGameObjects(
                background,
                obstacleData,
                rawData -> createObstacle(
                        (String) rawData[0], (int) rawData[1], (int) rawData[2], (int) rawData[3],
                        (int) rawData[4], (int) rawData[5]
                ),
                MyStage::add
        );
    }

    public void setupTurtles(MyStage background, Object[][] turtleData) {
        setupGameObjects(
                background,
                turtleData,
                rawData -> createTurtle(
                        (int) rawData[0], (int) rawData[1], (int) rawData[2], (int) rawData[3],
                        (int) rawData[4], (boolean) rawData[5]
                ),
                MyStage::add
        );
    }
}
