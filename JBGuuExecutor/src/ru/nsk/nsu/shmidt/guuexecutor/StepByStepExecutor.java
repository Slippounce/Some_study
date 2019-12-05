package ru.nsk.nsu.shmidt.guuexecutor;

import ru.nsk.nsu.shmidt.guuexecutor.commands.Command;
import ru.nsk.nsu.shmidt.guuexecutor.commands.CommandTypes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

public class StepByStepExecutor {
    private ProgramKeeper programKeeper;
    private Stack<StackEntry> stackTrace;
    private HashMap<String, Integer> variables;
    private int commandNumberInCurrentProcedure;
    private int numberOfLastCommandInCurrentProcedure;
    private boolean isProgramEnded = false;
    public StepByStepExecutor(ProgramKeeper programKeeper){
        this.programKeeper = programKeeper;
        commandNumberInCurrentProcedure = 0;
        numberOfLastCommandInCurrentProcedure = programKeeper.getProcedureDescription("main").getCommandNumber() - 1;
        stackTrace = new Stack<>();
        variables = new HashMap<>();
    }

    public void start(){
        stackTrace.push(new StackEntry("main", programKeeper.getProcedureDescription("main").getLineNumber()));
//        printHelp();
        printUsage();
        printState();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(!isProgramEnded){
            try{
                if(reader.ready()){
                    String clientMessage = reader.readLine();
                    if(clientMessage.equalsIgnoreCase("quit")){
                        System.out.println("Initiate closing program...");
                        break;
                    }
                    switch (clientMessage){
                        case "i":{
                            doStepForward(true);
                            break;
                        }
                        case "o":{
                            doStepForward(false);
                            break;
                        }
                        case "trace":{
                            printStackTrace();
                            break;
                        }
                        case "var":{
                            printAllVariables();
                            break;
                        }
                        default: printUsage();
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void printState(){
        if(!isProgramEnded) {
            StackEntry lastEntry = stackTrace.peek();
            ProcedureDescription currentProcedureDescription = programKeeper.getProcedureDescription(lastEntry.getProcedureName());
            Command command = currentProcedureDescription.getCommandByIndex(commandNumberInCurrentProcedure);
            System.out.println(lastEntry.getProcedureName()+"(line:"+(programKeeper.getProcedureDescription(lastEntry.getProcedureName()).getCommandByIndex(commandNumberInCurrentProcedure).getLineNumber())+") -> " + command.toString()+"\n");
        }
    }

    private void printAllVariables(){
        if(variables.size() > 0) {
            System.out.println("List of defined variables : ");
            for (HashMap.Entry<String, Integer> pair : variables.entrySet()) {
                System.out.println(pair.getKey() + " = " + pair.getValue());
            }
        }else{
            System.out.println("No variables defined yet!");
        }
        System.out.println();
    }

    private void printStackTrace(){
        Iterator<StackEntry> iterator = stackTrace.iterator();
        System.out.println("Current stacktrace(in depth):");
        while(iterator.hasNext()){
            StackEntry entry = iterator.next();
            System.out.println(entry.getProcedureName()+"(line:"+programKeeper.getProcedureDescription(entry.getProcedureName()).getLineNumber()+")");
        }
        System.out.println();
    }

    private void doStepForward(boolean isStepInto){
        StackEntry lastEntry = stackTrace.peek();
        ProcedureDescription currentProcedureDescription = programKeeper.getProcedureDescription(lastEntry.getProcedureName());
        Command command = currentProcedureDescription.getCommandByIndex(commandNumberInCurrentProcedure);
        if(command.getCommandType() != CommandTypes.CALL || isStepInto) {
            command.execute(this);
            moveToNextCommand();
            printState();
        }else{
            System.out.println("to move across command \"call\" use \"step into\" by typing \"i\"");
        }
    }

    private void moveToNextCommand(){
        commandNumberInCurrentProcedure++;
        if(commandNumberInCurrentProcedure > numberOfLastCommandInCurrentProcedure) {
            while (commandNumberInCurrentProcedure > numberOfLastCommandInCurrentProcedure) {
                int lineNumber = stackTrace.pop().getLineNumber();
                if (stackTrace.size() == 0) {
                    isProgramEnded = true;
                    return;
                }
                commandNumberInCurrentProcedure = lineNumber - stackTrace.peek().getLineNumber();
                numberOfLastCommandInCurrentProcedure = programKeeper.getProcedureDescription(stackTrace.peek().getProcedureName()).getCommandNumber() - 1;
            }
        }

    }

    private void printUsage(){
        System.out.println("\n" +
                "USAGE\n" +
                "type following commands in console to control debugger:\n" +
                "\"i\" - step into\n" +
                "\"o\" - step over\n" +
                "\"trace\" - print current stack trace\n" +
                "\"var\" - print values of all defined variables\n" +
                "\"quit\" - finishing program\n");
    }

    public HashMap<String, Integer> getVariables() {
        return variables;
    }

    public int getVarValueByName(String name){
        Integer value = variables.get(name);
        if(value == null){
            throw new IllegalArgumentException("variable \""+name+"\" wasn't define in this scope");
        }
        return value;
    }

    public Stack<StackEntry> getStackTrace() {
        return stackTrace;
    }

    public ProgramKeeper getProgramKeeper() {
        return programKeeper;
    }

    public void setCommandNumberInCurrentProcedure(int commandNumberInCurrentProcedure) {
        this.commandNumberInCurrentProcedure = commandNumberInCurrentProcedure;
    }

    public void setNumberOfLastCommandInCurrentProcedure(int numberOfLastCommandInCurrentProcedure) {
        this.numberOfLastCommandInCurrentProcedure = numberOfLastCommandInCurrentProcedure;
    }

    public int getCommandNumberInCurrentProcedure() {
        return commandNumberInCurrentProcedure;
    }

    public int getNumberOfLastCommandInCurrentProcedure() {
        return numberOfLastCommandInCurrentProcedure;
    }
}
