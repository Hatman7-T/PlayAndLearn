package GamesController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Easy extends GameController{
	 	@FXML
	    private Label answerBox1;

	    @FXML
	    private Label answerBox2;

	    @FXML
	    private Label answerBox3;

	    @FXML
	    private Label messageLabel;

	    @FXML
	    private Label missing1;

	    @FXML
	    private Label missing2;

	    @FXML
	    private Label missing3;
	    
	    
	    @FXML
	    public void initialize() {
	    	List <Label> missingFields = Arrays.asList(missing1, missing2, missing3);
	    	
	    	makeDraggable(answerBox1, missingFields);
	        makeDraggable(answerBox2, missingFields);
	        makeDraggable(answerBox3, missingFields);
	        
	        makeDroppable(missing1);
	        makeDroppable(missing2);
	        makeDroppable(missing3);
	    }
	    
	    public void done(ActionEvent e) throws IOException {
	    	List <Label> missingFields = Arrays.asList(missing1, missing2, missing3);
	        List <Label> answerBoxes = Arrays.asList(answerBox1, answerBox2, answerBox3);
	    	checkWin(e, messageLabel, missingFields, answerBoxes);
	    }
	    
}
