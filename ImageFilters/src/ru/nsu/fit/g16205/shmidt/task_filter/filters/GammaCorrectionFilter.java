package ru.nsu.fit.g16205.shmidt.task_filter.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GammaCorrectionFilter implements IFilter {
    @Override
    public BufferedImage getFilteredImage(BufferedImage image, int... ints) {
        double gamma = ints[0] / (double)100;
        BufferedImage filteredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                Color color = new Color(image.getRGB(x,y));
                int red,green,blue;
                red = (int) (255 * Math.pow(color.getRed() / 255.0, 1.0 / gamma));
                green = (int) (255 *  Math.pow(color.getGreen() / 255.0, 1.0 / gamma));
                blue = (int) (255 *  Math.pow(color.getBlue() / 255.0, 1.0 / gamma));
                filteredImage.setRGB(x, y, new RGBintChecker().getRGBint(red,green, blue));
            }
        }
        return filteredImage;
    }
}
