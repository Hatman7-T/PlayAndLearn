package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SceneController {
	public Stage stage;
	public Scene scene;
	public Parent root;
	public Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
	
	public void setDefaultStage(Stage stage) {
		Image icon = new Image("/Extra/icon.png");
		stage.setTitle("Play and Learn Java");
		stage.getIcons().add(icon);
		stage.setMaximized(true);
	}
	
	//Function to switch between the current scene and the Login Scene
	public void switchToScene(ActionEvent event, String url) throws IOException {
		root = FXMLLoader.load(getClass().getResource(url));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
		stage.setScene(scene);
		setDefaultStage(stage);
		stage.show();
	}
	
	public boolean checkCred(ActionEvent e, TextField unameTextField, TextField pswTextField, TextField emailTextField, userClass user) throws IOException {
	    String username = unameTextField != null ? unameTextField.getText() : null;
	    String password = pswTextField != null ? pswTextField.getText() : null;
	    String email = emailTextField != null ? emailTextField.getText() : null;

	    boolean checked = false;
	    String csvFile = "/DB/UserDB.csv";  // Path to your CSV file
	    String line;
	    String delimiter = ";";  // Delimiter used in the CSV file

	    InputStream is = getClass().getResourceAsStream(csvFile);
	    if (is == null) {
	        System.err.println("File not found: " + csvFile);
	        return checked;
	    }

	    try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
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
	            	user.setUsername(words[1]);
	                user.setEmail(words[3]);
	                user.setDifficultyGame1(words[4]);
	                user.setLevelGame1(Integer.parseInt(words[5]));
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
