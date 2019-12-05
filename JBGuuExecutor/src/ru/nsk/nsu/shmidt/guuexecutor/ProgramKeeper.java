package ru.nsk.nsu.shmidt.guuexecutor;

import java.util.HashMap;

public class ProgramKeeper {
    private HashMap<String, ProcedureDescription> procedureDescriptionsMap;
    ProgramKeeper(){
        procedureDescriptionsMap = new HashMap<>();
    }

    public ProcedureDescription getProcedureDescription(String procedureName){
        ProcedureDescription foundDescription = procedureDescriptionsMap.get(procedureName);
        if(foundDescription == null){
            throw new IllegalArgumentException("There isn't such procedure");
        }
        return foundDescription;
    }

    public boolean isContains(String procedureName){
        return procedureDescriptionsMap.containsKey(procedureName);
    }

    public void putProcedureDescription(String procedureName, ProcedureDescription procedureDescription){
        if(procedureDescriptionsMap.containsKey(procedureName)){
            throw new IllegalArgumentException("procedure "+procedureName+" was already defined");
        }
        procedureDescriptionsMap.put(procedureName, procedureDescription);
    }

}
