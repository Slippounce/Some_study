package ru.nsk.nsu.shmidt.guuexecutor;

public class StackEntry {
    private String procedureName;
    private int lineNumber;

    public StackEntry(String procedureName, int lineNumber) {
        this.procedureName = procedureName;
        this.lineNumber = lineNumber;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}
