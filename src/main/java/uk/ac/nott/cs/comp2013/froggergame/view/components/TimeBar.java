package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class TimeBar {
    private static final int BAR_WIDTH = 100;
    private static final int BAR_HEIGHT = 20;
    private static final int TOTAL_TIME = 30;

    private final Rectangle timeBar;
    private final Text timeLabel;
    private Timeline timeline;
    private double remainingTime;

    public interface TimeOutListener {
        void onTimeOut();
    }

    private TimeOutListener timeOutListener;

    public void setTimeOutListener(TimeOutListener listener) {
        this.timeOutListener = listener;
    }

    public TimeBar(Pane parentPane) {
        timeBar = new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.GREEN);
        timeBar.setX(500);
        timeBar.setY(10);
        timeBar.setArcWidth(15);
        timeBar.setArcHeight(15);

        timeLabel = new Text("TIME");
        timeLabel.setX(430);
        timeLabel.setY(30);
        timeLabel.setFill(Color.YELLOW);
        timeLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
        timeBar.setFill(Color.GREEN);

        remainingTime = TOTAL_TIME;
        parentPane.getChildren().addAll(timeBar, timeLabel);
    }

    public Rectangle getRectangle() {
        return timeBar;
    }

    public Text getText() {
        return timeLabel;
    }

    public void start() {
        if (timeline == null) {
            timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), event -> updateTimeBar())
            );
            timeline.setCycleCount(TOTAL_TIME);
            timeline.setOnFinished(event -> onTimeOut());
        }
        timeline.play();
    }

    private void updateTimeBar() {
        remainingTime--;

        double newWidth = (remainingTime / TOTAL_TIME) * BAR_WIDTH;
        timeBar.setWidth(newWidth);

        if (remainingTime < 10) {
            timeBar.setFill(Color.RED);
        } else if (remainingTime < 20) {
            timeBar.setFill(Color.ORANGE);
        }

        //timeLabel.setText("TIME: " + (int) remainingTime);
    }

    private void onTimeOut() {
        timeLabel.setText("TIME: 0");

        timeBar.setFill(Color.GRAY);

        if (timeOutListener != null) {
            timeOutListener.onTimeOut();
        }
    }

    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }
}
