package ru.nsu.fit.g16205.shmidt.task_filter.forms;

import ru.nsu.fit.g16205.shmidt.task_filter.filters.VolumeRenderer;
import ru.nsu.fit.g16205.shmidt.task_filter.view.InitView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GammaCorrectionForm extends JFrame{

    private JButton jButtonApply;
    private JTextField textField;

    public JButton getjButtonApply() {
        return jButtonApply;
    }

    public JTextField getTextField() {
        return textField;
    }

    public GammaCorrectionForm(InitView component, VolumeRenderer volumeRenderer, JFrame parent){
        setLocationRelativeTo(parent);
        textField = new JTextField("1.0",5);
        JLabel label = new JLabel("Gamma :");
        JPanel gammaForm = new JPanel(new FlowLayout());
        gammaForm.add(label);
        gammaForm.add(textField);
        add(gammaForm, BorderLayout.NORTH);

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
//            if(component.getSelectedImagePiece() != null) {
//                component.setFilteredImage(volumeRenderer.getGammaCorrectedImage(component.getSelectedImagePiece(), Double.parseDouble(textField.getText())));
//                component.repaint();
//            }
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
        add(flow, BorderLayout.SOUTH);

        pack();

        setVisible(true);

    }
}
