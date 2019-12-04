package ru.nsu.fit.g16205.shmidt.task_filter.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BlackWhiteFilter implements IFilter {
    @Override
    public BufferedImage getFilteredImage(BufferedImage image, int... ints) {
        BufferedImage filteredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i < image.getHeight(); i++){
            for(int j = 0; j < image.getWidth(); j++){
                Color color = new Color(image.getRGB(j,i));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                int res = (red+green+blue)/3;
                if (res > 255) res = 255;
                Color newColor = new Color(res,res,res);
                filteredImage.setRGB(j,i,newColor.getRGB());
            }
        }
        return filteredImage;
    }
}
