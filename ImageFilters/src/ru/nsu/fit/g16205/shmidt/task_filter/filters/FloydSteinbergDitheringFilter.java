package ru.nsu.fit.g16205.shmidt.task_filter.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FloydSteinbergDitheringFilter implements IFilter {
    @Override
    public BufferedImage getFilteredImage(BufferedImage image, int... ints) {
        double redQuotient = 255 / (double)(ints[0] - 1);
        double greenQuotient = 255 / (double)(ints[1] - 1);
        double blueQuotient = 255 / (double)(ints[2] - 1);
        BufferedImage filteredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        RGBerror[] currentRowErrors = new RGBerror[image.getWidth()];
        RGBerror[] nextRowErrors = new RGBerror[image.getWidth()];
        for(int i = 0; i < image.getWidth(); i++){
            currentRowErrors[i] = new RGBerror();
            nextRowErrors[i] = new RGBerror();
        }

        int redNew;
        int greenNew;
        int blueNew;
        double redError;
        double greenError;
        double blueError;
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                Color currentColor = new Color(image.getRGB(x,y));


//                double np = Math.floor((checkPalleteValueForDithering((int) (currentColor.getRed()+currentRowErrors[x].getRed())))/redQuotient);
                double paletteColor = Math.round((currentColor.getRed() + currentRowErrors[x].getRed()) / redQuotient)*redQuotient;
                redNew = checkPalleteValueForDithering((int) paletteColor);
                redError = currentColor.getRed() + currentRowErrors[x].getRed() - paletteColor;

                paletteColor = Math.round((currentColor.getGreen() + currentRowErrors[x].getGreen()) / greenQuotient) * greenQuotient;
                greenNew = checkPalleteValueForDithering((int) paletteColor);
                greenError = currentColor.getGreen() + currentRowErrors[x].getGreen() - paletteColor;

                paletteColor = Math.round((currentColor.getBlue() + currentRowErrors[x].getBlue()) / blueQuotient) * blueQuotient;
                blueNew = checkPalleteValueForDithering((int) paletteColor);
                blueError = currentColor.getBlue() + currentRowErrors[x].getBlue() - paletteColor;

                filteredImage.setRGB(x,y, new RGBintChecker().getRGBint(redNew,greenNew,blueNew));
                dispelError(x, currentRowErrors, nextRowErrors,redError,greenError,blueError);


            }
            RGBerror[] t = currentRowErrors;
            currentRowErrors = nextRowErrors;
            nextRowErrors = t;
            clearErrorsArray(nextRowErrors);
        }
        return filteredImage;
    }

    private int checkPalleteValueForDithering(int value){
        if(value >= 255){
            return 254;
        }
        if(value < 0){
            return 0;
        }
        return value;
    }

    private void clearErrorsArray(RGBerror[] array){
        for (RGBerror anArray : array) {
            anArray.reset();
        }
    }


    private void dispelError(int position, RGBerror[] currentRow, RGBerror[] nextRow, double redError, double greenError, double blueError){
        if(position+1 < currentRow.length) {
            currentRow[position + 1].addShift((7*redError)/16,7*greenError/16,7*blueError/16);
            nextRow[position].addShift(redError/16, greenError/16, greenError/16);
        }
        nextRow[position].addShift(5*redError/16, 5*greenError/16, 5*greenError/16);
        if(position-1 >= 0){
            nextRow[position].addShift(3*redError/16, 3*greenError/16, 3*greenError/16);
        }
    }
}
