package uk.ac.nott.cs.comp2013.froggergame.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Objects;

import uk.ac.nott.cs.comp2013.froggergame.controller.MainMenuController;

/**
 * Main menu view for the Frogger game, providing options to play the game, view high scores, or quit.
 */
public class MainMenuView {
    private final Stage primaryStage;
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 800;
    private static final String MENU_IMAGE_PATH = "/images/menu.png";
    private static final String FROG_ICON_PATH = "/images/froggerUp.png";
    private final MainMenuController controller;

    /**
     * Constructor to initialize the main menu view with the primary stage and controller.
     *
     * @param primaryStage The primary stage for the application.
     * @param controller   The controller handling menu actions.
     */
    public MainMenuView(Stage primaryStage, MainMenuController controller) {
        this.primaryStage = primaryStage;
        this.controller = controller;
    }

    /**
     * Displays the main menu view.
     */
    public void show() {
        VBox rootLayout = createRootLayout();
        Scene scene = new Scene(rootLayout, WINDOW_WIDTH, WINDOW_HEIGHT);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/mainmenu.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createRootLayout() {
        ImageView banner = createHeaderBanner();
        VBox menuLayout = createMenuLayout();

        VBox rootLayout = new VBox();
        rootLayout.getChildren().addAll(banner, menuLayout);
        rootLayout.setAlignment(Pos.TOP_CENTER);
        rootLayout.getStyleClass().add("root-layout");

        return rootLayout;
    }

    private VBox createMenuLayout() {
        VBox menuLayout = new VBox(
                createButton("PLAY GAME", controller::startGame),
                createButton("HIGH SCORES", controller::showHighScores),
                createButton("QUIT GAME", controller::quitGame)
        );
        menuLayout.getStyleClass().add("menu-layout");
        return menuLayout;
    }

    private ImageView createHeaderBanner() {
        ImageView banner = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(MENU_IMAGE_PATH)).toExternalForm()));
        banner.getStyleClass().add("header-banner");
        return banner;
    }

    private HBox createButton(String text, Runnable action) {
        ImageView frogIcon = createFrogIcon();
        frogIcon.setVisible(false);

        Button button = createStyledButton(text);
        button.setOnMouseEntered(event -> frogIcon.setVisible(true));
        button.setOnMouseExited(event -> frogIcon.setVisible(false));
        button.setOnAction(event -> action.run());

        HBox hBox = new HBox(frogIcon, button);
        hBox.getStyleClass().add("button-container");

        return hBox;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("menu-button");
        return button;
    }

    private ImageView createFrogIcon() {
        ImageView frogIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(FROG_ICON_PATH)).toExternalForm()));
        frogIcon.getStyleClass().add("frog-icon");
        return frogIcon;
    }
}
