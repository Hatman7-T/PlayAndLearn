package logicPuzzleSolver;

import logicPuzzleSolver.exercises.Exercise;
import logicPuzzleSolver.exercises.impl.SmallestEvenMultiple;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Data {
    public static Data INSTANCE = new Data();

    public String difficulty = "";
    public final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
    public Exercise[] easyExercises = new Exercise[]{new SmallestEvenMultiple()};
    public Exercise[] mediumExercises = new Exercise[] {};
    public Exercise[] hardExercises = new Exercise[] {};
}
