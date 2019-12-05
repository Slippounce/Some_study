package ru.nsu.fit.g16205.shmidt.Actions;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ExitAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
