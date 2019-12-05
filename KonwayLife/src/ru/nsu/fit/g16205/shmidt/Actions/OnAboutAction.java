package ru.nsu.fit.g16205.shmidt.Actions;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OnAboutAction extends AbstractAction {
    private JFrame parent;

    public OnAboutAction(JFrame jFrame){
        parent = jFrame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(parent, "First task called \"Life\"\nCopyright � 2010 Shmidt Maksim, FIT, group 16205", "About program", JOptionPane.INFORMATION_MESSAGE);
    }
}
