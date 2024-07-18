package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application{
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/scenes/WelcomeScene.fxml"));
			
			primaryStage.setWidth(1300);
			primaryStage.setHeight(750);
			primaryStage.setResizable(false);
			
	
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			Image icon = new Image("/extra/icon.png");
			primaryStage.setTitle("Play and Learn Java");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}