package ru.nsu.fit.g16205.shmidt.Actions;

import ru.nsu.fit.g16205.shmidt.konwaylogic.GameField;
import ru.nsu.fit.g16205.shmidt.konwaylogic.LogicModule;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OnPauseAction extends AbstractAction {
    private GameField gameField;
    private LogicModule logicModule;
    public OnPauseAction(GameField gameField, LogicModule logicModule) {
        this.gameField = gameField;
        this.logicModule = logicModule;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameField.setGameStatePaused();
        gameField.setGameModeXOR();
    }
}
