package logicPuzzleSolver.exercises;

public abstract class Exercise {
    public abstract String getTitle();

    public abstract String getDesc();

    public abstract Example[] getExamples();

    public abstract String getInitialCode();

    public abstract String getCallFunction();

    public abstract RunCase[] getRunCases();
}
