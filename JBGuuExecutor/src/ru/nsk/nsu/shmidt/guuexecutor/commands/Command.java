package ru.nsk.nsu.shmidt.guuexecutor.commands;

import ru.nsk.nsu.shmidt.guuexecutor.StepByStepExecutor;

import java.util.ArrayList;

public abstract class Command {
    private int lineNumber;
    private final CommandTypes commandType;
    private ArrayList<String> parameters;
    public Command(CommandTypes commandType){
        this.commandType = commandType;
        this.parameters = new ArrayList<>();
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void addParameter(String parameter){
        parameters.add(parameter);
    }

    public CommandTypes getCommandType() {
        return commandType;
    }

    public String getArgumentByIndex(int index){
        if(index >= parameters.size()){
            throw new IllegalArgumentException("out of boundaries");
        }
        return parameters.get(index);
    }

    public abstract void execute(StepByStepExecutor context);

    public abstract boolean isValid();

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder(commandType.toString().toLowerCase());
        for (String parameter : parameters) {
            string.append(" " + parameter);
        }
        return String.valueOf(string);
    }

    public int getNumberOfParameters(){
        return parameters.size();
    }

    public ArrayList<String> getParameters() {
        return parameters;
    }
}
