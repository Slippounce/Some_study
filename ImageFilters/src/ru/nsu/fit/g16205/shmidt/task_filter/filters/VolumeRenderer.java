package ru.nsu.fit.g16205.shmidt.task_filter.filters;

import ru.nsu.fit.g16205.shmidt.task_filter.view.InitView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class VolumeRenderer {

    //region Private entities
    private ArrayList<Point> graphicAbsorptionPoints;
    private boolean isEmissionOn = false;
    private  boolean isAbsorptionOn = false;
    //endregion

    //region Volume Rendering
    public void changeEmissionRenderingStatus(){
        isEmissionOn = !isEmissionOn;
    }
    private class MM{

        double min;
        double max;


        MM(double min, double max) {
            this.min = min;
            this.max = max;
        }
    }
    public void changeAbsorptionRenderingStatus(){
        isAbsorptionOn = !isAbsorptionOn;
    }


    public BufferedImage getVolumeRenderedImage(BufferedImage image, HashMap<RenderConfigParser.MyPoint, Integer> chargesMap, int nx, int ny, int nz, ArrayList<Point> graphicAbsorptionPoints, ArrayList<Point> graphicEmissionPointsRed, ArrayList<Point> graphicEmissionPointsGreen,ArrayList<Point> graphicEmissionPointsBlue){

        if(!isAbsorptionOn && !isEmissionOn){
            throw new IllegalArgumentException();
        }

        this.graphicAbsorptionPoints = graphicAbsorptionPoints;
        MM maxAndMinTValues = findMaxAndMinValues(chargesMap, nx, ny, nz);
        double normalizer = (maxAndMinTValues.max - maxAndMinTValues.min) / 100.0;
        double shift = -(maxAndMinTValues.min / normalizer);
        BufferedImage filteredImage = new BufferedImage(InitView.frameSideLength, InitView.frameSideLength, BufferedImage.TYPE_INT_RGB);

        for(int y = 0 ; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                Color color = new Color(image.getRGB(x,y));
                double red = color.getRed();
                double green = color.getGreen();
                double blue = color.getBlue();
                int redG = 0;
                int greenG = 0;
                int blueG = 0;
                double tau = 0;
                for(int i = 0 ; i < nz; i++){
                    if(isAbsorptionOn) {
                        tau = getAbsorptionCof(chargesMap, x, y, i, normalizer, shift);
                    }
                    if(isEmissionOn) {
                        redG = getEmissionCof(chargesMap, graphicEmissionPointsRed, x, y, i, normalizer, shift);
                        greenG = getEmissionCof(chargesMap, graphicEmissionPointsGreen, x, y, i, normalizer, shift);
                        blueG = getEmissionCof(chargesMap, graphicEmissionPointsBlue, x, y, i, normalizer, shift);
                    }
                    red = red * Math.exp((-tau) * (1.0/nz)) + redG;
                    green = green * Math.exp((-tau) * (1.0/nz)) + greenG;
                    blue = blue * Math.exp((-tau) * (1.0/nz)) + blueG;
                }
                filteredImage.setRGB(x,y, new RGBintChecker().getRGBint((int)red, (int)green, (int)blue));
            }
        }
        return filteredImage;
    }


    private int getEmissionCof(HashMap<RenderConfigParser.MyPoint, Integer> chargesMap, ArrayList<Point> graphicEmissionPoints, int x, int y, int z, double normalizer, double shift){
    Iterator<java.util.Map.Entry<RenderConfigParser.MyPoint, Integer>> iterator = chargesMap.entrySet().iterator();
    double f = 0;
    while(iterator.hasNext()){
        HashMap.Entry<RenderConfigParser.MyPoint, Integer> pair = iterator.next();
        f += pair.getValue() / getDistance(x, y, z, pair.getKey().x, pair.getKey().y, pair.getKey().z);
    }
    f = (f)/normalizer + shift;
    return getG(f, graphicEmissionPoints);
}

    private int getG(double f, ArrayList<Point> graphicEmissionPoints){
    int g = 0;
    double smallestStep = Double.MAX_VALUE;


        for (Point graphicEmissionPoint : graphicEmissionPoints) {
            if (Math.abs(f - graphicEmissionPoint.x / 4) < smallestStep) {
                smallestStep = f - graphicEmissionPoint.x / 4;
                g = graphicEmissionPoint.y;
            }
            if (Math.abs(f - graphicEmissionPoint.x / 4) == smallestStep) {
                if (g < graphicEmissionPoint.y) {
                    g = graphicEmissionPoint.y;
                }
            }

        }
    // типа нашел ближайшую точку

    return g;
}

    private double getAbsorptionCof(HashMap<RenderConfigParser.MyPoint, Integer> chargesMap, int x, int y, int z, double normilizer, double shift){
        Iterator<java.util.Map.Entry<RenderConfigParser.MyPoint, Integer>> iterator = chargesMap.entrySet().iterator();
        double f = 0;
        while(iterator.hasNext()){
            HashMap.Entry<RenderConfigParser.MyPoint, Integer> pair = iterator.next();
            f += pair.getValue() / getDistance(x, y, z, pair.getKey().x, pair.getKey().y, pair.getKey().z);
        }
        f = (f)/normilizer + shift;
        return getTau(f);
    }


    private double getTau(double f){
        double tau;
        int x1 = -1, x2 = -1;
        double y1 = -1,y2 = -1;
        for(int i = 0; i < graphicAbsorptionPoints.size(); i++){
            if(f > graphicAbsorptionPoints.get(i).x/4){
                x1 = graphicAbsorptionPoints.get(i).x/4;
                y1 = graphicAbsorptionPoints.get(i).y/100;
                x2 = graphicAbsorptionPoints.get(i+1).x/4;
                y2 = graphicAbsorptionPoints.get(i+1).y/100;
            }else{
                break;
            }
        }
        if(y1 == y2){
            tau = y1;
        }else {
            tau = ((f - x1) * (y2 - y1)) / (x2 - x1);
        }
        return tau;
    }

    private MM findMaxAndMinValues(HashMap<RenderConfigParser.MyPoint, Integer> chargesMap, int nx, int ny, int nz){
        double tMax = 0, tMin = Double.MAX_VALUE;
        for(int x = 0; x < nx; x++){
            for(int y = 0; y < ny; y++){
                for(int z = 0; z < nz; z++){
                    Iterator<java.util.Map.Entry<RenderConfigParser.MyPoint, Integer>> iterator = chargesMap.entrySet().iterator();
                    double t = 0;
                    while(iterator.hasNext()){
                        HashMap.Entry<RenderConfigParser.MyPoint, Integer> pair = iterator.next();
                        t += pair.getValue() / getDistance(x, y, z, pair.getKey().x, pair.getKey().y, pair.getKey().z);
                    }
                    if(tMax < t){
                        tMax = t;
                    }
                    if(tMin > t){
                        tMin = t;
                    }
                }
            }
        }
        return new MM(tMin, tMax);
    }

    private double getDistance(int x1, int y1, int z1, int x2, int y2, int z2){
        double distance = Math.sqrt((Math.pow(x1 - x2, 2))+(Math.pow(y1 - y2, 2))+(Math.pow(z1 - z2, 2)));
        if(distance < 1){
            distance = 1;
        }
        return distance;
    }
    //endregion


}
