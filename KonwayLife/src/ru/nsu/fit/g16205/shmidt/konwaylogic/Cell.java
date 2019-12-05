package ru.nsu.fit.g16205.shmidt.konwaylogic;

public class Cell {
    private double currentImpact;
    private CellState currentCellCondition;

    public Cell(){
        currentImpact = 0;
        currentCellCondition = CellState.DEAD;
    }

    //region Getters
    public double getCurrentImpact() {
        return currentImpact;
    }

    public boolean isAlive() {
        return currentCellCondition == CellState.ALIVE;
    }
    //endregion

    //region Setters

    public void setCellAlive() {
        this.currentCellCondition = CellState.ALIVE;
    }

    public void setCellDead() {
        this.currentCellCondition = CellState.DEAD;
    }

    public void setCurrentImpact(double currentImpact) {
        this.currentImpact = currentImpact;
    }
    //endregion
}
