package uk.ac.nott.cs.comp2013.froggergame;

import javafx.application.Application;
import javafx.stage.Stage;
import uk.ac.nott.cs.comp2013.froggergame.view.MainMenuView;

public class Main extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		MainMenuView mainMenu = new MainMenuView(primaryStage);
		mainMenu.show();
	}
}
