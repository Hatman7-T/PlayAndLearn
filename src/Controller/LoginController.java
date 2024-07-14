package Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        userClass user = new userClass();
        if (btnType.equals("Login")) {
        	if (checkCred(e, unameTextField, pswTextField, null, user)) {
        		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/UserScene.fxml"));
                Parent root = loader.load();

                // Get the target controller
                UserController userController = loader.getController();

                // Pass the User object to the UserController
                userController.setUser(user);

                // Set the new scene
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
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
