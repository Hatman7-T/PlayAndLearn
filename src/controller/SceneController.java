/**
 * Controller generale che gestisce tutte le scene
 * 	- public void switchToScene(ActionEvent event, String url): Funzione che gestisce il cambio di scena
 * 	- public boolean checkCred(ActionEvent e, TextField unameTextField, TextField pswTextField, TextField emailTextField): 
 * 		Funzione che effettua il controllo delle credenziali inserite dall'utente
 * @author Paolo Laferla, Ardelean Razvan 
 * @project Play And Learn
 */

package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SceneController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	//Function to switch between the current scene and the Login Scene
	public void switchToScene(ActionEvent event, String url) throws IOException {
		root = FXMLLoader.load(getClass().getResource(url));														//Load della scena
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
		stage.setScene(scene);																						//Set della scena
	}
	
	public boolean checkCred(ActionEvent e, TextField unameTextField, TextField pswTextField, TextField emailTextField) throws IOException {
	    String username = unameTextField != null ? unameTextField.getText() : null;												
	    String password = pswTextField != null ? pswTextField.getText() : null;
	    String email = emailTextField != null ? emailTextField.getText() : null;									//Controllo delle credenziali nel 
	    																											//caso in cui siano null
	    																													

	    boolean checked = false;
	    String csvFile = "src/db/UserDB.csv";  																		//Path del file CSV
	    String line;
	    String delimiter = ";";  																					//Delimitatore del file CSV


	    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	        while ((line = br.readLine()) != null) {																//Lettura riga per riga del file
	            String[] words = line.split(delimiter);																//Split delle righe usando il delimitatore
	            
	            // Check for email if emailTextField is provided
	            if (email != null && words.length > 3 && words[1].equals(username) || words[3].equals(email)) {		//Se le credenziali sono inserite 
	                checked = true;																					//per una registrazione
	                break;
	            }

	            if (email == null && words.length > 2 && words[1].equals(username) && words[2].equals(password)) {  //Se le credenziali sono inserite per un login
	            	UserClass user = new UserClass(Integer.parseInt(words[0]), words[1], words[2], words[3], 		//si crea un nuovo user
	            			words[4], Integer.parseInt(words[5]), words[6], Integer.parseInt(words[7]), words[8], Integer.parseInt(words[9]));
	            	UserSession.currentUser = user;																	//Si imposta come user corrente
	            	checked = true;
	                break;
	            }
	        }
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }

	    return checked;
	}
}
