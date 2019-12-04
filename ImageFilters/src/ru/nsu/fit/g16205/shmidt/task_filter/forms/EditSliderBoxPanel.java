package ru.nsu.fit.g16205.shmidt.task_filter.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EditSliderBoxPanel extends JPanel {
    private JSlider slider;
    private JTextField textField;
    EditSliderBoxPanel(String label, int bottomLine, int upperLine){
        setLayout(new FlowLayout());
        add(new Label(label));
        textField = new JTextField(String.valueOf(bottomLine),10);
        slider = new JSlider(bottomLine,upperLine, bottomLine);
        slider.addChangeListener( e -> textField.setText(String.valueOf(slider.getValue())));
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String typed = textField.getText();
                if(!typed.matches("\\d+") || typed.length() == 0){
                    typed = String.valueOf(slider.getMinimum());
                    textField.setText(typed);
                }
                if(typed.length() > String.valueOf(slider.getMaximum()).length()){
                    typed = String.valueOf(slider.getMaximum());
                    textField.setText(typed);
                }
                int value = Integer.parseInt(typed);
                if(value < slider.getMinimum() ){
                    textField.setText(String.valueOf(slider.getMinimum()));
                    value = slider.getMinimum();
                }
                if(value > slider.getMaximum()){
                    textField.setText(String.valueOf(slider.getMaximum()));
                    value = slider.getMaximum();
                }
                slider.setValue(value);
            }
        });
        add(textField);
        add(slider);

    }

    EditSliderBoxPanel(String label, int bottomLine, int upperLine, int defaultValue){
        setLayout(new FlowLayout());
        add(new Label(label));
        textField = new JTextField(String.valueOf(defaultValue),10);
        slider = new JSlider(bottomLine,upperLine, defaultValue);
        slider.addChangeListener( e -> textField.setText(String.valueOf(slider.getValue())));
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String typed = textField.getText();
                if(!typed.matches("\\d+") || typed.length() == 0){
                    typed = String.valueOf(slider.getMinimum());
                    textField.setText(typed);
                }
                if(typed.length() > String.valueOf(slider.getMaximum()).length()){
                    typed = String.valueOf(slider.getMaximum());
                    textField.setText(typed);
                }
                int value = Integer.parseInt(typed);
                if(value < slider.getMinimum() ){
                    textField.setText(String.valueOf(slider.getMinimum()));
                    value = slider.getMinimum();
                }
                if(value > slider.getMaximum()){
                    textField.setText(String.valueOf(slider.getMaximum()));
                    value = slider.getMaximum();
                }
                slider.setValue(value);
            }
        });
        add(textField);
        add(slider);

    }

    public int getValue() {
        return slider.getValue();
    }
    public void setValue(int value){
        //check
        slider.setValue(value);
    }
}
