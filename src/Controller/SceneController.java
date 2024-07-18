package Controller;

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
	public Stage stage;
	public Scene scene;
	public Parent root;
	// public Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
	
	//Function to switch between the current scene and the Login Scene
	public void switchToScene(ActionEvent event, String url) throws IOException {
		root = FXMLLoader.load(getClass().getResource(url));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	public boolean checkCred(ActionEvent e, TextField unameTextField, TextField pswTextField, TextField emailTextField) throws IOException {
	    String username = unameTextField != null ? unameTextField.getText() : null;
	    String password = pswTextField != null ? pswTextField.getText() : null;
	    String email = emailTextField != null ? emailTextField.getText() : null;

	    boolean checked = false;
	    String csvFile = "src/DB/UserDB.csv";  // Path to your CSV file
	    String line;
	    String delimiter = ";";  // Delimiter used in the CSV file


	    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	        while ((line = br.readLine()) != null) {
	            // Split the current line using the delimiter
	            String[] words = line.split(delimiter);
	            
	            // Check for email if emailTextField is provided
	            if (email != null && words.length > 3 && words[1].equals(username) || words[3].equals(email)) {
	                checked = true;
	                break;
	            }

	            // Check for username and password if emailTextField is not provided
	            if (email == null && words.length > 2 && words[1].equals(username) && words[2].equals(password)) {
					String difficultyGame3 = "easy";
					if (words.length >= 7) {
						difficultyGame3 = words[6];
					}
	            	UserClass user = new UserClass(Integer.parseInt(words[0]), words[1], words[2], words[3], words[4], Integer.parseInt(words[5]), difficultyGame3);
	            	UserSession.currentUser = user;
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
