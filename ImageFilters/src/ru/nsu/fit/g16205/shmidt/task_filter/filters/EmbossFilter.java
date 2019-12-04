package ru.nsu.fit.g16205.shmidt.task_filter.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EmbossFilter extends ConvoluteMatrixFilter {
    @Override
    public BufferedImage getFilteredImage(BufferedImage image, int... ints) {
        double[][] kernel = getKernel();
        BufferedImage filteredImage = convolute(image, kernel);
        addShift(filteredImage, 128);
        return filteredImage;
    }

    @Override
    protected double[][] getKernel(){
        return new double[][]{{0,1,0},{1,0,-1},{0,-1,0}};
    }

    private void addShift(BufferedImage image, int shift){
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                Color currentColor = new Color(image.getRGB(x,y));
                int red = currentColor.getRed()+shift;
                int green = currentColor.getGreen()+shift;
                int blue = currentColor.getBlue()+shift;
                image.setRGB(x,y,new RGBintChecker().getRGBint(red, green, blue));
            }
        }
    }

}
