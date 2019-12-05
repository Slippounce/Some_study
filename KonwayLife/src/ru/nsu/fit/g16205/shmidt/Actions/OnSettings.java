package ru.nsu.fit.g16205.shmidt.Actions;

import ru.nsu.fit.g16205.shmidt.konwaylogic.GameField;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OnSettings extends AbstractAction {
    private GameField gameField;
    private JPanel fieldFrame;
    private JFrame parent;

    public OnSettings(GameField gameField, JPanel jPanel, JFrame parent) {
        this.gameField = gameField;
        this.fieldFrame = jPanel;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameSettingsForm gameSettingsForm = new GameSettingsForm(gameField,fieldFrame);
        gameSettingsForm.start();
    }
}
