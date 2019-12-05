package ru.nsk.nsu.shmidt.guuexecutor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;

public class Main {
    public static void main(String[] args) {

        if(args.length != 1){
            System.err.println("\n" +
                    "Please, pass the source guu code file to the first and only parameter.\n" +
                    "For now programm was stated with template guu code file,\n" +
                    "which you can find in project resources directory \"/resources/template.txt\"");
            Main main = new Main();
            try {
                main.startFromTemplate();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return;
        }
        String guuCodePath = args[0];
        try {
            FileReader fileReader = new FileReader(new File(guuCodePath));
            GuuCodeParser guuCodeParser = new GuuCodeParser();
            ProgramKeeper programKeeper = new ProgramKeeper();
            guuCodeParser.parseGuuCode(fileReader, programKeeper);
            StepByStepExecutor executor = new StepByStepExecutor(programKeeper);
            executor.start();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File not found");
        }catch (IllegalArgumentException error){
            System.err.println(error.getMessage());
        }
        System.out.println("Program has finished");
    }

    public void startFromTemplate() throws URISyntaxException {
        URL url = getClass().getResource("/resources/template.txt");
        File file = new File(url.toURI());
        try {
            FileReader fileReader = new FileReader(file);
            GuuCodeParser guuCodeParser = new GuuCodeParser();
            ProgramKeeper programKeeper = new ProgramKeeper();
            guuCodeParser.parseGuuCode(fileReader, programKeeper);
            StepByStepExecutor executor = new StepByStepExecutor(programKeeper);
            executor.start();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File not found");
        }catch (IllegalArgumentException error){
            System.err.println(error.getMessage());
        }
        System.out.println("Program has finished");
    }
}
