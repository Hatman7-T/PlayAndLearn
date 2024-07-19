/**
 * Controller che si occupa della gestione della registrazione:
 * 	- public void goBackToLogin(ActionEvent e): Funzione che permette di tornare alla scenea LoginScene.fxml
 *  - public void checkReg (ActionEvent e): Funzione che esegue il controllo dei dati inseriti nei campi di registrazione
 *  - private int getNextId(): Funzione che calcola l'ID del nuovo utente 
 *  - private void appendUserToCSV(int id, String username, String password, String email, String difficultyGame, int levelGame): 
 *    	Funzione che gestisce l'aggiunzione del nuovo utente al file CSV
 * @author Paolo Laferla, Ardelean Razvan 
 * @project Play And Learn
 */

package controller;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class RegistrationController extends SceneController{

	@FXML
	private Button backButton;

	@FXML
	private TextField emailTextField;

	@FXML
	private TextField pswTextField;

	@FXML
	private Button regButton;

	@FXML
	private TextField unameTextField;

	@FXML
	private Label warningLabel;

	private String csvFile = "/db/UserDB.csv";

	protected final static String DELIMITER = ";";  // Delimiter used in the CSV file

	public void goBackToLogin(ActionEvent e) throws IOException {
		switchToScene(e, "/scenes/LoginScene.fxml");
	}

	public void checkReg (ActionEvent e) throws IOException, InterruptedException {
		if(checkCred(e, unameTextField, pswTextField, emailTextField)) {								//Utente già registrato
			warningLabel.setStyle("-fx-text-fill: #e10e0e;"); 											//Avviso a schermo 
			warningLabel.setText("L'utente è già registrato!");
			warningLabel.setVisible(true);
		}
		else {																							//Utente non ancora registrato
			String username = unameTextField.getText();
			String password = pswTextField.getText();
			String email = emailTextField.getText();
			String difficultyGame = "Easy";
			int levelGame = 1;
			int ID = getNextId();																		//Imposto le variabili con tutti i dati del nuovo utente

			if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {							//Se tutti i campi non sono compilati
				warningLabel.setStyle("-fx-text-fill: #e10e0e;"); 										//Avviso a schermo
				warningLabel.setText("Tutti i campi sono obbligatori!");
				warningLabel.setVisible(true);
				return;
			}


			if (ID == -1) {																				//Errore apertura file 
				warningLabel.setStyle("-fx-text-fill: #e10e0e;"); 										//Avviso a schermo
				warningLabel.setText("Errore nella lettura del file CSV!");
				warningLabel.setVisible(true);
				return;
			}

			backButton.setDisable(true);																//Nel caso in cui la registrazione vada a buon fine
			regButton.setDisable(true);
			appendUserToCSV(ID, username, password, email, difficultyGame, levelGame);					//Aggiungo il nuovo utente al file CSV

			warningLabel.setStyle("-fx-text-fill: #0aab0d;"); 
			warningLabel.setText("Utente registrato con successo!");									//Avviso a schermo
			warningLabel.setVisible(true);
			PauseTransition pause = new PauseTransition(Duration.seconds(2));
			pause.setOnFinished(event -> {
				try {
					switchToScene(e, "/scenes/LoginScene.fxml");
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			});
			pause.play();
		}
	}

	private int getNextId() {																			
		InputStream is = getClass().getResourceAsStream(csvFile);										//Leggo il file dove sono registrati tutti gli User
		if (is == null) {																				//Se non viene trovato -> Errore in console
			System.err.println("File not found: " + csvFile);
			return -1;
		}

		int maxId = 0;	
		String line;

		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {						
			while ((line = br.readLine()) != null) {													//Lettura riga per riga del file 
				String[] words = line.split(DELIMITER);
				int currentId = Integer.parseInt(words[0]);
				if (currentId > maxId) {																//Controllo qual è l'ID più grande registrato
					maxId = currentId;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return -1;
		}

		return maxId + 1;																				//Ritorno l'ID più grande attualmente registrato + 1
	}

	private void appendUserToCSV(int id, String username, String password, String email, String difficultyGame, int levelGame) {
		String newUserData = id + DELIMITER + username + DELIMITER + password + DELIMITER + email + DELIMITER
				+ difficultyGame + DELIMITER + levelGame + DELIMITER 
				+ difficultyGame + DELIMITER + levelGame + DELIMITER
				+ difficultyGame + DELIMITER + levelGame
				+"\n";																					//Creazione della stringa per il nuovo utente

		try (FileWriter fw = new FileWriter("src/db/UserDB.csv", true)) {
			fw.write(newUserData);																		//Scrittura su file
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
