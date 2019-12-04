package ru.nsu.fit.g16205.shmidt.task_filter.filters;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class SmoothFilter implements IFilter {
    @Override
    public BufferedImage getFilteredImage(BufferedImage image, int... ints) {
        int n = ints[0];
        ArrayList<Integer> resultsRed = new ArrayList<>();

        ArrayList<Integer> resultsGreen = new ArrayList<>();

        ArrayList<Integer> resultsBlue = new ArrayList<>();

        BufferedImage filteredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        for(int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                for (int i = -n; i <= n; i++) {
                    for (int j = -n; j <= n; j++) {

                        int xChecked = x + j;
                        int yChecked = y + i;
                        if (x + j < 0) {
                            xChecked = 0;
                        }
                        if (x + j >= image.getWidth()) {
                            xChecked = image.getWidth() - 1;
                        }
                        if (y + i < 0) {
                            yChecked = 0;
                        }
                        if (y + i >= image.getHeight()) {
                            yChecked = image.getHeight() - 1;
                        }
                        Color color = new Color(image.getRGB(xChecked, yChecked));
                        resultsRed.add(color.getRed());
                        resultsGreen.add(color.getGreen());
                        resultsBlue.add(color.getBlue());
                    }
                }
                Collections.sort(resultsRed);
                Collections.sort(resultsGreen);
                Collections.sort(resultsBlue);
                int medianElementIndex = ((( 2 * n + 1) * (2 * n + 1)) - 1) / 2;
                filteredImage.setRGB(x, y, new RGBintChecker().getRGBint(resultsRed.get( medianElementIndex), resultsGreen.get(medianElementIndex), resultsBlue.get(medianElementIndex)));
                resultsBlue.clear();
                resultsRed.clear();
                resultsGreen.clear();
            }
        }
        return filteredImage;
    }
}
