package ru.nsu.fit.g16205.shmidt.konwaygraphic;


import ru.nsu.fit.g16205.shmidt.ActionKeeper;

import javax.swing.*;
import java.awt.event.KeyEvent;


public class InitMainWindow extends MainFrame {
    public InitMainWindow(ActionKeeper actionKeeper) {
        super( 800, 600, "Life", actionKeeper);
        actionKeeper.setParentJFrame(this);

        try {
            addSubMenu("File", KeyEvent.VK_F);
            addSubMenu("Help", KeyEvent.VK_H);
            addSubMenu("Game", KeyEvent.VK_G);

            addMenuItem("File/Open", "creates field template from file", KeyEvent.VK_O, "open.png", "onFileOpen");
            addMenuItem("File/Save", "saves current field condition to file", KeyEvent.VK_S, "save.png", "onSave");
            addMenuItem("File/Exit", "Exit application", KeyEvent.VK_X, "Exit.gif", "onExit");
            addMenuItem("Help/About...", "Shows program short description and copyright information", KeyEvent.VK_A, "About.gif", "onAbout");
            addMenuItem("Game/Pause","Suspends life processes", KeyEvent.VK_P, "Pause.png", "onPause");
            addMenuItem("Game/Next stage", "move life to the next stage", KeyEvent.VK_M, "Next.png", "onNextStage");
            addMenuItem("Game/Play", "next life stage every 1 second", KeyEvent.VK_SPACE,"Play.png", "onPlay");
            addMenuItem("Game/Clear", "Clears the field", KeyEvent.VK_C, "clear.png", "onClear");
            addMenuItem("Game/XOR","Turn on XOR mode", KeyEvent.VK_X, "xor.png","onXOR");
            addMenuItem("Game/Replace","Turn on Replace mode", KeyEvent.VK_Y, "replace.png","onReplace");
            addMenuItem("Game/Settings", "game settings", KeyEvent.VK_S, "settings.png","onSettings");
            addMenuItem("Game/Display impacts", "change display impacts option", KeyEvent.VK_D,"display.png", "onDisplayImpacts");

            addToolBarButton("File/Open");
            addToolBarSeparator();
            addToolBarButton("File/Save");
            addToolBarSeparator();
            addToolBarButton("File/Exit");
            addToolBarSeparator();
            addToolBarButton("Help/About...");
            addToolBarSeparator();
            addToolBarButton("Game/Play");
            addToolBarSeparator();
            addToolBarButton("Game/Pause");
            addToolBarSeparator();
            addToolBarButton("Game/Next stage");
            addToolBarSeparator();
            addToolBarButton("Game/Clear");
            addToolBarSeparator();
            addToolBarButton("Game/Display impacts");
            addToolBarSeparator();
            addToolBarButton("Game/XOR");
            addToolBarSeparator();
            addToolBarButton("Game/Replace");
            addToolBarSeparator();
            addToolBarButton("Game/Settings");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        }
    public void onAbout()
    {
        JOptionPane.showMessageDialog(this, "First task called \"Life\"\nCopyright � 2010 Shmidt Maksim, FIT, group 16205", "About program", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * File/Exit - exits application
     */
    public void onExit()
    {
        System.exit(0);
    }



}
