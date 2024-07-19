package logicPuzzleSolver.exercises.impl;

import logicPuzzleSolver.exercises.Example;
import logicPuzzleSolver.exercises.Exercise;
import logicPuzzleSolver.exercises.RunCase;

public class RunningSum extends Exercise {
    @Override
    public String getTitle() {
        return "Running Sum";
    }

    @Override
    public String getDesc() {
        return "Dato un array di numeri, definiamo la somma corrente di un array come 'runningSums[i] = sum(nums[0]...nums[i]).\nRitorna la somma corrente dell'array 'nums'.";
    }

    @Override
    public Example[] getExamples() {
        return new Example[] {
                new Example() {
                    @Override
                    public String getInput() {
                        return "nums = [1, 2, 3, 4]";
                    }

                    @Override
                    public String getOutput() {
                        return "[1, 3, 6, 10]";
                    }

                    @Override
                    public String getExplanation() {
                        return "La somma corrente si ricava con [1, 1+2, 1+2+3, 1+2+3+4]";
                    }
                },
                new Example() {
                    @Override
                    public String getInput() {
                        return "nums = [1, 1, 1, 1, 1]";
                    }

                    @Override
                    public String getOutput() {
                        return "[1, 2, 3, 4, 5]";
                    }
                },
                new Example() {
                    @Override
                    public String getInput() {
                        return "nums = [3, 1, 2, 10, 1]";
                    }

                    @Override
                    public String getOutput() {
                        return "[3, 4, 6, 16, 17]";
                    }
                }
        };
    }

    @Override
    public String getInitialCode() {
        return "/**\n * @param {number[]} nums\n * @return {number[]}\n */\nvar runningSum = function(nums) {\n\t\n};";
    }

    @Override
    public String getCallFunction() {
        return "runningSum";
    }

    @Override
    public RunCase[] getRunCases() {
        return new RunCase[] {
                new RunCase() {
                    @Override
                    public Object[] getArgs() {
                        return new Object[] {
                                new int[] {1, 2, 3, 4}
                        };
                    }

                    @Override
                    public Object getOutput() {
                        return new Double[] {1.0, 3.0, 6.0, 10.0};
                    }

                    @Override
                    public Class<?> getOutputType() {
                        return Double[].class;
                    }
                },
                new RunCase() {
                    @Override
                    public Object[] getArgs() {
                        return new Object[] {
                                new int[] {1, 1, 1, 1, 1}
                        };
                    }

                    @Override
                    public Object getOutput() {
                        return new Double[] {1.0, 2.0, 3.0, 4.0, 5.0};
                    }

                    @Override
                    public Class<?> getOutputType() {
                        return Double[].class;
                    }
                },
                new RunCase() {
                    @Override
                    public Object[] getArgs() {
                        return new Object[] {
                                new int[] {3, 1, 2, 10, 1}
                        };
                    }

                    @Override
                    public Object getOutput() {
                        return new Double[] {3.0, 4.0, 6.0, 16.0, 17.0};
                    }

                    @Override
                    public Class<?> getOutputType() {
                        return Double[].class;
                    }
                }
        };
    }
}
