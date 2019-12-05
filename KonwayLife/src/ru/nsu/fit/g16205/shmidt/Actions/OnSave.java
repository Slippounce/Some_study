package ru.nsu.fit.g16205.shmidt.Actions;

import ru.nsu.fit.g16205.shmidt.konwaylogic.GameField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OnSave extends AbstractAction {
    private GameField gameField;
    private JFrame parent;
    public OnSave(GameField gameField, JFrame parent) {
        this.gameField = gameField; this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        OnSaveForm form = new OnSaveForm(gameField);
//        form.start();
        FileUtils fileUtils = new FileUtils();
        File file =  fileUtils.getSaveFileName(parent,".txt","fv");
//        file.
        if(file != null) {
            try {
                saveFieldInFile(file);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    private void saveFieldInFile(File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(String.valueOf(gameField.getM())+" "+ String.valueOf(gameField.getN())+"\n");
        writer.write(String.valueOf(gameField.getSeparatorLineWidthInPixels()) + "\n");
        writer.write(String.valueOf(gameField.getCellSizeInPixels()) + "\n");
        int aliveNumber = gameField.countAlive();
        writer.write(String.valueOf(aliveNumber) + "\n");
        for(int i = 0; i < gameField.getN() ;i++){
            for(int j = 0 ; j < gameField.getM()-1;  j++){
                if(gameField.getCell(i,j).isAlive()){
                    writer.write(String.valueOf(i) + " " + String.valueOf(j) + "\n");
                }
            }
            if(i%2 == 0){
                if(gameField.getCell(i,gameField.getM()-1).isAlive()){
                    writer.write(String.valueOf(i) + " " + String.valueOf(gameField.getM()-1) + "\n");
                }
            }
        }
        writer.close();
    }
}
