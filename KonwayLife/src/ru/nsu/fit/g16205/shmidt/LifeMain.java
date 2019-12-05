package ru.nsu.fit.g16205.shmidt;

import ru.nsu.fit.g16205.shmidt.konwaygraphic.InitMainWindow;
import ru.nsu.fit.g16205.shmidt.konwaygraphic.InitView;
import ru.nsu.fit.g16205.shmidt.konwaylogic.GameField;
import ru.nsu.fit.g16205.shmidt.konwaylogic.LogicModule;

import javax.swing.*;
import java.awt.*;

public class LifeMain {

    public static void main(String[] args) {
        GameField gameField = new GameField();
        LogicModule logicModule = new LogicModule(gameField);
        InitView graphicsComponent = new InitView(gameField,logicModule);
        InitMainWindow mainFrame = new InitMainWindow(new ActionKeeper(gameField, logicModule, graphicsComponent));
        Timer timerJ = new Timer(1000, e -> {
            logicModule.moveToNextStage();
            graphicsComponent.repaint();
        });
        graphicsComponent.setPreferredSize(new Dimension(800,600));
        JScrollPane panelPane = new JScrollPane(graphicsComponent);
        mainFrame.getContentPane().add(panelPane);
        mainFrame.setVisible(true);
//        FileUtils f = new FileUtils();
//        f.getSaveFileName(mainFrame, ".txt", "textfile");


        final boolean CONTINUE_GAME = true;
//        GameState currentState = gameField.getGameState();
        while(CONTINUE_GAME){
//            if(currentState != gameField.getGameState()) {
//                System.out.println("state changed " + currentState);
//                currentState = gameField.getGameState();
                if(gameField.isGamePlay()){
                    if(gameField.isFieldDead()){
                        gameField.setGameStatePaused();
                        gameField.setGameModeXOR();
                    }else{
                        timerJ.start();
                    }

                }
                if(gameField.isGamePaused()){
                    timerJ.stop();
                }
                //}
        }
    }
}
