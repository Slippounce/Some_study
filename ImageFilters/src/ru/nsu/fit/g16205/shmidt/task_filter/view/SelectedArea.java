package ru.nsu.fit.g16205.shmidt.task_filter.view;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SelectedArea {

    private BufferedImage image;
    private double divider;
    private int realSelectedAreaWidth;
    private int realSelectedAreaHeight;
    private int realImageWidth;
    private int realImageHeight;

    public SelectedArea(BufferedImage image, double divider) {
        this.image = image;
        this.divider = divider;
        realSelectedAreaHeight = (int) Math.round(InitView.frameSideLength / divider);
        realSelectedAreaWidth = (int) Math.round(InitView.frameSideLength/divider);
        realImageHeight = (int) Math.round(image.getHeight()/divider);
        realImageWidth =  (int)Math.round(image.getWidth()/divider);
    }

    public BufferedImage drawSelectedArea(Graphics g,Point presumptiveCenterCoordinate){
        Graphics2D graphics2D = (Graphics2D) g.create();
        Point topLeftCorner = new Point();
        if(isPictureLessThanPrefferedSelectedSize()){
            drawSelectedArea(graphics2D,image.getWidth(),image.getHeight(), new Point(InitView.padding,InitView.padding));
            return image;
        }
        if(presumptiveCenterCoordinate.x < InitView.padding+InitView.frameSideLength + 1 && presumptiveCenterCoordinate.x >= InitView.padding){
            // если жмакнули внутри зоны А
            topLeftCorner.x = presumptiveCenterCoordinate.x - realSelectedAreaWidth/2;
            if(presumptiveCenterCoordinate.x - InitView.padding < realSelectedAreaWidth/2){
                // если жмакнули внутри зоны но близко к границе left
                topLeftCorner.x = InitView.padding;
            }
            if(InitView.padding + realImageWidth - presumptiveCenterCoordinate.x  <= realSelectedAreaWidth/2){
                // если жмакнули близко к границе right
                topLeftCorner.x = InitView.padding +realImageWidth - realSelectedAreaWidth - 1;
            }
        }else{
            // если жмакнули вне зоны по х координате
            if(presumptiveCenterCoordinate.x > InitView.padding+InitView.frameSideLength) {
                topLeftCorner.x = InitView.padding + realImageWidth - realSelectedAreaWidth - 1;
            }
            if(presumptiveCenterCoordinate.x < InitView.padding){
                topLeftCorner.x = InitView.padding;
            }
        }

        if(presumptiveCenterCoordinate.y < InitView.padding + InitView.frameSideLength + 1 && presumptiveCenterCoordinate.y > InitView.padding - 1){
            //жмакнули внутри зоны А по координате у
            topLeftCorner.y = presumptiveCenterCoordinate.y - realSelectedAreaHeight/2;
                if (presumptiveCenterCoordinate.y - InitView.padding < realSelectedAreaHeight / 2) {
                    topLeftCorner.y = InitView.padding;
                }
                if (InitView.padding + realImageHeight - presumptiveCenterCoordinate.y <= realSelectedAreaHeight / 2 ) {

                    topLeftCorner.y = InitView.padding  + realImageHeight - realSelectedAreaHeight - 1;
                }
        }else{
            if(presumptiveCenterCoordinate.y > InitView.padding + InitView.frameSideLength){
                topLeftCorner.y = InitView.padding + realImageHeight - realSelectedAreaHeight - 1;
            }
            if(presumptiveCenterCoordinate.y < InitView.padding){
                topLeftCorner.y = InitView.padding;
            }

        }

        drawSelectedArea(graphics2D, realSelectedAreaWidth,realSelectedAreaHeight,topLeftCorner);
        return image.getSubimage((int) ((topLeftCorner.x - InitView.padding)*divider), (int) ((topLeftCorner.y - InitView.padding)*divider), InitView.frameSideLength, InitView.frameSideLength);
    }

    private boolean isPictureLessThanPrefferedSelectedSize(){
        return image.getHeight() <= InitView.frameSideLength && image.getWidth() <= InitView.frameSideLength;
    }

    private void drawSelectedArea(Graphics2D graphics2D, int width, int height, Point topLeftCorner){
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{2}, 0);
        graphics2D.setStroke(dashed);
        graphics2D.drawLine(topLeftCorner.x, topLeftCorner.y, topLeftCorner.x + width, topLeftCorner.y);
        graphics2D.drawLine(topLeftCorner.x, topLeftCorner.y, topLeftCorner.x, topLeftCorner.y + height);
        graphics2D.drawLine(topLeftCorner.x+width, topLeftCorner.y, topLeftCorner.x+width, topLeftCorner.y+height);
        graphics2D.drawLine(topLeftCorner.x, topLeftCorner.y+height, topLeftCorner.x+width, topLeftCorner.y+height);
    }
}
