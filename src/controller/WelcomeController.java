/**
 * Controller che gestisce la scena WelcomeScene.fxml
 * 	- public void goToLoginScene(ActionEvent e): Funzione per passare alla scena LoginScene.fxml
 * @author Paolo Laferla, Ardelean Razvan 
 * @project Play And Learn
 */

package controller;

import java.io.IOException;

import javafx.event.ActionEvent;

public class WelcomeController extends SceneController{
	public void goToLoginScene(ActionEvent e) throws IOException {
		switchToScene(e, "/scenes/LoginScene.fxml");
	}
}
