package Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserController extends SceneController {
	
	private userClass user;
	
    @FXML
    private Label welcomeLabel;
	
	public void setUser(userClass user) {
		this.user = user;
		updateWelcomeLabel();
	}
	
	private void updateWelcomeLabel() {
		if (user != null) {
            welcomeLabel.setText("Benvenuto " + user.getUsername());
            welcomeLabel.setVisible(true);
        }
	}
	
    @FXML
	public void exit(ActionEvent e) throws IOException {
		switchToScene(e, "/Scenes/WelcomeScene.fxml");
	}
	
    @FXML
	public void startGame1(ActionEvent e) throws IOException {
		String selectedLevel = "/ExerciseFolder/";
		switch(user.getDifficultyGame1()) {
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
		switch(user.getLevelGame1()) {
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
		switchToScene(e, selectedLevel);
	}
}
