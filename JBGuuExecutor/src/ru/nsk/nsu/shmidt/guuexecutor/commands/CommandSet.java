package ru.nsk.nsu.shmidt.guuexecutor.commands;

import ru.nsk.nsu.shmidt.guuexecutor.StepByStepExecutor;

public class CommandSet extends Command {
    public CommandSet() {
        super(CommandTypes.SET);
    }

    @Override
    public void execute(StepByStepExecutor context) {
        String varName = getArgumentByIndex(0);
        int value = Integer.parseInt(getArgumentByIndex(1));
        context.getVariables().put(varName, value);
    }

    @Override
    public boolean isValid() {
        if(getNumberOfParameters() == 2){
            try{
                int testVarValue = Integer.parseInt(getArgumentByIndex(1));
                return true;
            }catch (NumberFormatException ignore){ }
        }
        return false;
    }


}
