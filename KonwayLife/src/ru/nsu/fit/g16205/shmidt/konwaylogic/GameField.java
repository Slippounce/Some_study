package ru.nsu.fit.g16205.shmidt.konwaylogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.StringTokenizer;

public class GameField {
    private Cell[][] field;
    private int separatorLineWidthInPixels;
    private int cellSizeInPixels;
    private int m;
    private int n;
    private GameState gameState;
    private GameMode gameMode;


    //region Getters

    public boolean isFieldDead(){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m-1; j++){
                if(field[i][j].isAlive()){
                    return false;
                }
            }
            if(i%2 ==0){
                if(field[i][m-1].isAlive()){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isGameModeNoEdit(){
        return gameMode == GameMode.NOEDIT;
    }

    public boolean isGamePaused() {
        return gameState == GameState.PAUSE;
    }

    public boolean isGamePlay() {
        return gameState == GameState.PLAY;
    }

    public Cell getCell(int i, int j){
        if(i<0 || j<0 || i>=n || j>=m){
            return null;
        }
        return field[i][j];
    }

    public boolean isGameModeXOR() {
        return gameMode == GameMode.XOR;
    }
    public boolean isGameModeReplace() {
        return gameMode == GameMode.REPLACE;
    }

    public Cell[][] getField() {
        return field;
    }

    public int getSeparatorLineWidthInPixels() {
        return separatorLineWidthInPixels;
    }

    public int getCellSizeInPixels() {
        return cellSizeInPixels;
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }
    //endregion

    //region Setters
    public void setSeparatorLineWidthInPixels(int separatorLineWidthInPixels) {
        this.separatorLineWidthInPixels = separatorLineWidthInPixels;
    }

    public void setCellSizeInPixels(int cellSizeInPixels) {
        this.cellSizeInPixels = cellSizeInPixels;
    }

    public void setFieldSize(int m, int n){
        if(m != this.m || n!=this.n) {
            Cell[][] resizedField = new Cell[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    resizedField[i][j] = new Cell();
                    if(i < this.n && j < this.m && field[i][j].isAlive()){
                        resizedField[i][j].setCellAlive();
                    }
                }
            }
            this.m = m;
            this.n = n;
            field = resizedField;
        }
    }

    public void setGameModeNoEdit(){
        gameMode = GameMode.NOEDIT;
    }

    public void setGameModeXOR(){
        gameMode = GameMode.XOR;
    }

    public void setGameModeReplace(){
        gameMode = GameMode.REPLACE;
    }

    public void setGameStatePaused(){
        gameState = GameState.PAUSE;
    }

    public void setGameStatePlay(){
        gameState = GameState.PLAY;
    }
    //endregion

    public GameField(){
        m = 30;
        n = 30;
        separatorLineWidthInPixels = 2;
        cellSizeInPixels = 20;
        gameMode = GameMode.XOR;
        gameState = GameState.PAUSE;
        field = new Cell[n][m];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                field[i][j] = new Cell();
            }
        }
    }

    public void clearField(){
        for(int i = 0; i < n;i++){
            for(int j = 0 ; j < m-1;  j++){
                field[i][j].setCellDead();
            }
            if(i%2 == 0){
                field[i][m-1].setCellDead();
            }
        }
    }

    public void changeLifeConditionInCertainCell(int i, int j){
        if(i < 0 || i >= n || j < 0 || (j >= m && i%2 == 0) || (j>=m-1 && i%2!=0)){
            throw new IllegalArgumentException();
        }
        if (field[i][j].isAlive()){
            field[i][j].setCellDead();
        }else{
            field[i][j].setCellAlive();
        }
    }

    public void startInitializationWithNewParameters(Reader reader){
        gameState = GameState.PAUSE;
        gameMode = GameMode.REPLACE;

        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            String readed = bufferedReader.readLine();
            StringTokenizer tokenizer = new StringTokenizer(readed);
            if(tokenizer.countTokens() < 2){
                throw new IllegalArgumentException("invalid input information");
            }
            int m = Integer.parseInt(tokenizer.nextToken());
            if(m <= 0 || m >= 1000){
                throw new IllegalArgumentException("invalid input information");
            }
            int n = Integer.parseInt(tokenizer.nextToken());
            if(n <= 0 || n >= 1000){
                throw new IllegalArgumentException("invalid input information");
            }
            checkLine(tokenizer);

            Cell[][] templateField = new Cell[n][m];
            for(int i = 0; i < n; i++){
                for(int j = 0; j < m; j++){
                    templateField[i][j] = new Cell();
                }
            }
            readed = bufferedReader.readLine();
            tokenizer = new StringTokenizer(readed);
            if(tokenizer.countTokens() == 0){
                throw new IllegalArgumentException();
            }
            int separatorLineWidthInPixels = Integer.parseInt(tokenizer.nextToken());
            if(separatorLineWidthInPixels <= 0 || separatorLineWidthInPixels >= 200){
                throw new IllegalArgumentException("invalid input information");
            }
            checkLine(tokenizer);

            readed = bufferedReader.readLine();
            tokenizer = new StringTokenizer(readed);
            int cellSizeInPixels = Integer.parseInt(tokenizer.nextToken());
            if(cellSizeInPixels <= 0 || cellSizeInPixels >= 1000){
                throw new IllegalArgumentException("invalid input information");
            }
            checkLine(tokenizer);

            tokenizer = new StringTokenizer(bufferedReader.readLine());
            int all = Integer.parseInt(tokenizer.nextToken());
            if(all <= 0 || all > m*n){
                throw new IllegalArgumentException("invalid input information");
            }
            checkLine(tokenizer);

            for(int i = 0; i < all; i++){
                tokenizer = new StringTokenizer(bufferedReader.readLine());
                int m1 = Integer.parseInt(tokenizer.nextToken());
                int n1 = Integer.parseInt(tokenizer.nextToken());
                if(m1 <= 0 || m1 >= m || n1 <= 0 || n1 >= n){
                    throw new IllegalArgumentException("invalid input information");
                }
                checkLine(tokenizer);
                templateField[m1][n1].setCellAlive();
            }
            this.field = templateField;
            this.m = m;
            this.n = n;
            this.separatorLineWidthInPixels = separatorLineWidthInPixels;
            this.cellSizeInPixels = cellSizeInPixels;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkLine(StringTokenizer tokenizer){
        if(tokenizer.hasMoreTokens()){
            String nextToken = tokenizer.nextToken();
            if(nextToken.codePointAt(0) != '/' || nextToken.codePointAt(0) != '/'){
                throw new IllegalArgumentException("invalid input information");
            }
        }
    }

    public int countAlive(){
        int counter = 0;
        for(int i = 0; i < n;i++){
            for(int j = 0 ; j < m-1;  j++){
                if(field[i][j].isAlive()){
                    counter++;
                }
            }
            if(i%2 == 0){
                if(field[i][m-1].isAlive()){
                    counter++;
                }
            }
        }
        return counter;
    }

}
