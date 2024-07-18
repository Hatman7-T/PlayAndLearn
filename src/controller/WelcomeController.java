package controller;

import java.io.IOException;

import javafx.event.ActionEvent;

public class WelcomeController extends SceneController{
	public void goToLoginScene(ActionEvent e) throws IOException {
		switchToScene(e, "/scenes/LoginScene.fxml");
	}
}
