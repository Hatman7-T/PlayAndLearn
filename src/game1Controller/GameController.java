/**
 * Controller generale che gestisce tutte le azioni riguardanti il gioco 1 "Drag and Drop"
 * 	- public void reset(ActionEvent e): Resetta il livello
 * 	- public void makeDraggable(Label label, List <Label> targets): Rende le answerBox trascinabili
 * 	- public void makeDroppable(Label label): Da all'utente la possibilità di rilasciare le answerBox sui target
 * 	- private boolean isDroppedOnTarget(Label label, Label target): Controlla che l'answerBox venga rilasciata su un missingField
 * 	- public void checkWin(ActionEvent e, Label messageLabel, List <Label> missingFields, List <Label> answerBoxes, Button doneButton, 
			Button exitButton, Button resetButton): Controllo delle risposte date dall'utente
 * 	- private boolean compareLabel(List<Label> missingFields, List<Label> answerBoxes): Compara le label e ritorna un boolean
 * 	- public void exit(ActionEvent e): Permette di tornare all'area utente
 * 	- private void showAlert(String difficulty): Mostra finestre di alert quando si sale di difficoltà
 * @author Paolo Laferla
 * @project Play And Learn
 */


package game1Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.UserController;
import controller.UserSession;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameController extends UserController{

	private double dragStartX;
	private double dragStartY;
	private double initialLayoutX;
	private double initialLayoutY;
	private List <String> intialMissingText = new ArrayList<>();


	public void reset(ActionEvent e) throws IOException {
		int level = UserSession.currentUser.getLevelGame1();
		String difficulty = UserSession.currentUser.getDifficultyGame1();					
		switchToScene(e, "/exercise1Folder/"+difficulty+"/Exercise"+level+".fxml");
	}

	public void makeDraggable(Label label, List <Label> targets) {

		intialMissingText.clear();
		label.setCursor(javafx.scene.Cursor.HAND);

		for (Label temp : targets) {
			intialMissingText.add(temp.getText());
		}


		label.setOnMousePressed(event -> {												//Quando l'utente clicca la box che vuole trascinare prendere le 
			dragStartX = event.getSceneX();												//Coordinate in aggionramento di questa e le sue coordinate iniziali
			dragStartY = event.getSceneY();
			initialLayoutX = label.getLayoutX();
			initialLayoutY = label.getLayoutY();
			label.setOpacity(0.5);														//Cambia l'opacità
			label.setCursor(javafx.scene.Cursor.CLOSED_HAND);							//E il tipo di cursore
		});

		label.setOnMouseDragged(event -> {												//Aggiorna la variabile offset
			double offsetX = event.getSceneX() - dragStartX;
			double offsetY = event.getSceneY() - dragStartY;
			label.setLayoutX(initialLayoutX + offsetX);									//Che viene utilizzata per modificare il layout della box 
			label.setLayoutY(initialLayoutY + offsetY);
		});

		label.setOnMouseReleased(event -> {												//Quando la box viene rilasciata
			boolean droppedOnTarget = false;

			for (Label target : targets) {
				if (isDroppedOnTarget(label, target)) {									//Controlla che sia stata rilasciata su un target
					if(target.getText().contains("___")) {
						String text = target.getText();
						String newText = text.replace("___", label.getText());			//E in quel caso se il target contiene la stringa "___" viene modificato
						target.setText(newText);										//Il testo
						droppedOnTarget = true;
						break;
					}
				}
			}

			if (!droppedOnTarget) {														//Se no viene riportata alla posizione iniziale
				label.setLayoutX(initialLayoutX);
				label.setLayoutY(initialLayoutY);
				label.setOpacity(1);
			} else {
				label.setVisible(false);
			}
			label.setCursor(javafx.scene.Cursor.HAND);
		});
	}

	public void makeDroppable(Label label) {												
		label.setOnDragOver(event -> {													//Si rileva se la answerBox passa sul target
			if (event.getGestureSource() != label && event.getDragboard().hasString()) {
				event.acceptTransferModes(TransferMode.MOVE);
			}
			event.consume();
		});

		label.setOnDragDropped(event -> {												//Se la answerBox viene rilasciata sul target  
			Dragboard db = event.getDragboard();
			boolean success = false;
			if (db.hasString()) {														//Si controlla che la dragBoard abbia contenga una stringa
				String text = label.getText();
				String newText = text.replace("___", db.getString());					//E in quel caso si modifica la label del target
				label.setText(newText);
				success = true;

				Label draggedLabel = (Label) event.getGestureSource();
				draggedLabel.setVisible(false);											//E la label si rende non visibile
			}
			event.setDropCompleted(success);
			event.consume();
		});
	}

	private boolean isDroppedOnTarget(Label label, Label target) {
		Bounds labelBounds = label.localToScene(label.getBoundsInLocal());
		Bounds targetBounds = target.localToScene(target.getBoundsInLocal());
		return labelBounds.intersects(targetBounds);
	}

	public void checkWin(ActionEvent e, Label messageLabel, List <Label> missingFields, List <Label> answerBoxes, Button doneButton, 
			Button exitButton, Button resetButton) throws IOException {

		if(compareLabel(missingFields, answerBoxes)) {										//Se l'asnwerBox è rilasciata sul missingField corretto 
			doneButton.setDisable(true);
			exitButton.setDisable(true);
			resetButton.setDisable(true);
			messageLabel.setStyle("-fx-text-fill: green;");
			messageLabel.setText("Soluzione corretta!");
			messageLabel.setVisible(true);
			PauseTransition pause = new PauseTransition(Duration.seconds(2));
			if(UserSession.currentUser.getLevelGame1() >= 3) {
				switch(UserSession.currentUser.getDifficultyGame1()) {
				case "Easy":
					showAlert("Easy");
					UserSession.currentUser.setDifficultyGame1("Medium");
					UserSession.currentUser.setLevelGame1(1);
					switchToScene(e, "/exercise1Folder/"+UserSession.currentUser.getDifficultyGame1()+"/Exercise"+UserSession.currentUser.getLevelGame1()+".fxml");
					break;
				case "Medium":
					showAlert("Medium");
					UserSession.currentUser.setDifficultyGame1("Hard");
					UserSession.currentUser.setLevelGame1(1);
					switchToScene(e, "/exercise1Folder/"+UserSession.currentUser.getDifficultyGame1()+"/Exercise"+UserSession.currentUser.getLevelGame1()+".fxml");
					break;
				case "Hard":
					showAlert("Hard");
					switchToScene(e, "/scenes/UserScene.fxml");
					break;
				default:
					break;
				}																			//Si disabilitano i bottoni, si passa alla scena successiva, 
				updateCSV();																//E si aggiorna il file CSV
			}else {									
				pause.setOnFinished(event -> {
					int newLevel = UserSession.currentUser.getLevelGame1() + 1;
					UserSession.currentUser.setLevelGame1(newLevel);

					try {
						updateCSV();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					String url = "/exercise1Folder/"+UserSession.currentUser.getDifficultyGame1()+"/Exercise"+UserSession.currentUser.getLevelGame1()+".fxml";

					try {
						switchToScene(e, url);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
				pause.play();
			}
		}else {																				//Altrimenti appare un messaggio d'errore 
			doneButton.setDisable(true);
			exitButton.setDisable(true);
			messageLabel.setStyle("-fx-text-fill: red;");
			messageLabel.setText("Soluzione errata!");
			messageLabel.setVisible(true);
		}
	}

	private boolean compareLabel(List<Label> missingFields, List<Label> answerBoxes) {
		for (int i = 0; i < missingFields.size(); i++) {
			String expectedText = intialMissingText.get(i);
			expectedText = expectedText.replaceAll("___", answerBoxes.get(i).getText());
			String actualText = missingFields.get(i).getText();
			if(!expectedText.equals(actualText)) {
				return false;
			}
		}
		return true;
	}

	public void exit(ActionEvent e) throws IOException {
		updateCSV();
		switchToScene(e, "/scenes/UserScene.fxml");
	}

	private void showAlert(String difficulty) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Congratulazioni!");
		alert.setHeaderText(null);
		switch(difficulty) {
		case "Easy":
			alert.setContentText("Hai appena concluso la sezione: Facile");
			break;
		case "Medium":
			alert.setContentText("Hai appena concluso la sezione: Intermedia");
			break;
		case "Hard":
			alert.setContentText("Hai appena conluso il gioco");
			break;
		}

		Image ico = new Image("/extra/DragAndDrop-black.png");

		Stage AlertStage = (Stage) alert.getDialogPane().getScene().getWindow();
		AlertStage.getIcons().add(ico);

		alert.showAndWait();
	}
}
