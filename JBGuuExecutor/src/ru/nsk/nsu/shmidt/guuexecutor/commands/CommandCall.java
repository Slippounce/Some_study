package ru.nsk.nsu.shmidt.guuexecutor.commands;

import ru.nsk.nsu.shmidt.guuexecutor.StackEntry;
import ru.nsk.nsu.shmidt.guuexecutor.StepByStepExecutor;

import java.util.Stack;

public class CommandCall extends Command {
    public CommandCall() {
        super(CommandTypes.CALL);
    }

    @Override
    public void execute(StepByStepExecutor context) {
        Stack<StackEntry> stack = context.getStackTrace();
        stack.push(new StackEntry(getArgumentByIndex(0), stack.peek().getLineNumber() + context.getCommandNumberInCurrentProcedure() + 1/*context.getProgramKeeper().getProcedureDescription(getArgumentByIndex(0)).getLineNumber())*/));
        context.setCommandNumberInCurrentProcedure(-1);
        context.setNumberOfLastCommandInCurrentProcedure(context.getProgramKeeper().getProcedureDescription(getArgumentByIndex(0)).getCommandNumber() - 1);
    }

    @Override
    public boolean isValid() {
        return getNumberOfParameters() == 1;
    }
}
