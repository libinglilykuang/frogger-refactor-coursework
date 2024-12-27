package uk.ac.nott.cs.comp2013.froggergame.view.components;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Represents a time bar component with a countdown and timeout behavior.
 */
public class TimeBar {
    private static final double DEFAULT_BAR_WIDTH = 100.0;
    private static final double DEFAULT_BAR_HEIGHT = 20.0;
    private static final int DEFAULT_TOTAL_TIME = 90;

    private final Rectangle timeBar;
    private final Text timeLabel;
    private Timeline timeline;
    private double remainingTime;
    private final double barWidth;
    private final double totalTime;

    public interface TimeOutListener {
        void onTimeOut();
    }

    private TimeOutListener timeOutListener;

    /**
     * Constructor for TimeBar with default size and timeout settings.
     *
     * @param parentPane The parent pane to which the time bar will be added.
     */
    public TimeBar(Pane parentPane) {
        this(parentPane, DEFAULT_BAR_WIDTH, DEFAULT_BAR_HEIGHT, DEFAULT_TOTAL_TIME);
    }

    /**
     * Constructor for TimeBar with custom size and timeout settings.
     *
     * @param parentPane The parent pane to which the time bar will be added.
     * @param barWidth   The width of the time bar.
     * @param barHeight  The height of the time bar.
     * @param totalTime  The total countdown time in seconds.
     */
    public TimeBar(Pane parentPane, double barWidth, double barHeight, double totalTime) {
        this.barWidth = barWidth;
        this.totalTime = totalTime;
        this.remainingTime = totalTime;

        // Initialize the time bar
        timeBar = new Rectangle(barWidth, barHeight);
        timeBar.getStyleClass().add("time-bar");

        // Initialize the time label
        timeLabel = new Text("TIME");
        timeLabel.getStyleClass().add("time-label");

        // Add components to the parent pane
        parentPane.getChildren().addAll(timeBar, timeLabel);

        // Apply CSS to the parent pane
        parentPane.getStylesheets().add(
                getClass().getResource("/styles/timebar.css").toExternalForm()
        );
    }

    public void setTimeOutListener(TimeOutListener listener) {
        this.timeOutListener = listener;
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
            timeline.setCycleCount((int) totalTime);
            timeline.setOnFinished(event -> onTimeOut());
        }
        timeline.play();
    }

    private void updateTimeBar() {
        remainingTime--;

        double newWidth = (remainingTime / totalTime) * barWidth;
        timeBar.setWidth(newWidth);

        if (remainingTime < 10) {
            timeBar.getStyleClass().removeAll("time-bar", "time-bar-warning", "time-bar-danger");
            timeBar.getStyleClass().add("time-bar-danger");
        } else if (remainingTime < 20) {
            timeBar.getStyleClass().removeAll("time-bar", "time-bar-warning", "time-bar-danger");
            timeBar.getStyleClass().add("time-bar-warning");
        }
    }

    private void onTimeOut() {
        timeLabel.setText("TIME: 0");
        timeBar.getStyleClass().removeAll("time-bar", "time-bar-warning", "time-bar-danger");
        timeBar.getStyleClass().add("time-bar-timeout");

        if (timeOutListener != null) {
            timeOutListener.onTimeOut();
        }
    }

    public void reset() {
        stop();
        remainingTime = totalTime;
        timeBar.setWidth(barWidth);
        timeBar.getStyleClass().removeAll("time-bar-warning", "time-bar-danger", "time-bar-timeout");
        timeBar.getStyleClass().add("time-bar");
        timeLabel.setText("TIME");
        start();
    }

    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }
}
