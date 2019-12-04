package ru.nsu.fit.g16205.shmidt.task_filter.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OrderedDitheringFilter implements IFilter {
    @Override
    public BufferedImage getFilteredImage(BufferedImage image, int... ints) {
        BufferedImage filteredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        int[][] matrix =  getOrderedDitheringMatrix();
        for(int i = 0 ; i < image.getHeight(); i++){
            for(int j = 0; j < image.getWidth(); j++){
                Color color = new Color(image.getRGB(j,i));
                int red = 0,green = 0,blue = 0;
                if(color.getRed() > matrix[i % 16][j % 16]){
                    red = 255;
                }
                if(color.getGreen() > matrix[i % 16][j % 16]){
                    green = 255;
                }
                if(color.getBlue() > matrix[i % 16][j % 16]){
                    blue = 255;
                }
                filteredImage.setRGB(j, i, new Color(red, green,blue).getRGB());
            }
        }


        return filteredImage;
    }

    private int[][] getOrderedDitheringMatrix(){
        int[][] tmp = new int[][]{
                {252,124,220,92,244,116,212,84},
                {60,188,28,156,52,180,20,148},
                {204,76,236,108,196,68,228,100},
                {12,140,44,172,4,132,36,164},
                {240,112,208,80,248,120,216,88},
                {48,176,16,144,56,184,24,152},
                {192,64,224,96,200,72,232,104},
                {0,128,32,160,8,136,40,168},
        };
        int[][] matrix = new int[16][16];
        for(int i = 0 ; i < 8;i++ ){
            for(int j = 0; j < 8; j++){
                matrix[i][j] = tmp[i][j] + 3;
                matrix[i+8][j] = tmp[i][j] + 1;
                matrix[i][j+8] = tmp[i][j];
                matrix[i+8][j+8] = tmp[i][j] + 2;
            }
        }
        return matrix;

    }
}
