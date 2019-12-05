package ru.nsu.fit.g16205.shmidt.Actions;

import ru.nsu.fit.g16205.shmidt.konwaygraphic.InitView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OnDisplayImpacts extends AbstractAction {
    private InitView parent;

    public OnDisplayImpacts(JPanel parent) {
        this.parent = (InitView) parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        parent.changeDisplayImpactsOption();
        parent.repaint();
    }
}
