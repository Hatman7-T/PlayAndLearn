package logicPuzzleSolver.exercises.impl;

import logicPuzzleSolver.exercises.Example;
import logicPuzzleSolver.exercises.Exercise;
import logicPuzzleSolver.exercises.RunCase;

public class TwoSums extends Exercise {
    @Override
    public String getTitle() {
        return "Somma a Due";
    }

    @Override
    public String getDesc() {
        return "Dato un array di numeri interi e un intero target, ritorna gli indici dei due numeri tali che la loro somma equivalga al numero target.\\n\\nPuoi assumere che ciascun input abbia una sola soluzione e non puoi usare lo stesso elemento due volte.";
    }

    @Override
    public Example[] getExamples() {
        return new Example[]{
                new Example() {
                    @Override
                    public String getInput() {
                        return "nums = [2,7,11,15]; target = 9";
                    }

                    @Override
                    public String getOutput() {
                        return "[0,1]";
                    }

                    @Override
                    public String getExplanation() {
                        return "Poiché nums[0] + nums[1] == 9, ritorniamo [0, 1].";
                    }
                },
                new Example() {
                    @Override
                    public String getInput() {
                        return "nums = [3,2,4]; target = 6";
                    }

                    @Override
                    public String getOutput() {
                        return "[1,2]";
                    }
                },
                new Example() {
                    @Override
                    public String getInput() {
                        return "nums = [3,3]; target = 6";
                    }

                    @Override
                    public String getOutput() {
                        return "[0,1]";
                    }
                }
        };
    }

    @Override
    public String getInitialCode() {
        return "/**\n * @param {number[]} nums\n * @param {number} target\n * @return {number[]}\n */\nvar twoSum = function(nums, target) {\n\t\n};";
    }

    @Override
    public String getCallFunction() {
        return "twoSum";
    }

    @Override
    public RunCase[] getRunCases() {
        return new RunCase[]{
                new RunCase() {
                    @Override
                    public Object[] getArgs() {
                        return new Object[]{
                                new int[]{2, 7, 11, 15}, 9
                        };
                    }

                    @Override
                    public Object getOutput() {
                        return new double[]{0.0, 1.0};
                    }

                    @Override
                    public Class<?> getOutputType() {
                        return double[].class;
                    }
                },
                new RunCase() {
                    @Override
                    public Object[] getArgs() {
                        return new Object[]{
                                new int[]{3, 2, 4}, 6
                        };
                    }

                    @Override
                    public Object getOutput() {
                        return new double[]{1.0, 2.0};
                    }

                    @Override
                    public Class<?> getOutputType() {
                        return double[].class;
                    }
                },
                new RunCase() {
                    @Override
                    public Object[] getArgs() {
                        return new Object[]{
                                new int[]{3, 3}, 6
                        };
                    }

                    @Override
                    public Object getOutput() {
                        return new double[]{0.0, 1.0};
                    }

                    @Override
                    public Class<?> getOutputType() {
                        return double[].class;
                    }
                },
                new RunCase() {
                    @Override
                    public Object[] getArgs() {
                        return new Object[]{
                                new int[]{3, 2, 4}, 7
                        };
                    }

                    @Override
                    public Object getOutput() {
                        return new double[]{0.0, 2.0};
                    }

                    @Override
                    public Class<?> getOutputType() {
                        return double[].class;
                    }
                }
        };
    }
}
