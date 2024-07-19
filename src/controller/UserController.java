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
import logicPuzzleSolver.Data;

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
        updateProgressBar();
    }
    
	private void updateWelcomeLabel() {
		if (UserSession.currentUser != null) {
            welcomeLabel.setText("Benvenuto " + UserSession.currentUser.getUsername());
            welcomeLabel.setVisible(true);
        }
	}
	
	private void updateProgressBar() {
		int level = UserSession.currentUser.getLevelGame1();
		String difficulty = UserSession.currentUser.getDifficultyGame1();
		double progress = 0.11 * level;
		switch(difficulty) {
			case "Medium":
				progress *= 2; 
				break;
				
			case "Hard":
				progress *= 3;
				break;
		}
		if(progress == 0.99)
			progress = 1;
		else if(progress == 0.11)
			progress = 0;
		progressBar1.setStyle("-fx-accent: #08d12d;");
		progressBar1.setProgress(progress);

		String diffGame3 = UserSession.currentUser.getDifficultyGame3();
		double progress3 = 0.0;
		switch (diffGame3) {
			case "medium" -> progress3 = 0.33;
			case "hard" -> progress3 = 0.66;
			case "none" -> progress3 = 1.0;
		}
		progressBar3.setStyle("-fx-accent: #08d12d;");
		progressBar3.setProgress(progress3);

		//Decidere se voler inserire la progress bar nella propria parte della schermata o meno
	}
	
	@FXML
	public void reset(ActionEvent e) throws IOException{
		UserSession.currentUser.setDifficultyGame1("Easy");
		UserSession.currentUser.setLevelGame1(1);
		UserSession.currentUser.setDifficultyGame3("easy");
		//Aggiungere reset valori degli altri giochi
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
		}
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Drag and Drop");
		alert.setHeaderText(null);
		alert.setContentText("Trascina tutte le soluzioni nella casella corretta");

		Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
		okButton.setText("OK");

		Button cancelButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
		cancelButton.setText("Annulla");

		Optional<ButtonType> result = alert.showAndWait();

		if (result.isPresent()) {
		    if (result.get() == ButtonType.OK) {
		        switchToScene(e, selectedLevel);
		    }
		}
	}

    public void updateCSV() throws IOException {
    	String newUserInformation = UserSession.currentUser.getID()+";"+
				UserSession.currentUser.getUsername()+";"+
				UserSession.currentUser.getPassword()+";"+
				UserSession.currentUser.getEmail()+";"+
				UserSession.currentUser.getDifficultyGame1()+";"+
				UserSession.currentUser.getLevelGame1() + ";" +
				UserSession.currentUser.getDifficultyGame3(); //Inserire difficolta e livelli degli altri gioca
    	List<String> lines = Files.readAllLines(Paths.get("src/db/UserDB.csv"));
    	int idToUpdate = UserSession.currentUser.getID();
		boolean updated = false;
		for (int i = 0; i < lines.size(); i++) {
			String[] data = lines.get(i).split(";");
			if (Integer.parseInt(data[0]) == idToUpdate) {
				lines.set(i, newUserInformation);
				updated = true;
				break;
			}
		}
		if (updated) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/db/UserDB.csv"))) {
				for (String line : lines) {
					writer.write(line);
					writer.newLine();
				}
				writer.close();
			}
		} else {
			System.out.println("ID not found.");
		}
    }


	@FXML
	public void startGame3(ActionEvent e) throws IOException {
		String difficulty = UserSession.currentUser.getDifficultyGame3();
		if (difficulty.equals("none")) {
			return;
		}
        Data.INSTANCE.difficulty = difficulty;
        switchToScene(e, "/logicPuzzleSolver/scenes/exercise.fxml");
	}
    //Inserire funzioni per startare gli altri giochi
}
