/**
 * Controller per la gestione di tutte le azioni nell'area utente:
 * 	- public void initialize(): Inizialliza la scena
 * 	- private void updateWelcomeLabel(): Aggiorna la label con ID "WelcomeLabel"
 * 	- private void updateProgressBar1(): Aggiorna la progress bar con ID "ProgressBar1"
 * 	- private void updateProgressBar2(): Aggiorna la progress bar con ID "ProgressBar2"
 * 	- private double calcProgress(int level, String difficulty): Calcola il progresso in base a livello e difficoltà raggiunta
 * 	- public void reset(ActionEvent e): Effettua il reset della difficoltà e il livello raggiunto ai valori iniziali
 * 	- public void exit(ActionEvent e): Riporta alla scena per il login "LoginScene.fxml"
 * 	- public void startGame1(ActionEvent e): Avvia il gioco 1
 * 	- public void startGame2(ActionEvent e): Avvia il gioco 2
 * 	- private boolean showAlertWindows(ActionEvent e, String title, String description, String iconPath): Rende visibile un alert window che descrive il gioco
 * 	- public void updateCSV(): Aggiorna il file CSV con i nuovi valori dello user
 * @author Paolo Laferla, Ardelean Razvan 
 * @project Play And Learn
 */

package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class UserController extends SceneController {

	@FXML
	private ProgressBar progressBar1;

	@FXML
	private ProgressBar progressBar2;

	@FXML
	private ProgressBar progressBar3;

	@FXML
	private Label welcomeLabel;

	
	@FXML
	public void initialize() {
		updateWelcomeLabel();
		
		updateProgressBar1();
		updateProgressBar2();
		updateProgressBar3();
	}

	private void updateWelcomeLabel() {
		if (UserSession.currentUser != null) {
			welcomeLabel.setText("Benvenuto " + UserSession.currentUser.getUsername());				//Imposta la WelcomeLabel aggiungendo il nome dell'utente
			welcomeLabel.setVisible(true);
		}
	}

	private void updateProgressBar1() {
		int level = UserSession.currentUser.getLevelGame1();
		String difficulty = UserSession.currentUser.getDifficultyGame1();
		
		progressBar1.setStyle("-fx-accent: #08d12d;");
		progressBar1.setProgress(calcProgress(level, difficulty));									
	}
	
	private void updateProgressBar2() {																
		int level = UserSession.currentUser.getLevelGame2();
		String difficulty = UserSession.currentUser.getDifficultyGame2();
		
		progressBar2.setStyle("-fx-accent: #08d12d;");
		progressBar2.setProgress(calcProgress(level, difficulty));
	}
	
	
	 private void updateProgressBar3() {																
		int level = UserSession.currentUser.getLevelGame3();
		String difficulty = UserSession.currentUser.getDifficultyGame3();
		
		progressBar3.setStyle("-fx-accent: #08d12d;");
		progressBar3.setProgress(calcProgress(level, difficulty));
	}
	
	private double calcProgress(int level, String difficulty) {										
		double progress = 0.11 * level;
		double temp = 0;
		switch(difficulty) {																		//Per ogni difficoltà, tramite una semplice formula, 
		case "Medium":																				//si calcola il progresso raggiunto 
			temp = 0.33;
			progress += temp;
			break;

		case "Hard":
			temp = 0.66;
			progress += temp;
			break;
		}
		if(progress == 0.99)
			progress = 1;
		else if(progress == 0.11)
			progress = 0;
		
		return progress;
	}

	@FXML
	public void reset(ActionEvent e) throws IOException{
		UserSession.currentUser.setDifficultyGame1("Easy");
		UserSession.currentUser.setLevelGame1(1);
		
		UserSession.currentUser.setDifficultyGame2("Easy");
		UserSession.currentUser.setLevelGame2(1);
																								
		UserSession.currentUser.setDifficultyGame3("Easy");
		UserSession.currentUser.setLevelGame3(1);
		
		updateCSV();
		switchToScene(e, "/scenes/UserScene.fxml");
	}

	@FXML
	public void exit(ActionEvent e) throws IOException {
		updateCSV();
		switchToScene(e, "/scenes/WelcomeScene.fxml");
	}

	@FXML
	public void startGame1(ActionEvent e) throws IOException {
		String selectedLevel = "/exercise1Folder/";
		switch(UserSession.currentUser.getDifficultyGame1()) {
		case "Easy":
			selectedLevel += "Easy/";
			break;
		case "Medium":
			selectedLevel += "Medium/";
			break;
		case "Hard":
			selectedLevel += "Hard/";
			break;
		default:
			break;
		}
		switch(UserSession.currentUser.getLevelGame1()) {
		case 1:
			selectedLevel += "Exercise1.fxml";
			break;
		case 2:
			selectedLevel += "Exercise2.fxml";
			break;
		case 3:
			selectedLevel += "Exercise3.fxml";
			break;
		default:
			break;
		}																							//Tramite gli switch case e la concatenazione delle stringhe
																									//si crea il path a cui deve essere indirizzato lo user
		String title = "Drag and Drop";
		String description = "Trascina tutte le soluzioni nella casella corretta";
		if(showAlertWindows(e, title, description, "/extra/DragAndDrop-black.png"))
			switchToScene(e, selectedLevel);
	}
		
	@FXML
	public void startGame2(ActionEvent e) throws IOException {
		String selectedLevel = "/game2Scenes/CodeComprehensionSelectDifficultyScene.fxml";			
		String title = "Code Comprehension";
		String description = "Analizza il codice e seleziona l'opzione più appropiata";
		if(showAlertWindows(e, title, description, "/extra/code-black-solid.png"))					//L'utente dopo aver deciso di iniziare a giocare il gioco 2
			switchToScene(e, selectedLevel);														//Viene portato alla scena in cui selezionerà la difficoltà
	}
	
	@FXML
	public void startGame3(ActionEvent e) throws IOException {}
	
	private boolean showAlertWindows(ActionEvent e, String title, String description, String iconPath) throws IOException {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(description);
		
        Image ico = new Image(iconPath);
        Stage AlertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        AlertStage.getIcons().add(ico);

		Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
		okButton.setText("OK");

		Button cancelButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
		cancelButton.setText("Annulla");

		Optional<ButtonType> result = alert.showAndWait();

		if (result.isPresent()) {
			if (result.get() == ButtonType.OK) {													//Se il bottone premuto è "OK" si a come valore di ritorno true
				return true;
			}
		}
		return false;
	}

	public void updateCSV() throws IOException {
		String delimiter = RegistrationController.DELIMITER;
		String newUserInformation = UserSession.currentUser.getID()+delimiter+
				UserSession.currentUser.getUsername()+delimiter+
				UserSession.currentUser.getPassword()+delimiter+
				UserSession.currentUser.getEmail()+delimiter+
				
				UserSession.currentUser.getDifficultyGame1()+delimiter+
				UserSession.currentUser.getLevelGame1()+delimiter+
				
				UserSession.currentUser.getDifficultyGame2()+delimiter+
				UserSession.currentUser.getLevelGame2()+delimiter+									//Si salvano tutte le informazioni riguardanti l'utente 
																									//attualmente loggato 
				UserSession.currentUser.getDifficultyGame3()+delimiter+
				UserSession.currentUser.getLevelGame3();
		
		List<String> lines = Files.readAllLines(Paths.get("src/db/UserDB.csv"));
		int idToUpdate = UserSession.currentUser.getID();
		boolean updated = false;
		for (int i = 0; i < lines.size(); i++) {
			String[] data = lines.get(i).split(";");
			if (Integer.parseInt(data[0]) == idToUpdate) {											//Se si trova l'ID dell'utente attualmente loggato
				lines.set(i, newUserInformation);													
				updated = true;																		//Si imposta updated a true	
				break;
			}
		}
		if (updated) {																				//Se uptdated è impostato su true 
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/db/UserDB.csv"))) {
				for (String line : lines) {
					writer.write(line);																//Si riscrive ogni user sul file CSV
					writer.newLine();
				}
				writer.close();
			}
		} else { 
			System.out.println("ID not found.");													//Se updated è impostato su false si stampa un errore
		}
	}
}
