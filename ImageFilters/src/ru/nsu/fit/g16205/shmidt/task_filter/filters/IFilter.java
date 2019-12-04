package ru.nsu.fit.g16205.shmidt.task_filter.filters;

import java.awt.image.BufferedImage;

public interface IFilter {
    BufferedImage getFilteredImage(BufferedImage image, int... ints);
}
