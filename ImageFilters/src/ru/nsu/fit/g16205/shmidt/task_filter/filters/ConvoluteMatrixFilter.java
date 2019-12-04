package ru.nsu.fit.g16205.shmidt.task_filter.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ConvoluteMatrixFilter implements IFilter {
    @Override
    public BufferedImage getFilteredImage(BufferedImage image, int... ints) {
        double[][] kernel = getKernel();
        return convolute(image, kernel);
    }

    protected double[][] getKernel(){
        return new double[][]{{1},{1}};
    }

    protected BufferedImage convolute(BufferedImage image, double[][] kernel){
        BufferedImage filteredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i < image.getHeight(); i++){
            for(int j = 0; j < image.getWidth(); j++){
                int filteredPixel = getResultedPixel(image, kernel, i,j);
                filteredImage.setRGB(j,i,filteredPixel);
            }
        }
        return filteredImage;
    }

    private int getResultedPixel(BufferedImage image, double[][] kernel , int y, int x){
        int resultRed = 0;
        int resultGreen = 0;
        int resultBlue = 0;
        int n = (kernel.length - 1)/2;

        for(int i = -n; i <= n; i++){
            for(int j = -n; j <= n; j++){
                int xChecked = x+j;
                int yChecked = y+i;
                if(x+j < 0){
                    xChecked = 0;
                }
                if(x+j >= image.getWidth()){
                    xChecked = image.getWidth()-1;
                }
                if(y+i < 0){
                    yChecked = 0;
                }
                if(y+i >= image.getHeight()){
                    yChecked = image.getHeight()-1;
                }
                Color color = new Color(image.getRGB(xChecked,yChecked));
                resultRed += (color.getRed()) *kernel[i+n][j+n];
                resultGreen += (color.getGreen())*kernel[i+n][j+n];
                resultBlue += (color.getBlue())*kernel[i+n][j+n];
            }
        }
        return new RGBintChecker().getRGBint(resultRed, resultGreen, resultBlue);
    }
}
