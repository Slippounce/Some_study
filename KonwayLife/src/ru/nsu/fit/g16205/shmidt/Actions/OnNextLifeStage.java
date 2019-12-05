package ru.nsu.fit.g16205.shmidt.Actions;

import ru.nsu.fit.g16205.shmidt.konwaylogic.LogicModule;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OnNextLifeStage extends AbstractAction {
    private JPanel jPanel;
    private LogicModule logicModule;
    public OnNextLifeStage(LogicModule logicModule, JPanel frame) {
        this.logicModule = logicModule;
        this.jPanel = frame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!logicModule.getGameField().isGamePlay()) {
            logicModule.moveToNextStage();
            jPanel.repaint();
        }
    }
}
