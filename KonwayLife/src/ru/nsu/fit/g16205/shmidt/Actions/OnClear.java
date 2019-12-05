package ru.nsu.fit.g16205.shmidt.Actions;

import ru.nsu.fit.g16205.shmidt.konwaylogic.LogicModule;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OnClear extends AbstractAction {
//    private GameField gameField;
    private JPanel jPanel;
    private LogicModule logicModule;
    public OnClear(LogicModule module, JPanel jPanel) {
//        this.gameField = gameField;
        this.logicModule = module;
        this.jPanel = jPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!logicModule.getGameField().isGameModeNoEdit()) {
            logicModule.getGameField().clearField();
            logicModule.recountCellsImpact();
            jPanel.repaint();
        }
    }
}
