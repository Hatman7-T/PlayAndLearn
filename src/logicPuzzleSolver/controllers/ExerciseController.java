package logicPuzzleSolver.controllers;

import Controller.SceneController;
import logicPuzzleSolver.Data;
import logicPuzzleSolver.exercises.Example;
import logicPuzzleSolver.exercises.Exercise;
import logicPuzzleSolver.exercises.RunCase;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import org.openjdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.Invocable;
import javax.script.ScriptEngine;

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

    private Exercise data;

    public void initialize() {
        Exercise[] exercises = new Exercise[0];
        switch (Data.INSTANCE.difficulty) {
            case "easy":
                exercises = Data.INSTANCE.easyExercise;
        }
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
                stringBuilder.append("Explanation: ");
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
                ScriptObjectMirror result = (ScriptObjectMirror) invocable.invokeFunction(callFunction, args);
                if (result == null) {
                    throw new Exception("not returning anything");
                }
                Object realResult;
                if (result.isArray()) {
                    realResult = result.to(Object[].class);
                    System.out.println(((Object[]) realResult)[0]);
                } else {
                    realResult = result;
                }

                if (realResult.equals(run.getOutput())) {
                    System.out.println("yeah");
                } else {
                    System.out.println("skill issue");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
