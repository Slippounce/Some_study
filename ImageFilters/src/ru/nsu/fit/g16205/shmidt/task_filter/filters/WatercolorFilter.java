package ru.nsu.fit.g16205.shmidt.task_filter.filters;

import java.awt.image.BufferedImage;

public class WatercolorFilter implements IFilter {
    @Override
    public BufferedImage getFilteredImage(BufferedImage image, int... ints) {
        BufferedImage filteredImage;
        filteredImage = new SmoothFilter().getFilteredImage(image,2);
        filteredImage = new SharpFilter().getFilteredImage(filteredImage);
        return filteredImage;
    }
}
