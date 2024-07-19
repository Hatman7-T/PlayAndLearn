package logicPuzzleSolver.controllers;

import controller.SceneController;
import controller.UserSession;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import logicPuzzleSolver.Data;
import logicPuzzleSolver.exercises.Example;
import logicPuzzleSolver.exercises.Exercise;
import logicPuzzleSolver.exercises.RunCase;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import org.openjdk.nashorn.api.scripting.ScriptObjectMirror;
import org.openjdk.nashorn.api.scripting.ScriptUtils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import java.io.IOException;

public class ExerciseController extends SceneController {
    @FXML
    private VBox main;
    @FXML
    private Label title;
    @FXML
    private Label desc;
    @FXML
    private Label example;
    @FXML
    private TextArea code;
    @FXML
    private Label status;
    @FXML
    private Button submit;

    private Exercise data;
    private boolean success = false;

    public void initialize() {
        Exercise[] exercises = switch (Data.INSTANCE.difficulty) {
            case "easy" -> Data.INSTANCE.easyExercises;
            case "medium" -> Data.INSTANCE.mediumExercises;
            case "hard" -> Data.INSTANCE.hardExercises;
            default -> new Exercise[0];
        };
        int random = (int) (Math.random() % exercises.length);
        data = exercises[random];
        title.setText(data.getTitle());
        desc.setText(data.getDesc());
        code.setText(data.getInitialCode());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        for (Example example : data.getExamples()) {
            stringBuilder.append("Input: ");
            stringBuilder.append(example.getInput());
            stringBuilder.append("\n");
            stringBuilder.append("Output: ");
            stringBuilder.append(example.getOutput());
            stringBuilder.append("\n");
            if (example.getExplanation() != null) {
                stringBuilder.append("Spiegazione: ");
                stringBuilder.append(example.getExplanation());
                stringBuilder.append("\n");
            }
        }
        example.setText(stringBuilder.toString());
    }

    @FXML
    public void onRunClicked() {
        RunCase[] runCases = data.getRunCases();
        ScriptEngine scriptEngine = Data.INSTANCE.scriptEngine;
        String callFunction = data.getCallFunction();

        try {
            scriptEngine.eval(code.getText());
            Invocable invocable = (Invocable) scriptEngine;
            for (RunCase run : runCases) {
                Object[] args = run.getArgs();
                Object result = invocable.invokeFunction(callFunction, args);

                if (result.equals(run.getOutput())) {
                    success = true;
                    status.setVisible(true);
                    submit.setVisible(true);
                    status.setText("OK");
                } else {
                    success = false;
                    status.setVisible(true);
                    submit.setVisible(false);
                    status.setText("ERRORE");
                    System.out.println(result);
                    System.out.println(run.getOutput());
                }
            }
        } catch (Exception e) {
            success = false;
            status.setVisible(true);
            submit.setVisible(false);
            status.setText("ERRORE");
            e.printStackTrace();
        }
    }

    @FXML
    public void onSubmit(ActionEvent e) {
        if (success) {
            switch (Data.INSTANCE.difficulty) {
                case "easy" -> UserSession.currentUser.setDifficultyGame3("medium");
                case "medium" -> UserSession.currentUser.setDifficultyGame3("hard");
                case "hard" -> UserSession.currentUser.setDifficultyGame3("none");
            }
            try {
                switchToScene(e, "/scenes/UserScene.fxml");
            } catch (IOException ignored) {}
        }
    }
}
