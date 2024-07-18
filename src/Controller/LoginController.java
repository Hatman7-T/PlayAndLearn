package Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController extends SceneController {
	
	@FXML
	private TextField unameTextField;
	
	@FXML
	private TextField pswTextField;
	
	@FXML
	private Label warningLabel;
	
    @FXML
    public void checkLogin(ActionEvent e) throws IOException {
        Button clickedButton = (Button) e.getSource();
        String btnType = clickedButton.getText();
        if (btnType.equals("Login")) {
        	if (checkCred(e, unameTextField, pswTextField, null)) {
        		switchToScene(e, "/Scenes/UserScene.fxml");
            } else {
                warningLabel.setText("Credenziali non valide!");
                warningLabel.setVisible(true);
            }
        }
        else if(btnType.equals("Register")) {
        	switchToScene(e, "/Scenes/RegisterScene.fxml");
        }
    }
	
	public void goToHomepage(ActionEvent e) throws IOException {
		switchToScene(e, "/Scenes/WelcomeScene.fxml");
	}
}
