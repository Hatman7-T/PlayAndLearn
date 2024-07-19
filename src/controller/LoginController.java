/**
 * Controller che si occupa della funzione di Login:
 *	- public void checkLogin(ActionEvent e): Funzione che si occupa della gestione dei pulsanti nel LoginScene.fxml
 *	- public void goToHomepage(ActionEvent e): Funzione che riporta alla Homepage
 * @author Paolo Laferla, Ardelean Razvan 
 * @project Play And Learn
 */
package controller;

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
        if (btnType.equals("Login")) {									//Se il bottone scelto è quello del login
        	if (checkCred(e, unameTextField, pswTextField, null)) {		//Viene eseguito il controllo delle credenziali e se va a buon fine
        		switchToScene(e, "/scenes/UserScene.fxml");				//Si viene portati alla UserScene.fxml
            } else {
                warningLabel.setText("Credenziali non valide!");		//Se il controllo delle credenziali non va buon fine appare un avviso
                warningLabel.setVisible(true);
            }
        }
        else if(btnType.equals("Register")) {							//Se il bottone scelto è quello della registrazione 
        	switchToScene(e, "/scenes/RegisterScene.fxml");				//si viene riportati alla schermata di registrazione
        }
    }
	
	public void goToHomepage(ActionEvent e) throws IOException {
		switchToScene(e, "/scenes/WelcomeScene.fxml");					//Funzione per cambiare scena
	}
}
