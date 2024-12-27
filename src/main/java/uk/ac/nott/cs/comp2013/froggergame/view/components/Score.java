package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.util.Objects;
import java.util.Stack;

import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;

public class Score {
    private static final String SCORE_LABEL_CLASS = "score-label";
    private static final String DIGIT_CLASS = "score-digit";
    private static final String BONUS_TEXT_CLASS = "bonus-text";

    private final ScoreManager scoreManager;

    public Score() {
        this.scoreManager = new ScoreManager();
    }

    public void updateScoreDisplay(Pane pane, int score, ScoreManager scoreManager) {
        if (pane == null) return;
        Platform.runLater(() -> {
            pane.getChildren().removeIf(node -> node.getStyleClass().contains(DIGIT_CLASS));
            addScoreDigitsToPane(pane, scoreManager.getDigits(score));
        });
    }

    private void addScoreDigitsToPane(Pane pane, Stack<Integer> digits) {
        int shift = 0;
        while (!digits.isEmpty()) {
            int digit = digits.pop();
            Digit digitNode = new Digit(digit, 30, 120 + shift, 5);
            digitNode.getStyleClass().add(DIGIT_CLASS);
            pane.getChildren().add(digitNode);
            shift += 30;
        }
    }

    public void createScoreLabel(Pane background) {
        Text scoreLabel = new Text("SCORE:");
        scoreLabel.getStyleClass().add(SCORE_LABEL_CLASS);
        background.getChildren().add(scoreLabel);

        background.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/styles/score.css")).toExternalForm()
        );
    }

    public void updateScore(int newScore) {
        updateScoreDisplay(null, newScore, scoreManager);
    }
}
