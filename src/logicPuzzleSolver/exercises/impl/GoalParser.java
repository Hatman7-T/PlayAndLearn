package logicPuzzleSolver.exercises.impl;

import logicPuzzleSolver.exercises.Example;
import logicPuzzleSolver.exercises.Exercise;
import logicPuzzleSolver.exercises.RunCase;

public class GoalParser extends Exercise {
    @Override
    public String getTitle() {
        return "Goal Parser";
    }

    @Override
    public String getDesc() {
        return "Hai un Goal Parser che deve interpretare una stringa 'command'.\n'command' consite di un alfabeto di 'G', '()' e '(al)'.\nIl Goal Parser interpreta 'G' come 'G', '()' come 'o' e '(al)' come 'al'; le stringhe intepretate sono concatenate nello stesso ordine.\nData quindi una stringa 'command' ritornare l'interpretazione del Goal Parser di quella stringa.";
    }

    @Override
    public Example[] getExamples() {
        return new Example[] {
                new Example() {
                    @Override
                    public String getInput() {
                        return "command = \"G()(al)\"";
                    }

                    @Override
                    public String getOutput() {
                        return "\"Goal\"";
                    }
                },
                new Example() {
                    @Override
                    public String getInput() {
                        return "command = \"G()()()()(al)\"";
                    }

                    @Override
                    public String getOutput() {
                        return "\"Gooooal\"";
                    }
                },
                new Example() {
                    @Override
                    public String getInput() {
                        return "command = \"(al)G(al)()()G\"";
                    }

                    @Override
                    public String getOutput() {
                        return "\"alGalooG\"";
                    }
                }
        };
    }

    @Override
    public String getInitialCode() {
        return "/**\n * @param {string} command\n * @return {string}\n */\nvar interpret = function(command) {\n\t\n};";
    }

    @Override
    public String getCallFunction() {
        return "interpret";
    }

    @Override
    public RunCase[] getRunCases() {
        return new RunCase[] {
                new RunCase() {
                    @Override
                    public Object[] getArgs() {
                        return new Object[] {"G()(al)"};
                    }

                    @Override
                    public Object getOutput() {
                        return "Goal";
                    }

                    @Override
                    public Class<?> getOutputType() {
                        return String.class;
                    }
                },
                new RunCase() {
                    @Override
                    public Object[] getArgs() {
                        return new Object[] {"G()()()()(al)"};
                    }

                    @Override
                    public Object getOutput() {
                        return "Gooooal";
                    }

                    @Override
                    public Class<?> getOutputType() {
                        return String.class;
                    }
                },
                new RunCase() {
                    @Override
                    public Object[] getArgs() {
                        return new Object[] {"(al)G(al)()()G"};
                    }

                    @Override
                    public Object getOutput() {
                        return "alGalooG";
                    }

                    @Override
                    public Class<?> getOutputType() {
                        return String.class;
                    }
                }
        };
    }
}
