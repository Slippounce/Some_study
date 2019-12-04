package ru.nsu.fit.g16205.shmidt.task_filter.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TripleSliderForm extends JFrame {

    private JButton jButtonApply;
    private EditSliderBoxPanel editSliderBoxPanel1;
    private EditSliderBoxPanel editSliderBoxPanel2;
    private EditSliderBoxPanel editSliderBoxPanel3;

    public JButton getjButtonApply() {
        return jButtonApply;
    }

    public EditSliderBoxPanel getEditSliderBoxPanel1() {
        return editSliderBoxPanel1;
    }

    public EditSliderBoxPanel getEditSliderBoxPanel2() {
        return editSliderBoxPanel2;
    }

    public EditSliderBoxPanel getEditSliderBoxPanel3() {
        return editSliderBoxPanel3;
    }

    public TripleSliderForm(JFrame parent, String name1, String name2, String name3, int bottomLine, int upperLine, int defaultValue){
        setLayout(new FlowLayout());
        parent.setEnabled(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE/*DO_NOTHING_ON_CLOSE*/);
        editSliderBoxPanel1 = new EditSliderBoxPanel(name1, bottomLine,upperLine, defaultValue);
        editSliderBoxPanel2 = new EditSliderBoxPanel(name2, bottomLine,upperLine, defaultValue);
        editSliderBoxPanel3 = new EditSliderBoxPanel(name3, bottomLine,upperLine, defaultValue);

        addWindowListener(new WindowAdapter() {
                              @Override
                              public void windowClosing(WindowEvent e) {
                                  super.windowClosing(e);
                                  parent.setEnabled(true);
                              }

                          }
        );


        jButtonApply = new JButton("Ok");
        JButton jButtonCancel = new JButton("Cancel");

        jButtonApply.addActionListener(e -> {
            parent.setEnabled(true);
            dispose();
        });
        jButtonCancel.addActionListener(e -> {
            parent.setEnabled(true);
            dispose();
        });

        JPanel test = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        constraints.gridy = 0;
        constraints.gridx = 0;
        test.add(editSliderBoxPanel1, constraints);
        constraints.gridy = 1;
        test.add(editSliderBoxPanel2, constraints);
        constraints.gridy = 2;
        test.add(editSliderBoxPanel3, constraints);


        JPanel flow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        flow.add(jButtonCancel);
        flow.add(jButtonApply);

        add(test);

        add(flow, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }
}
