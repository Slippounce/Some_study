package ru.nsu.fit.g16205.shmidt.Actions;

import ru.nsu.fit.g16205.shmidt.konwaylogic.GameField;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileOpenForm extends JFrame {
    private GameField gameField;
    private JPanel fieldFrame;
    public FileOpenForm(GameField gameField, JPanel jPanel) throws HeadlessException {
        this.gameField = gameField;
        this.fieldFrame = jPanel;
    }

    public void start(){
        setTitle("Open file form");
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
        JButton jButtonOpen = new JButton("Open");
        JButton jButtonCancel = new JButton("Cancel");
        jButtonCancel.addActionListener(e -> dispose());
        jButtonOpen.addActionListener(e -> {
            String fileName = jTextFieldFileName.getText();
            try {
                FileReader fileReader = new FileReader(fileName);
                gameField.startInitializationWithNewParameters(fileReader);
            } catch (FileNotFoundException e1) {
                System.err.println("File not found");
                JOptionPane.showMessageDialog(jPanel, "File not found", "Error", JOptionPane.INFORMATION_MESSAGE);
            }catch (IllegalArgumentException e2){
                System.err.println("непраивльный формат файла");
                JOptionPane.showMessageDialog(jPanel, "File has inappropriate format", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
            fieldFrame.repaint();
            dispose();
        });
        jPanelButtons.add(jButtonOpen);
        jPanelButtons.add(jButtonCancel);
        add(jPanel, BorderLayout.NORTH);
        add(jPanelButtons, BorderLayout.SOUTH);
        setVisible(true);
    }
}
