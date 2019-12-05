package ru.nsu.fit.g16205.shmidt.konwaylogic;

public class LogicModule {
    private GameParameters gameParameters;
    private GameField gameField;
    public LogicModule(GameField gameField){
        this.gameField = gameField;
        gameParameters = new GameParameters();
    }

    public GameField getGameField() {
        return gameField;
    }

    public void moveToNextStage(){
//        if(!gameField.isGamePlay()) {
            updateCellsState();
            recountCellsImpact();
//        }
    }

    private void updateCellsState(){
        Cell[][] field = gameField.getField();
        for(int i = 0; i < gameField.getN(); i++){
            for(int j = 0; j < gameField.getM() - 1; j++){
                if(field[i][j].isAlive()){
                    if(field[i][j].getCurrentImpact() > gameParameters.getLifeEnd() || field[i][j].getCurrentImpact() < gameParameters.getLiveBegin()){
                        field[i][j].setCellDead();
                    }
                }else{
                    if(field[i][j].getCurrentImpact() >= gameParameters.getBirthBegin() && field[i][j].getCurrentImpact() <= gameParameters.getBirthEnd()){
                        field[i][j].setCellAlive();
                    }
                }
            }
            if(i%2 == 0){
                if(field[i][gameField.getM()-1].isAlive()){
                    if(field[i][gameField.getM()-1].getCurrentImpact() > gameParameters.getLifeEnd() || field[i][gameField.getM()-1].getCurrentImpact() < gameParameters.getLiveBegin()){
                        field[i][gameField.getM()-1].setCellDead();
                    }
                }else{
                    if(field[i][gameField.getM()-1].getCurrentImpact() >= gameParameters.getBirthBegin() && field[i][gameField.getM()-1].getCurrentImpact() <= gameParameters.getBirthEnd()){
                        field[i][gameField.getM()-1].setCellAlive();
                    }
                }
            }
        }
    }

    public void recountCellsImpact(){
        for(int i = 0; i < gameField.getN(); i++){
            Cell[][] field = gameField.getField();
            for(int j = 0; j < gameField.getM() - 1; j++){
                int firstLevelNeighborsNumber = countFirstLevelNeighbors(i,j);
                int secondLevelNeighborsNumber = countSecondLevelNeighbors(i,j);
                field[i][j].setCurrentImpact(countImpact(firstLevelNeighborsNumber, secondLevelNeighborsNumber));
            }
            if(i%2 == 0){
                int firstLevelNeighborsNumber = countFirstLevelNeighbors(i,gameField.getM()-1);
                int secondLevelNeighborsNumber = countSecondLevelNeighbors(i,gameField.getM()-1);
                field[i][gameField.getM()-1].setCurrentImpact(countImpact(firstLevelNeighborsNumber, secondLevelNeighborsNumber));

            }
        }


    }

    private double countImpact(int fst, int snd){
        return gameParameters.getFstImpct() * fst + gameParameters.getSndImpct() * snd;
    }

    private int countFirstLevelNeighbors(int i, int j){
        Cell[][] field = gameField.getField();
        int tmp = 0;
        if(i%2 == 0){
            if((j-1 >= 0) && field[i][j-1].isAlive()){
                tmp++;
            }
            if((j+1 <= gameField.getM()-1) && field[i][j+1].isAlive()){
                tmp++;
            }
            if((i-1 >= 0) && field[i-1][j].isAlive()){
                tmp++;
            }
            if((i+1 <= gameField.getN()-1) && field[i+1][j].isAlive()){
                tmp++;
            }

            if((i-1 >= 0) && (j-1 >= 0) && field[i-1][j-1].isAlive()){
                tmp++;
            }
            if((i+1 <= gameField.getN()-1) && (j-1 >= 0) && field[i+1][j-1].isAlive()){
                tmp++;
            }
        }else{
            if((j-1 >= 0) && field[i][j-1].isAlive()){
                tmp++;
            }
            if((j+1 <= gameField.getM()-2) && field[i][j+1].isAlive()){
                tmp++;
            }
            if((i-1 >= 0) && field[i-1][j].isAlive()){
                tmp++;
            }
            if((i+1 <= gameField.getN()-1) && field[i+1][j].isAlive()){
                tmp++;
            }

            if((i-1 >= 0) && (j+1 <= gameField.getM()-2) && field[i-1][j+1].isAlive()){
                tmp++;
            }
            if((i+1 <= gameField.getN()-1) && (j+1 <= gameField.getM()-2) && field[i+1][j+1].isAlive()){
                tmp++;
            }
        }
        return tmp;
    }

    private int countSecondLevelNeighbors(int i, int j){
        Cell[][] field = gameField.getField();
        int tmp = 0;
        if((i-2 >= 0) && field[i-2][j].isAlive()){
            tmp++;
        }
        if((i+2 <= gameField.getN()-1) && field[i+2][j].isAlive()){
            tmp++;
        }

        if(i%2 == 0){
            if((i-1 >= 0) && (j+1 <= gameField.getM()-2) && field[i-1][j+1].isAlive()){
                tmp++;
            }
            if((i-1 >= 0) && (j-2 >= 0) && field[i-1][j-2].isAlive()){
                tmp++;
            }
            if((i+1 <= gameField.getN()-1) && (j+1 <= gameField.getM()-2) && field[i+1][j+1].isAlive()){
                tmp++;
            }
            if((i+1 <= gameField.getN()-1) && (j-2 >= 0) && field[i+1][j-2].isAlive()){
                tmp++;
            }
        }else{
            if((i-1 >= 0) && (j+2 <= gameField.getM()-1) && field[i-1][j+2].isAlive()){
                tmp++;
            }
            if((i-1 >= 0) && (j-1 >= 0) && field[i-1][j-1].isAlive() ){
                tmp++;
            }
            if((i+1 <= gameField.getN()-1) && (j+2 <= gameField.getM()-1) && field[i+1][j+2].isAlive()){
                tmp++;
            }
            if((i+1 <= gameField.getN()-1) && (j-1 >= 0) && field[i+1][j-1].isAlive()){
                tmp++;
            }
        }

        return tmp;
    }
}
