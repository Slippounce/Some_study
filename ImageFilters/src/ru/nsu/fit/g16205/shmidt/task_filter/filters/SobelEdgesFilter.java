package ru.nsu.fit.g16205.shmidt.task_filter.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SobelEdgesFilter implements IFilter {
    @Override
    public BufferedImage getFilteredImage(BufferedImage image, int... ints) {
        BufferedImage filteredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i < image.getHeight(); i++){
            for(int j = 0; j < image.getWidth(); j++){
                if(getSobelRate(getVicinityColors(image,j,i,1)) > ints[0]){
                    filteredImage.setRGB(j,i, Color.WHITE.getRGB());
                }else{
                    filteredImage.setRGB(j,i, Color.BLACK.getRGB());
                }
            }
        }
        return filteredImage;
    }

    private double getSobelRate( Color[][] vicinity){
        long sxRed = (vicinity[0][2].getRed() + 2*vicinity[1][2].getRed() +vicinity[2][2].getRed()) - (vicinity[0][0].getRed() + 2*vicinity[1][0].getRed()+vicinity[2][0].getRed());
        long syRed = (vicinity[2][0].getRed()+2*vicinity[2][1].getRed()+vicinity[2][2].getRed()) - (vicinity[0][0].getRed()+2*vicinity[0][1].getRed()+vicinity[0][2].getRed());
        long sxGreen = (vicinity[0][2].getGreen()+2*vicinity[1][2].getGreen()+vicinity[2][2].getGreen()) - (vicinity[0][0].getGreen() + 2*vicinity[1][0].getGreen()+vicinity[2][0].getGreen());
        long syGreen = (vicinity[2][0].getGreen()+2*vicinity[2][1].getGreen()+vicinity[2][2].getGreen()) - (vicinity[0][0].getGreen()+2*vicinity[0][1].getGreen()+vicinity[0][2].getGreen());
        long sxBlue = (vicinity[0][2].getBlue()+2*vicinity[1][2].getBlue()+vicinity[2][2].getBlue()) - (vicinity[0][0].getBlue() + 2*vicinity[1][0].getBlue()+vicinity[2][0].getBlue());
        long syBlue = (vicinity[2][0].getBlue()+2*vicinity[2][1].getBlue()+vicinity[2][2].getBlue()) - (vicinity[0][0].getBlue()+2*vicinity[0][1].getBlue()+vicinity[0][2].getBlue());
        long sx = sxRed+sxGreen+sxBlue;
        long sy = syRed+syBlue+syGreen;

        return Math.sqrt(Math.pow(sx,2) + Math.pow(sy,2));
    }

    private Color[][] getVicinityColors(BufferedImage image, int x, int y, int vicinityLevel){
        Color [][] vicinity = new Color[vicinityLevel*2+1][vicinityLevel*2+1];
        for (int i = -vicinityLevel; i <= vicinityLevel; i++) {
            for (int j = -vicinityLevel; j <= vicinityLevel; j++) {

                int xChecked = x + j;
                int yChecked = y + i;
                if (x + j < 0) {
                    xChecked = 0;
                }
                if (x + j >= image.getWidth()) {
                    xChecked = image.getWidth() - 1;
                }
                if (y + i < 0) {
                    yChecked = 0;
                }
                if (y + i >= image.getHeight()) {
                    yChecked = image.getHeight() - 1;
                }

                vicinity[i+vicinityLevel][j+vicinityLevel] = new Color(image.getRGB(xChecked,yChecked));
            }
        }
        return vicinity;
    }

}
