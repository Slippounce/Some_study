package ru.nsu.fit.g16205.shmidt.task_filter.filters;

import ru.nsu.fit.g16205.shmidt.task_filter.view.InitView;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Zoom2xFilter implements IFilter {
    @Override
    public BufferedImage getFilteredImage(BufferedImage image, int... ints) {
        BufferedImage filteredImage = new BufferedImage(InitView.frameSideLength, InitView.frameSideLength, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = filteredImage.createGraphics();
        g.drawImage(image, 0,0, filteredImage.getWidth(), filteredImage.getHeight(),null);
        g.dispose();
        return filteredImage;
    }
}
