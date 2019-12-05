package ru.nsu.fit.g16205.shmidt.Actions;


import ru.nsu.fit.g16205.shmidt.konwaylogic.GameField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OnSaveForm extends JFrame {
    private GameField gameField;

    public OnSaveForm( GameField gameField) {
        this.gameField = gameField;
    }

    public void start(){
        setTitle("Save file form");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - 500)/2, (screenSize.height - 100)/2 , 500,100);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel jPanel = new JPanel(new FlowLayout());
        JLabel jLabelFileName = new JLabel("File name :");
        JTextField jTextFieldFileName = new JTextField(15);
        jPanel.add(jLabelFileName);
        jPanel.add(jTextFieldFileName);
        JPanel jPanelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton jButtonOpen = new JButton("Save");
        JButton jButtonCancel = new JButton("Cancel");
        jButtonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        jButtonOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = jTextFieldFileName.getText();
                File file;
                File dirSave = new File("saved");
                if(dirSave.mkdir()){
                    System.out.println(" directory for saved files was created");
                }else{
                    System.out.println(" directory for saved files was not created");
                }
                file = new File("saved\\"+fileName);

                try {
                    if(file.createNewFile())
                    saveFieldInFile(file);
                    else{throw new IOException();}
                } catch (IOException e1) {
                    System.out.println("an error occured while trying to save to file");
                    e1.printStackTrace();
                }
                dispose();
            }
        });
        jPanelButtons.add(jButtonOpen);
        jPanelButtons.add(jButtonCancel);

        add(jPanel, BorderLayout.NORTH);
        add(jPanelButtons, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void saveFieldInFile(File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(String.valueOf(gameField.getM())+" "+ String.valueOf(gameField.getN())+"\n");
        writer.write(String.valueOf(gameField.getSeparatorLineWidthInPixels()) + "\n");
        writer.write(String.valueOf(gameField.getCellSizeInPixels()) + "\n");
        int aliveNumber = gameField.countAlive();
        writer.write(String.valueOf(aliveNumber) + "\n");
        for(int i = 0; i < gameField.getN() ;i++){
            for(int j = 0 ; j < gameField.getM()-1;  j++){
                if(gameField.getCell(i,j).isAlive()){
                    writer.write(String.valueOf(i) + " " + String.valueOf(j) + "\n");
                }
            }
            if(i%2 == 0){
                if(gameField.getCell(i,gameField.getM()-1).isAlive()){
                    writer.write(String.valueOf(i) + " " + String.valueOf(gameField.getM()-1) + "\n");
                }
            }
        }
        writer.close();
    }
}
