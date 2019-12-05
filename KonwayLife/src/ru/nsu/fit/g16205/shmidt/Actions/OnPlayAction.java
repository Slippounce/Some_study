package ru.nsu.fit.g16205.shmidt.Actions;

import ru.nsu.fit.g16205.shmidt.konwaylogic.GameField;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OnPlayAction extends AbstractAction{
    private GameField gameField;

    public OnPlayAction(GameField gameField) {
        this.gameField = gameField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameField.setGameStatePlay();
        gameField.setGameModeNoEdit();
    }
}
