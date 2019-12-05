package ru.nsu.fit.g16205.shmidt.Actions;

import ru.nsu.fit.g16205.shmidt.konwaylogic.LogicModule;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OnFileOpen extends AbstractAction {
//    private GameField gameField;
    private LogicModule logicModule;
    private JFrame parent;
    private JPanel playingComponent;

    public OnFileOpen(LogicModule logicModule, JFrame parent, JPanel playingComponent) {
//        this.gameField = gameField;
        this.logicModule = logicModule;
        this.parent = parent;
        this.playingComponent = playingComponent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//       FileOpenForm fileOpenForm = new FileOpenForm(gameField,playingComponent);
//       fileOpenForm.start();
        FileUtils fileUtils = new FileUtils();
       File file =  fileUtils.getOpenFileName(parent,".txt","???");
       if(file != null) {
           try {
               logicModule.getGameField().startInitializationWithNewParameters(new FileReader(file));
               logicModule.recountCellsImpact();
               playingComponent.repaint();
           } catch (FileNotFoundException e1) {
               JOptionPane.showMessageDialog(parent, "something baaad");
               e1.printStackTrace();
           }
       }
    }
}
