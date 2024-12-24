package uk.ac.nott.cs.comp2013.froggergame.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.List;
import java.util.Objects;

import uk.ac.nott.cs.comp2013.froggergame.controller.GameController;
import uk.ac.nott.cs.comp2013.froggergame.controller.GameControllerFactory;

public class MainMenuView {
    private final Stage primaryStage;
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 800;
    private static final String BACKGROUND_COLOR = "#3E5596";
    private GameView gameView;
    private GameController gameController;

    public MainMenuView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() {
        VBox rootLayout = createRootLayout();
        Scene scene = new Scene(rootLayout, WINDOW_WIDTH, WINDOW_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createRootLayout() {
        ImageView banner = createHeaderBanner();
        VBox menuLayout = createMenuLayout();

        VBox rootLayout = new VBox();
        rootLayout.getChildren().addAll(banner, menuLayout);
        rootLayout.setAlignment(Pos.TOP_CENTER);
        rootLayout.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");

        return rootLayout;
    }

    private VBox createMenuLayout() {
        HBox playGameButton = createButton("PLAY GAME", this::startGame);
        HBox highScoresButton = createButton("HIGH SCORES", this::showHighScores);
        HBox quitGameButton = createButton("QUIT GAME", this::quitGame);

        VBox menuLayout = new VBox(30, playGameButton, highScoresButton, quitGameButton);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setTranslateY(30);

        return menuLayout;
    }

    private ImageView createHeaderBanner() {
        ImageView banner = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/menu.png")).toExternalForm()));
        banner.setFitWidth(WINDOW_WIDTH);
        banner.setPreserveRatio(true);
        return banner;
    }

    private HBox createButton(String text, Runnable action) {
        ImageView frogIcon = createFrogIcon();
        frogIcon.setVisible(false);

        Button button = createStyledButton(text);
        button.setOnMouseEntered(event -> {
            button.setTextFill(Color.YELLOW);
            frogIcon.setVisible(true);
            button.setCursor(javafx.scene.Cursor.HAND);
        });
        button.setOnMouseExited(event -> {
            button.setTextFill(Color.LIMEGREEN);
            frogIcon.setVisible(false);
        });
        button.setOnAction(event -> action.run());

        HBox hBox = new HBox(20, frogIcon, button);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        button.setTextFill(Color.LIMEGREEN);
        button.setStyle("-fx-background-color: black; -fx-border-color: limegreen; -fx-border-width: 2px;");
        button.setPrefWidth(400);
        return button;
    }

    private ImageView createFrogIcon() {
        ImageView frogIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/froggerUp.png")).toExternalForm()));
        frogIcon.setFitWidth(30);
        frogIcon.setFitHeight(30);
        return frogIcon;
    }

    private void startGame() {
        initializeStage(primaryStage);

        gameView.startBackground();
        gameView.playBackgroundMusic();
        gameController.startGameLoop();
    }

    private void initializeStage(Stage primaryStage) {
        gameView = new GameView();
        gameController = GameControllerFactory.createGameController(gameView.getGameStage());
        Scene scene = new Scene(gameView, WINDOW_WIDTH, WINDOW_HEIGHT);
        gameController.configureInputHandling(scene);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void showHighScores() {
        List<String[]> scores = List.of(
                new String[]{"PLAYER1", "1200"},
                new String[]{"PLAYER2", "950"},
                new String[]{"PLAYER3", "800"}
        );
        HighScoresView highScoresView = new HighScoresView(primaryStage, scores);
        highScoresView.show();
    }


    private void quitGame() {
        primaryStage.close();
    }
}
