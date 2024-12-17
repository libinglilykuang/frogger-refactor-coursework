package uk.ac.nott.cs.comp2013.froggergame.model.logic;

import uk.ac.nott.cs.comp2013.froggergame.view.resources.ImageLoader;
import uk.ac.nott.cs.comp2013.froggergame.model.entities.*;
import uk.ac.nott.cs.comp2013.froggergame.view.managers.LevelView;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;

public class GameObjectFactory {
    public Animal createAnimal() {
        return new Animal(ImageLoader.loadImage("froggerUp.png", 40, 40).getUrl());
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

    public void setupTurtles(MyStage background, Object[][] turtleData) {
        for (Object[] turtle : turtleData) {
            int xpos = (int) turtle[0];
            int ypos = (int) turtle[1];
            int speed = (int) turtle[2];
            int width = (int) turtle[3];
            int height = (int) turtle[4];
            boolean isWet = (boolean) turtle[5];

            if (isWet) {
                background.add(new WetTurtle(xpos, ypos, speed, width, height));
            } else {
                background.add(new Turtle(xpos, ypos, speed, width, height));
            }
        }
    }

    public void setupObstacles(MyStage background, Object[][] obstacleData) {
        for (Object[] obstacle : obstacleData) {
            String imageName = (String) obstacle[0];
            int width = (int) obstacle[1];
            int height = (int) obstacle[2];
            int xpos = (int) obstacle[3];
            int ypos = (int) obstacle[4];
            int speed = (int) obstacle[5];

            Obstacle obstacleObject = createObstacle(imageName, width, height, xpos, ypos, speed);
            background.add(obstacleObject);
        }
    }

    public void setupLogs(MyStage background, Object[][] logData) {
        for (Object[] log : logData) {
            String imageName = (String) log[0];
            int width = (int) log[1];
            int height = (int) log[2];
            int xpos = (int) log[3];
            int ypos = (int) log[4];
            int yLevel = (int) log[5];
            double speed = (double) log[6];

            Log logObject = createLog(imageName, width, height, xpos, ypos, yLevel, speed);
            background.add(logObject);
        }
    }
}
