package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;
import uk.ac.nott.cs.comp2013.froggergame.model.data.LevelConfig;
import uk.ac.nott.cs.comp2013.froggergame.view.managers.LevelComposite;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Level {
    private static final String CSS_PATH = "/styles/level.css";

    private final Pane background;
    private Text levelLabel;
    private final List<End> ends;

    public Level(Pane background) {
        if (background == null) {
            throw new IllegalArgumentException("Background pane cannot be null");
        }
        this.background = background;
        this.levelLabel = null;
        this.ends = new ArrayList<>();

        background.getStylesheets().add(Objects.requireNonNull(getClass().getResource(CSS_PATH)).toExternalForm());
    }

    public void addActor(Actor actor) {
        if (actor != null) {
            background.getChildren().add(actor);
            System.out.println("Actor added: " + actor.getClass().getSimpleName() + " at position (" + actor.getX() + ", " + actor.getY() + ")");
        }
    }

    public void updateLevelDisplay(int level) {
        removeExistingLevelLabel();
        levelLabel = createLevelLabel(level);
        background.getChildren().add(levelLabel);
        levelLabel.toFront();
    }

    public void resetLevelLayout(LevelConfig config) {
        ends.clear();
        LevelComposite levelComposite = new LevelComposite();
        for (int i = 0; i < config.totalEnds(); i++) {
            End end = new End(config.startX() + i * config.gap(), config.y());
            levelComposite.addComponent(end);
            ends.add(end);
        }
        levelComposite.render(background);
    }

    private void removeExistingLevelLabel() {
        if (levelLabel != null) {
            background.getChildren().remove(levelLabel);
            levelLabel = null;
        }
    }

    private Text createLevelLabel(int level) {
        Text text = new Text("LEVEL: " + level);
        text.getStyleClass().add("level-label");
        return text;
    }

    public List<Actor> getActors() {
        return background.getChildren().stream()
                .filter(node -> node instanceof Actor)
                .map(node -> (Actor) node)
                .collect(Collectors.toList());
    }

    public List<End> getEnds() {
        return ends;
    }
}
