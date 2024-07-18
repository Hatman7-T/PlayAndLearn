package logicPuzzleSolver;

import logicPuzzleSolver.exercises.Exercise;
import logicPuzzleSolver.exercises.impl.TwoSums;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Data {
    public static Data INSTANCE = new Data();

    public String difficulty = "";
    public final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
    public Exercise[] easyExercise = new Exercise[]{new TwoSums()};
}
