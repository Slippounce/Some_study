package ru.nsk.nsu.shmidt.guuexecutor.commands;

import ru.nsk.nsu.shmidt.guuexecutor.StepByStepExecutor;

public class CommandPrint extends Command {
    public CommandPrint() {
        super(CommandTypes.PRINT);
    }

    @Override
    public void execute(StepByStepExecutor context) {
        for(String parameter : getParameters()){
            System.out.println("var "+ parameter + " = " + context.getVarValueByName(parameter) + "\n");
        }
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
