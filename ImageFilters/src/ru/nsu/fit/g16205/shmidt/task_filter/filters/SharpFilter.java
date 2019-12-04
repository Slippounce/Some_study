package ru.nsu.fit.g16205.shmidt.task_filter.filters;

import java.awt.image.BufferedImage;

public class SharpFilter extends ConvoluteMatrixFilter {
    @Override
    public BufferedImage getFilteredImage(BufferedImage image, int ... ints){
        double[][] kernel = getKernel();
        return convolute(image, kernel);
    }

    @Override
    protected double[][] getKernel(){
        return new double[][]{{0,-1,0},{-1,5,-1},{0,-1,0}};
    }
}
