package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.util.Stack;

import javafx.util.Duration;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;

public class Score {

    private static final int DEFAULT_DIGIT_SIZE = 30;
    private static final int DEFAULT_DIGIT_START_X = 120;
    private static final int DEFAULT_DIGIT_START_Y = 5;
    private static final int DEFAULT_DIGIT_SPACING = 30;
    private static final String DEFAULT_SCORE_LABEL_TEXT = "SCORE:";
    private static final String DEFAULT_SCORE_LABEL_FONT = "Arial";
    private static final int DEFAULT_SCORE_LABEL_FONT_SIZE = 25;
    private static final int DEFAULT_SCORE_LABEL_X = 15;
    private static final int DEFAULT_SCORE_LABEL_Y = 30;
    private int lastScore = 0;
    private ScoreManager scoreManager;
    private Pane gamePane;

    public Score() {
        this.scoreManager = new ScoreManager();
    }

    public void updateScoreDisplay(Pane pane, int score, ScoreManager scoreManager) {
        if (pane == null) return;
        Platform.runLater(() -> {
            pane.getChildren().removeIf(node -> node instanceof Digit);
            addScoreDigitsToPane(pane, scoreManager.getDigits(score));
            if (score > lastScore) {
                showScoreIncrease(pane, score - lastScore);
            } else if (score < lastScore) {
                showScoreDecrease(pane, lastScore - score);
            }
            lastScore = score;
        });
    }

    private void showScoreDecrease(Pane pane, int decrease) {
        Text minusText = new Text("-" + decrease);
        minusText.setFont(Font.font(DEFAULT_SCORE_LABEL_FONT, FontWeight.BOLD, DEFAULT_SCORE_LABEL_FONT_SIZE));
        minusText.setFill(Color.RED);
        minusText.setX(DEFAULT_DIGIT_START_X);
        minusText.setY(DEFAULT_DIGIT_START_Y + 40);
        pane.getChildren().add(minusText);
        TranslateTransition moveUp = new TranslateTransition(Duration.seconds(1), minusText);
        moveUp.setByY(-30);
        FadeTransition fade = new FadeTransition(Duration.seconds(1), minusText);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        ParallelTransition animation = new ParallelTransition(moveUp, fade);
        animation.setOnFinished(event -> pane.getChildren().remove(minusText));
        animation.play();
    }

    private void addScoreDigitsToPane(Pane pane, Stack<Integer> digits) {
        int shift = 0;
        while (!digits.isEmpty()) {
            int digit = digits.pop();
            Digit digitNode = new Digit(digit, DEFAULT_DIGIT_SIZE, DEFAULT_DIGIT_START_X + shift, DEFAULT_DIGIT_START_Y);
            pane.getChildren().add(digitNode);
            shift += DEFAULT_DIGIT_SPACING;
        }
    }

    public void createScoreLabel(Pane background) {
        Text scoreLabel = new Text(DEFAULT_SCORE_LABEL_TEXT);
        scoreLabel.setFont(Font.font(DEFAULT_SCORE_LABEL_FONT, FontWeight.BOLD,DEFAULT_SCORE_LABEL_FONT_SIZE));
        scoreLabel.setFill(Color.YELLOW);
        scoreLabel.setX(DEFAULT_SCORE_LABEL_X);
        scoreLabel.setY(DEFAULT_SCORE_LABEL_Y);
        background.getChildren().add(scoreLabel);
    }

    private void showScoreIncrease(Pane pane, int increase) {
        Text bonusText = new Text("+" + increase);
        bonusText.setFont(Font.font(DEFAULT_SCORE_LABEL_FONT, FontWeight.BOLD, DEFAULT_SCORE_LABEL_FONT_SIZE));
        bonusText.setFill(Color.GOLD);
        bonusText.setX(DEFAULT_DIGIT_START_X);
        bonusText.setY(DEFAULT_DIGIT_START_Y + 40);

        pane.getChildren().add(bonusText);

        TranslateTransition moveUp = new TranslateTransition(Duration.seconds(1), bonusText);
        moveUp.setByY(-30);

        FadeTransition fade = new FadeTransition(Duration.seconds(1), bonusText);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);

        ParallelTransition animation = new ParallelTransition(moveUp, fade);
        animation.setOnFinished(event -> pane.getChildren().remove(bonusText));
        animation.play();
    }

    public void updateScore(int newScore) {
        updateScoreDisplay(gamePane, newScore, scoreManager);
    }
}
