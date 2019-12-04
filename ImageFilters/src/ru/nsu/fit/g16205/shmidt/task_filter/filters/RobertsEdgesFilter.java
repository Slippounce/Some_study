package ru.nsu.fit.g16205.shmidt.task_filter.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RobertsEdgesFilter implements IFilter {
    @Override
    public BufferedImage getFilteredImage(BufferedImage image, int... ints) {
        BufferedImage filteredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                if(getRobertsRate(image,y,x) > ints[0]){
                    filteredImage.setRGB(x, y, Color.WHITE.getRGB());
                }else{
                    filteredImage.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }

        return filteredImage;
    }


    private int getRobertsRate(BufferedImage image, int y, int x){
        Color g11,g12,g21,g22;
        int xChecked = x+1 < image.getWidth() ? x+1 : x;
        int yChecked = y+1 < image.getHeight() ? y+1 : y;

        g11 = new Color(image.getRGB(x,y));
        g12 = new Color(image.getRGB(xChecked, y));
        g21 = new Color(image.getRGB(x, yChecked));
        g22 = new Color(image.getRGB(xChecked, yChecked));
        return (getChannelRobertsRate(g11.getRed(),g12.getRed(),g21.getRed(),g22.getRed())
                + getChannelRobertsRate(g11.getGreen(),g12.getGreen(),g21.getGreen(),g22.getGreen())
                + getChannelRobertsRate(g11.getBlue(),g12.getBlue(),g21.getBlue(),g22.getBlue())) /3;
    }

    private int getChannelRobertsRate(int g11, int g12, int g21, int g22){
        return Math.abs(g11 - g22) + Math.abs(g21 - g12);
    }
}
