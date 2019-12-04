package ru.nsu.fit.g16205.shmidt.task_filter.filters;

import java.awt.image.BufferedImage;

public class NegativeFilter implements IFilter {
    @Override
    public BufferedImage getFilteredImage(BufferedImage image, int... ints) {
        BufferedImage filteredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                filteredImage.setRGB(x,y,~image.getRGB(x,y));
            }
        }
        return filteredImage;
    }
}
