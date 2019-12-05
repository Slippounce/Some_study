package ru.nsu.fit.g16205.shmidt;

import ru.nsu.fit.g16205.shmidt.Actions.*;
import ru.nsu.fit.g16205.shmidt.konwaygraphic.InitView;
import ru.nsu.fit.g16205.shmidt.konwaylogic.GameField;
import ru.nsu.fit.g16205.shmidt.konwaylogic.LogicModule;

import javax.swing.*;
import java.util.HashMap;

public class ActionKeeper {
    private HashMap<String, AbstractAction> actions;
    private JFrame parent;
    private GameField gameField;
    public ActionKeeper(GameField gameField, LogicModule logicModule, InitView jPanel){
        this.gameField = gameField;
        actions = new HashMap<>();
        actions.put("onExit", new ExitAction());
        actions.put("onAbout", new OnAboutAction(parent));
        actions.put("onPause", new OnPauseAction(this.gameField, logicModule));
        actions.put("onNextStage", new OnNextLifeStage(logicModule, jPanel));
        actions.put("onPlay", new OnPlayAction(gameField));
        actions.put("onClear", new OnClear(logicModule, jPanel));
        actions.put("onXOR", new OnXOR(gameField));
        actions.put("onReplace", new OnReplace(gameField));
        actions.put("onSettings", new OnSettings(gameField,jPanel,parent));
        actions.put("onFileOpen", new OnFileOpen(logicModule, parent, jPanel));
        actions.put("onSave", new OnSave(gameField, parent));
        actions.put("onDisplayImpacts", new OnDisplayImpacts(jPanel));
    }
    public Action getAction(String action) {
        return actions.get(action);
    }

    public void setParentJFrame(JFrame parent){
        this.parent = parent;
    }

    public boolean isSuchActionExists(String action){
        return actions.containsKey(action);
    }
}
