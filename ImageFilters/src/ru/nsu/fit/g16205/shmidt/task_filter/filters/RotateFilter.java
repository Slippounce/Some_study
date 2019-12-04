package ru.nsu.fit.g16205.shmidt.task_filter.filters;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class RotateFilter implements IFilter {
    @Override
    public BufferedImage getFilteredImage(BufferedImage image, int... ints) {
        double locationX = image.getWidth() / 2;
        double locationY = image.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians (ints[0]), locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB/*image.getType()*/);
        op.filter(image, newImage);
        return newImage;
    }
}
