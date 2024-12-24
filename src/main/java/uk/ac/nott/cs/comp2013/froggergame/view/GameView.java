package uk.ac.nott.cs.comp2013.froggergame.view;

import javafx.scene.layout.Pane;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Background;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;

public class GameView extends Pane {
    private MyStage background;
    private Background staticBackground;

    public GameView() {
        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        background = new MyStage();
        staticBackground = new Background("background.png");
    }

    public void startBackground() {
        background.start();
    }

    public void playBackgroundMusic() {
        background.playMusic();
    }

    private void setupLayout() {
        if (staticBackground != null) {
            getChildren().add(staticBackground);
        }
        getChildren().add(background);
    }

    public MyStage getGameStage() {
        return background;
    }
}
