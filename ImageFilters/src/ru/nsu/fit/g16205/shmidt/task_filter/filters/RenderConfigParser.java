package ru.nsu.fit.g16205.shmidt.task_filter.filters;

import ru.nsu.fit.g16205.shmidt.task_filter.view.InitView;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class RenderConfigParser {
    private ArrayList<Point> graphicAbsorptionPoints = new ArrayList<>();
    private ArrayList<Point> graphicEmissionPointsRed = new ArrayList<>();
    private ArrayList<Point> graphicEmissionPointsGreen = new ArrayList<>();
    private ArrayList<Point> graphicEmissionPointsBlue = new ArrayList<>();
    private HashMap<MyPoint, Integer> chargeElementsMap = new HashMap<>();

    public class MyPoint{
        public int x;
        public int y;
        public int z;

        public MyPoint(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public ArrayList<Point> getGraphicAbsorptionPoints() {
        return graphicAbsorptionPoints;
    }

    public ArrayList<Point> getGraphicEmissionPointsRed() {
        return graphicEmissionPointsRed;
    }

    public ArrayList<Point> getGraphicEmissionPointsGreen() {
        return graphicEmissionPointsGreen;
    }

    public ArrayList<Point> getGraphicEmissionPointsBlue() {
        return graphicEmissionPointsBlue;
    }

    public HashMap<MyPoint, Integer> getChargeElementsMap() {
        return chargeElementsMap;
    }

    public RenderConfigParser(){}

    public void readGraphics(File file) throws FileNotFoundException, NumberFormatException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        try {
            String readed = reader.readLine();

            StringTokenizer tokenizer = new StringTokenizer(readed);
            if(tokenizer.countTokens() < 1){
                throw new IllegalArgumentException("invalid input information");
            }
            int pointsNumber = Integer.parseInt(tokenizer.nextToken());

            for(int i = 0; i < pointsNumber; i++){
                readed = reader.readLine();
                tokenizer = new StringTokenizer(readed);
                if(tokenizer.countTokens() < 2){
                    throw new IllegalArgumentException("invalid input information");
                }
                int m = Integer.parseInt(tokenizer.nextToken());
                double t = Double.parseDouble(tokenizer.nextToken());
                if(t < 0 || t > 1 || m < 0 || m > 100){
                    throw new IllegalArgumentException("invalid input information");
                }
                checkLine(tokenizer);
                graphicAbsorptionPoints.add(new Point(m*4, (int) (t*100)));
            }
            while ((readed = reader.readLine()).length() == 0) { }
            tokenizer = new StringTokenizer(readed);

            if(tokenizer.countTokens() < 1){
                throw new IllegalArgumentException("invalid input information");
            }
            pointsNumber = Integer.parseInt(tokenizer.nextToken());
            for(int i = 0; i < pointsNumber; i++){
                readed = reader.readLine();
                tokenizer = new StringTokenizer(readed);
                if(tokenizer.countTokens() < 4){
                    throw new IllegalArgumentException("invalid input information");
                }
                int m = Integer.parseInt(tokenizer.nextToken());
                int t = Integer.parseInt(tokenizer.nextToken());
                if(t < 0 || t > 255 || m < 0 || m > 100){
                    throw new IllegalArgumentException("invalid input information");
                }
                graphicEmissionPointsRed.add(new Point(m*4, t));
                t = Integer.parseInt(tokenizer.nextToken());
                if(t < 0 || t > 255){
                    throw new IllegalArgumentException("invalid input information");
                }
                graphicEmissionPointsGreen.add(new Point(m*4, t));
                t = Integer.parseInt(tokenizer.nextToken());
                if(t < 0 || t > 255){
                    throw new IllegalArgumentException("invalid input information");
                }
                graphicEmissionPointsBlue.add(new Point(m*4, t));
                checkLine(tokenizer);
            }


            while ((readed = reader.readLine()).length() == 0) { }
            tokenizer = new StringTokenizer(readed);
            if(tokenizer.countTokens() < 1){
                throw new IllegalArgumentException("invalid input information");
            }

            pointsNumber = Integer.parseInt(tokenizer.nextToken());
            for(int i = 0; i < pointsNumber; i++){
                readed = reader.readLine();
                tokenizer = new StringTokenizer(readed);
                if(tokenizer.countTokens() < 4){
                    throw new IllegalArgumentException("invalid input information");
                }
                String s = tokenizer.nextToken();
                double xNormalized = Double.parseDouble(/*tokenizer.nextToken()*/s);
                double yNormalized = Double.parseDouble(tokenizer.nextToken());
                double zNormalized = Double.parseDouble(tokenizer.nextToken());
                int q = Integer.parseInt(tokenizer.nextToken());

                if(xNormalized < 0 || zNormalized < 0 || yNormalized < 0 || xNormalized > 1 || zNormalized > 1 || yNormalized > 1){
                    throw new IllegalArgumentException("invalid input information");
                }
                MyPoint myPoint = new MyPoint( (int) (xNormalized* InitView.frameSideLength), (int)(yNormalized * InitView.frameSideLength), (int)(zNormalized * InitView.frameSideLength));
                chargeElementsMap.put(myPoint, q);
                checkLine(tokenizer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkLine(StringTokenizer tokenizer){
        if(tokenizer.hasMoreTokens()){
            String nextToken = tokenizer.nextToken();
            if(nextToken.codePointAt(0) != '/' || nextToken.codePointAt(1) != '/'){
                throw new IllegalArgumentException("invalid input information");
            }
        }
    }

}
