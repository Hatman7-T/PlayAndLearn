/**
 * Controller che gestisce tutti i giochi di livello di Medium
 * 	- public void initialize(): Inizializza la scena 
 * 	- public void done(ActionEvent e): Esegue il check delle risposte quando l'utente preme sul bottone "doneButton"
 * @author Paolo Laferla
 * @project Play And Learn
 */


package game1Controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Hard extends GameController {

    @FXML
    private Label answerBox1;

    @FXML
    private Label answerBox2;

    @FXML
    private Label answerBox3;

    @FXML
    private Label answerBox4;

    @FXML
    private Label answerBox5;

    @FXML
    private Button doneButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label messageLabel;

    @FXML
    private Label missing1;

    @FXML
    private Label missing2;

    @FXML
    private Label missing3;

    @FXML
    private Label missing4;

    @FXML
    private Button resetButton;
    
    
    @FXML
    public void initialize() {
    	List <Label> missingFields = Arrays.asList(missing1, missing2, missing3, missing4);
    	
    	makeDraggable(answerBox1, missingFields);
        makeDraggable(answerBox2, missingFields);
        makeDraggable(answerBox3, missingFields);
        makeDraggable(answerBox4, missingFields);
        makeDraggable(answerBox5, missingFields);
        
        makeDroppable(missing1);
        makeDroppable(missing2);
        makeDroppable(missing3);
        makeDroppable(missing4);
        
    }
    
    public void done(ActionEvent e) throws IOException {
    	List <Label> missingFields = Arrays.asList(missing1, missing2, missing3, missing4);
        List <Label> answerBoxes = Arrays.asList(answerBox1, answerBox2, answerBox3, answerBox4);
    	checkWin(e, messageLabel, missingFields, answerBoxes, doneButton, exitButton, resetButton);
    }
    
}
