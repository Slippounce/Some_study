package ru.nsk.nsu.shmidt.guuexecutor;

import ru.nsk.nsu.shmidt.guuexecutor.commands.Command;

import java.util.ArrayList;

public class ProcedureDescription {
    private int lineNumber;
    private ArrayList<Command> commands;
    public ProcedureDescription(int lineNumber){
        this.lineNumber = lineNumber;
        commands = new ArrayList<>();
    }

    public void addCommand(Command command){
        commands.add(command);
    }

    public int getLineNumber(){
        return this.lineNumber;
    }

    public int getCommandNumber(){
        return commands.size();
    }

    public Command getCommandByIndex(int index){
        if(index >= commands.size()){
            throw new IllegalArgumentException();
        }
        if(commands.size() == 0){
            throw new IllegalArgumentException();
        }
        return commands.get(index);
    }
}
