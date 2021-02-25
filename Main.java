package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		Pane mainPane=(Pane)FXMLLoader.load(Main.class.getResource("home.fxml"));
		primaryStage.setScene(new Scene(mainPane));
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
