package ru.nsk.nsu.shmidt.guuexecutor.commands;

import ru.nsk.nsu.shmidt.guuexecutor.StepByStepExecutor;

public class CommandSub extends Command {
    public CommandSub() {
        super(CommandTypes.SUB);
    }

    @Override
    public void execute(StepByStepExecutor context) {

    }

    @Override
    public boolean isValid() {
        return getNumberOfParameters() == 1;
    }
}
