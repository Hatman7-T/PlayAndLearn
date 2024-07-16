package GamesController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Controller.SceneController;
import Controller.UserSession;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.util.Duration;

public class GameController extends SceneController{
	
    private double dragStartX;
    private double dragStartY;
    private double initialLayoutX;
    private double initialLayoutY;
	private List <String> intialMissingText = new ArrayList<>();
    
    
    public void makeDraggable(Label label, List <Label> targets) {
    	
    	intialMissingText.clear();
    	label.setCursor(javafx.scene.Cursor.HAND);
    	
    	for (Label temp : targets) {
    		intialMissingText.add(temp.getText());
		}
    	

        label.setOnMousePressed(event -> {
            dragStartX = event.getSceneX();
            dragStartY = event.getSceneY();
            initialLayoutX = label.getLayoutX();
            initialLayoutY = label.getLayoutY();
            label.setOpacity(0.5);
            label.setCursor(javafx.scene.Cursor.CLOSED_HAND);
        });

        label.setOnMouseDragged(event -> {
            double offsetX = event.getSceneX() - dragStartX;
            double offsetY = event.getSceneY() - dragStartY;
            label.setLayoutX(initialLayoutX + offsetX);
            label.setLayoutY(initialLayoutY + offsetY);
        });

        label.setOnMouseReleased(event -> {
            boolean droppedOnTarget = false;
            
            for (Label target : targets) {
                if (isDroppedOnTarget(label, target)) {
                    String text = target.getText();
                    String newText = text.replace("___", label.getText());
                    target.setText(newText);
                    droppedOnTarget = true;
                    break;
                }
            }
            
            if (!droppedOnTarget) {
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
        label.setOnDragOver(event -> {
            if (event.getGestureSource() != label && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        label.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                String text = label.getText();
                String newText = text.replace("___", db.getString());
                label.setText(newText);
                success = true;

                // Hide the dragged label upon successful drop
                Label draggedLabel = (Label) event.getGestureSource();
                draggedLabel.setVisible(false);
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
    
    public void checkWin(ActionEvent e, Label messageLabel, List <Label> missingFields, List <Label> answerBoxes) throws IOException {
    	if(compareLabel(missingFields, answerBoxes)) {
    		messageLabel.setStyle("-fx-text-fill: green;");
    		messageLabel.setText("Soluzione corretta!");
    		messageLabel.setVisible(true);
    		PauseTransition pause = new PauseTransition(Duration.seconds(2));
	        pause.setOnFinished(event -> {
	            if(UserSession.currentUser.getLevelGame1() >= 3) {
					switch(UserSession.currentUser.getDifficultyGame1()) {
						case "Easy":
							UserSession.currentUser.setDifficultyGame1("Medium");
							UserSession.currentUser.setLevelGame1(1);
							break;
						case "Medium":
							UserSession.currentUser.setDifficultyGame1("Hard");
							UserSession.currentUser.setLevelGame1(1);
							break;
						case "Hard":
							//Inserire scena in cui ci si congratula per il completamento dei livelli
							break;
						default:
							break;
					}
				}else {
					int newLevel = UserSession.currentUser.getLevelGame1() + 1;
					UserSession.currentUser.setLevelGame1(newLevel);
				}
	            String url = "/exercise1Folder/"+UserSession.currentUser.getDifficultyGame1()+"/Exercise"+UserSession.currentUser.getLevelGame1()+".fxml";
	            try {
					switchToScene(e, url);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        });
	        pause.play();
    		
    	}else {
    		messageLabel.setStyle("-fx-text-fill: red;");
    		messageLabel.setText("Soluzione sbagliata!");
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
    	switchToScene(e, "/Scenes/UserScene.fxml");
    }
}
