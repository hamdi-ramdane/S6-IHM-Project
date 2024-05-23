package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	public static Stage stage;

	@Override
	public void start(Stage primaryStage) throws IOException {

		stage = primaryStage;
	    Parent root = FXMLLoader.load(getClass().getResource("screens/login_screen.fxml"));

		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.show();

	}
    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stage.getScene().setRoot(pane);
    }
	public static void main(String[] args) {
		launch(args);
	}
}
