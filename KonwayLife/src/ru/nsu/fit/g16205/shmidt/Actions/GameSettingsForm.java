package ru.nsu.fit.g16205.shmidt.Actions;

import ru.nsu.fit.g16205.shmidt.konwaylogic.GameField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class GameSettingsForm extends JFrame {
   private GameField gameField;
   private JPanel fieldFrame;
    public GameSettingsForm(GameField gameField, JPanel fieldFrame){
        this.gameField = gameField;
        this.fieldFrame = fieldFrame;
    }

    public void start(){

        setTitle("Settings");
        setLayout(new FlowLayout());
        setSize(500,200);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - getWidth())/2, (screenSize.height - getHeight())/2 , getWidth(), getHeight());
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        JLabel jLabelMode = new JLabel("Выберете режим игры:");
        JPanel modeSettings = new JPanel();
        JRadioButton radioButtonModeXOR = new JRadioButton("XOR");
        JRadioButton radioButtonModeReplace = new JRadioButton("Replace");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonModeXOR);
        buttonGroup.add(radioButtonModeReplace);
        modeSettings.add(jLabelMode);
        modeSettings.add(radioButtonModeXOR);
        modeSettings.add(radioButtonModeReplace);
        if(gameField.isGameModeXOR()) {
            radioButtonModeXOR.setSelected(true);
        }
        if(gameField.isGameModeReplace()){
            radioButtonModeReplace.setSelected(true);
        }
        gameField.setGameModeNoEdit();
        add(modeSettings, BorderLayout.NORTH);

        JTextField textFieldCellSize = new JTextField(String.valueOf((gameField.getCellSizeInPixels())), 10);
        JPanel panelCellSize = new JPanel();
        JSlider slider;
        slider = new JSlider(0,100,gameField.getCellSizeInPixels());
        slider.addChangeListener(e -> textFieldCellSize.setText(String.valueOf(slider.getValue())));

        textFieldCellSize.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String typed = textFieldCellSize.getText();
                slider.setValue(0);
                if(!typed.matches("\\d+") || typed.length()>3){
                    return;
                }
                int value = Integer.parseInt(typed);
                slider.setValue(value);
            }
        });
        panelCellSize.add(new JLabel("Cell size"));
        panelCellSize.add(textFieldCellSize);
        panelCellSize.add(slider);
        add(panelCellSize);

        JPanel panelFieldParameters = new JPanel(new GridLayout(3,1,0,0));
        JTextField fieldParameterM = new JTextField(String.valueOf(gameField.getM()), 3);
        JTextField fieldParameterN = new JTextField(String.valueOf(gameField.getN()) , 3);
        JTextField fieldParameterW = new JTextField(String.valueOf(gameField.getSeparatorLineWidthInPixels()) , 3);
        fieldParameterW.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String typed = fieldParameterW.getText();
                if(!typed.matches("\\d+") || typed.length()>2 ){
                    if(typed.length() != 0){
                        fieldParameterW.setText(fieldParameterW.getText().substring(0, fieldParameterW.getText().length() - 1));
                    }
                    return;
                }
                int value = Integer.parseInt(typed);
                if(value <= 0){
                    fieldParameterW.setText("1");
                }
            }
        });
        fieldParameterM.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String typed = fieldParameterM.getText();
                if(!typed.matches("\\d+") || typed.length()>3 ){
                    if(typed.length() != 0){
                        fieldParameterM.setText(fieldParameterM.getText().substring(0, fieldParameterM.getText().length() - 1));
                    }
                    return;
                }
                int value = Integer.parseInt(typed);
                if(value > 200){
                    fieldParameterM.setText("200");
                }
                if(value <= 0){
                    fieldParameterM.setText("1");
                }
            }
        });
        fieldParameterN.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String typed = fieldParameterN.getText();
                if(!typed.matches("\\d+") || typed.length()>3 ){
                    if(typed.length() != 0){
                        fieldParameterN.setText(fieldParameterN.getText().substring(0, fieldParameterN.getText().length() - 1));
                    }
                    return;
                }
                int value = Integer.parseInt(typed);
                if(value > 200){
                    fieldParameterN.setText("200");
                }
                if(value <= 0){
                    fieldParameterN.setText("1");
                }
            }
        });

        panelFieldParameters.add(new JLabel("m : "));
        panelFieldParameters.add(fieldParameterM);
        panelFieldParameters.add(new JLabel("n :"));
        panelFieldParameters.add(fieldParameterN);
        panelFieldParameters.add(new JLabel("w :"));
        panelFieldParameters.add(fieldParameterW);
        add(panelFieldParameters, BorderLayout.EAST);

        JPanel settingsSetButtons = new JPanel(new GridLayout(1 ,2,5,0));
        JButton jbuttonCancelSettings = new JButton("Cancel");
        jbuttonCancelSettings.addActionListener(e -> {
            if(radioButtonModeXOR.isSelected()){
                gameField.setGameModeXOR();
            }
            if(radioButtonModeReplace.isSelected()){
                gameField.setGameModeReplace();
            }
            dispose();
        });
        JButton jButtonSetSettings = new JButton("OK!");
        jButtonSetSettings.addActionListener((ActionEvent e) -> {
            if(radioButtonModeXOR.isSelected()){
                gameField.setGameModeXOR();
            }
            if(radioButtonModeReplace.isSelected()){
                gameField.setGameModeReplace();
            }
            if(textFieldCellSize.getText().length() != 0) {
                int newCellSize = Integer.parseInt(textFieldCellSize.getText());
                if (newCellSize > 0 && newCellSize <= 100) {
                    if (newCellSize > Integer.parseInt(fieldParameterW.getText())) {
                        gameField.setCellSizeInPixels(newCellSize);
                    } else {
                        gameField.setCellSizeInPixels(Integer.parseInt(fieldParameterW.getText()) + 1);
                    }
                }
            }
            String typed = fieldParameterM.getText();
            int typedParameterM = gameField.getM(), typedParameterN = gameField.getN();
            if(typed.length() != 0){
                typedParameterM = Integer.parseInt(typed);
            }
            typed = fieldParameterN.getText();
            if(typed.length() != 0){
                typedParameterN = Integer.parseInt(typed);
            }
            gameField.setFieldSize(typedParameterM, typedParameterN);
            typed = fieldParameterW.getText();
            if(typed.length() != 0){
                gameField.setSeparatorLineWidthInPixels(Integer.parseInt(typed));
            }
            fieldFrame.repaint();
            dispose();

        });
        settingsSetButtons.add(jbuttonCancelSettings);
        settingsSetButtons.add(jButtonSetSettings);
        JPanel setButtonsFlowPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        setButtonsFlowPanel.add(settingsSetButtons);
        add(setButtonsFlowPanel, BorderLayout.SOUTH);

        JTextField jTextFieldLinesCount = new JTextField();
        jTextFieldLinesCount.setMaximumSize(new Dimension(100, 20));
        setVisible(true);
    }
}
