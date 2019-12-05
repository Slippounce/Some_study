package ru.nsk.nsu.shmidt.guuexecutor;

import ru.nsk.nsu.shmidt.guuexecutor.commands.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.StringTokenizer;

public class GuuCodeParser {
    public void parseGuuCode(Reader reader, ProgramKeeper keeper){
        BufferedReader bufferedReader = new BufferedReader(reader);
        String readLine;
        int lineNumber = 0;
        String currentProcedureName = null;
        ProcedureDescription currentProcedureDescription = null;
        try{
            while((readLine = bufferedReader.readLine()) != null){
                try {
                    lineNumber++;
                    Command command = parseLineToCommand(readLine);
                    command.setLineNumber(lineNumber);
                    if (command.getCommandType() == CommandTypes.SUB) {
                        if (currentProcedureDescription != null) {
                            keeper.putProcedureDescription(currentProcedureName, currentProcedureDescription);
                        }
                        currentProcedureName = command.getArgumentByIndex(0);
                        currentProcedureDescription = new ProcedureDescription(lineNumber);
                    } else {
                        if (currentProcedureDescription != null) {
                            currentProcedureDescription.addCommand(command);
                        }
                    }
                }catch (EmptyLineException ignored){}
            }
            if (currentProcedureDescription != null) {
                keeper.putProcedureDescription(currentProcedureName, currentProcedureDescription);
            }
            if(!keeper.isContains("main")){
                throw new IllegalArgumentException("guu source file haven't procedure \"main\" to start from!");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private Command parseLineToCommand(String line) throws EmptyLineException {
        StringTokenizer tokenizer = new StringTokenizer(line);
        if(tokenizer.countTokens() < 1){
            throw new EmptyLineException("empty command line");
        }
        Command command = null;
        String token = tokenizer.nextToken();
        if(token.equalsIgnoreCase("set")){
               command = new CommandSet();
        }
        if(token.equalsIgnoreCase("call")){
            command = new CommandCall();
        }
        if(token.equalsIgnoreCase("print")){
            command = new CommandPrint();
        }
        if(token.equalsIgnoreCase("sub")){
            command = new CommandSub();
        }

        if(command == null){
            throw new IllegalArgumentException("invalid command name");
        }

        while(tokenizer.hasMoreTokens()){
            command.addParameter(tokenizer.nextToken());
        }

        if(!command.isValid()){
            throw new IllegalArgumentException("invalid source code");
        }
        return command;
    }
}
