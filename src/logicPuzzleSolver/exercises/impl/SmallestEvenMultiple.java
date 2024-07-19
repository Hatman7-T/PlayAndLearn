package logicPuzzleSolver.exercises.impl;

import logicPuzzleSolver.exercises.Example;
import logicPuzzleSolver.exercises.Exercise;
import logicPuzzleSolver.exercises.RunCase;

public class SmallestEvenMultiple extends Exercise {
    @Override
    public String getTitle() {
        return "Multiplo più piccolo";
    }

    @Override
    public String getDesc() {
        return "Dato un numero intero positivo n, ritornare il numero intero positivo più piccolo che sia multiplo sia di 2 che di n.";
    }

    @Override
    public Example[] getExamples() {
        return new Example[] {
                new Example() {
                    @Override
                    public String getInput() {
                        return "n = 5";
                    }

                    @Override
                    public String getOutput() {
                        return "10";
                    }

                    @Override
                    public String getExplanation() {
                        return "Il multiplo più piccolo di 2 e 5 è 10.";
                    }
                },
                new Example() {
                    @Override
                    public String getInput() {
                        return "n = 6";
                    }

                    @Override
                    public String getOutput() {
                        return "6";
                    }

                    @Override
                    public String getExplanation() {
                        return "Il multiplo più piccolo di 2 e 6 è 6.";
                    }
                }
        };
    }

    @Override
    public String getInitialCode() {
        return "/**\n * @param {number} n\n * @return {number}\n */\nvar smallestEvenMultiple = function(n) {\n\t\n};";
    }

    @Override
    public String getCallFunction() {
        return "smallestEvenMultiple";
    }

    @Override
    public RunCase[] getRunCases() {
        return new RunCase[] {
                new RunCase() {
                    @Override
                    public Object[] getArgs() {
                        return new Object[] {
                                5
                        };
                    }

                    @Override
                    public Object getOutput() {
                        return 10.0;
                    }

                    @Override
                    public Class<?> getOutputType() {
                        return Double.class;
                    }
                },
                new RunCase() {
                    @Override
                    public Object[] getArgs() {
                        return new Object[] {
                                6
                        };
                    }

                    @Override
                    public Object getOutput() {
                        return 6;
                    }

                    @Override
                    public Class<?> getOutputType() {
                        return Double.class;
                    }
                },
                new RunCase() {
                    @Override
                    public Object[] getArgs() {
                        return new Object[] {
                                11
                        };
                    }

                    @Override
                    public Object getOutput() {
                        return 22.0;
                    }

                    @Override
                    public Class<?> getOutputType() {
                        return Double.class;
                    }
                }
        };
    }
}
