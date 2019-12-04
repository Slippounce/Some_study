package ru.nsu.fit.g16205.shmidt.task_filter.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SliderForm extends JFrame {
   private JButton jButtonApply;
    private EditSliderBoxPanel editSliderBoxPanel;
    public JButton getjButtonApply() {
        return jButtonApply;
    }

    public EditSliderBoxPanel getEditSliderBoxPanel() {
        return editSliderBoxPanel;
    }

    public SliderForm(JFrame parent, String name, int defaultValue, int bottomLineForSlider, int upperLineForSlider){
        setLocationRelativeTo(parent);
        editSliderBoxPanel = new EditSliderBoxPanel(name, bottomLineForSlider,upperLineForSlider);
        editSliderBoxPanel.setValue(defaultValue);
        jButtonApply = new JButton("Ok");
        JButton jButtonCancel = new JButton("Cancel");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        parent.setEnabled(false);

        addWindowListener(new WindowAdapter() {
                              @Override
                              public void windowClosing(WindowEvent e) {
                                  super.windowClosing(e);
                                  parent.setEnabled(true);
                              }

                          }
        );

        jButtonApply.addActionListener(e -> {
            parent.setEnabled(true);
            dispose();
        });
        jButtonCancel.addActionListener(e -> {
            parent.setEnabled(true);
            dispose();
        });


        JPanel flow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        flow.add(jButtonCancel);
        flow.add(jButtonApply);

        add(editSliderBoxPanel);
        add(flow, BorderLayout.SOUTH);
        pack();
        setVisible(true);

    }

}
