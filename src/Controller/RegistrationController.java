package Controller;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class RegistrationController extends SceneController{
	
	@FXML
	private TextField unameTextField;
	
	@FXML
	private TextField pswTextField;
	
	@FXML
	private TextField emailTextField;
	
	@FXML
	private Label warningLabel;
	
	private String csvFile = "/DB/UserDB.csv";
	
	public void goBackToLogin(ActionEvent e) throws IOException {
		switchToScene(e, "/Scenes/LoginScene.fxml");
	}
	
	public void checkReg (ActionEvent e) throws IOException, InterruptedException {
		 if(checkCred(e, unameTextField, pswTextField, emailTextField)) {
			 warningLabel.setStyle("-fx-text-fill: #e10e0e;"); 
			 warningLabel.setText("L'utente è già registrato!");
			 warningLabel.setVisible(true);
		 }
		 else {
		        String username = unameTextField.getText();
		        String password = pswTextField.getText();
		        String email = emailTextField.getText();
		        String difficultyGame1 = "Easy";
		        String difficultyGame3 = "easy";
		        int levelGame1 = 1;
		        int ID = getNextId();
		        
		        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
		        	warningLabel.setStyle("-fx-text-fill: #e10e0e;"); 
		        	warningLabel.setText("Tutti i campi sono obbligatori!");
		            warningLabel.setVisible(true);
		            return;
		        }


		        if (ID == -1) {
		        	warningLabel.setStyle("-fx-text-fill: #e10e0e;"); 
		            warningLabel.setText("Errore nella lettura del file CSV!");
		            warningLabel.setVisible(true);
		            return;
		        }

		        appendUserToCSV(ID, username, password, email, difficultyGame1, levelGame1, difficultyGame3);
		        
		        warningLabel.setStyle("-fx-text-fill: #0aab0d;"); 
		        warningLabel.setText("Utente registrato con successo!");
		        warningLabel.setVisible(true);
		        PauseTransition pause = new PauseTransition(Duration.seconds(2));
		        pause.setOnFinished(event -> {
		            try {
		                switchToScene(e, "/Scenes/LoginScene.fxml");
		            } catch (IOException ex) {
		                ex.printStackTrace();
		            }
		        });
		        pause.play();
		 }
	}
	
	private int getNextId() {
        InputStream is = getClass().getResourceAsStream(csvFile);
        if (is == null) {
            System.err.println("File not found: " + csvFile);
            return -1;
        }

        int maxId = 0;
        String line;
        String delimiter = ";";  // Delimiter used in the CSV file

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            while ((line = br.readLine()) != null) {
                String[] words = line.split(delimiter);
                int currentId = Integer.parseInt(words[0]);
                if (currentId > maxId) {
                    maxId = currentId;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return -1;
        }

        return maxId + 1;
    }
	
	private void appendUserToCSV(int id, String username, String password, String email, String difficultyGame1, int levelGame1, String difficultyGame3) {
        String newUserData = id + ";" + username + ";" + password + ";" + email + ";" + difficultyGame1 + ";" + levelGame1 + ";" + difficultyGame3 +"\n";

        try (FileWriter fw = new FileWriter("src/DB/UserDB.csv", true)) {
        	fw.write(newUserData);
        } catch (IOException ex) {
        	ex.printStackTrace();
        }
	}
}
