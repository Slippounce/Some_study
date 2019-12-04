package ru.nsu.fit.g16205.shmidt.task_filter.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.util.ArrayList;

public class InitView extends JPanel {
    public static final int frameSideLength = 350;
    public static final int padding = 10;
    private final String firstFieldName = "Zone A";
    private final String secondFieldName = "Zone B";
    private final String thirdFieldName = "Zone C";
    private BufferedImage currentImage;
    private Image compressedImage;
    private BufferedImage selectedImagePiece;
    private BufferedImage filteredImage;
    private SelectedArea selectedArea;
    private Point mousePosition = new Point();
    private double currentDivider;
    private CursorMode currentCursorMode = CursorMode.POINTER;
    private boolean isMousePressedInsideZoneA;
    private boolean isAreaSelecting = false;
    private boolean isGraphicsDraw = false;
    private ArrayList<Point> absorptionGraphic;
    private ArrayList<Point> emissionRed;
    private ArrayList<Point> emissionGreen;
    private ArrayList<Point> emissionBlue;


    public InitView()  {
        currentImage = null;
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(isSelectMode() && currentImage != null){
                    if(e.getX() >= padding && e.getX() <= padding+frameSideLength && e.getY() >= padding && e.getY() <= padding+frameSideLength){
                        isAreaSelecting = true;
                        isMousePressedInsideZoneA = true;
                        mousePosition.x = e.getX();
                        mousePosition.y = e.getY();
                        repaint();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                isMousePressedInsideZoneA = false;
                isAreaSelecting = false;
            }

        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if(isAreaSelecting && isMousePressedInsideZoneA){
                   mousePosition.x = e.getX();
                   mousePosition.y = e.getY();
                   repaint();
                }
            }
        });

    }

    public BufferedImage getSelectedImagePiece() {
        return selectedImagePiece;
    }

    public BufferedImage getFilteredImage() {
        return filteredImage;
    }

    public void setCurrentImage(BufferedImage currentImage) {
        this.currentImage = currentImage;
        isMousePressedInsideZoneA = false;
        selectedArea = null;
        isAreaSelecting = false;
        selectedImagePiece = null;
        filteredImage = null;
        if(currentImage != null){
            double imageWidth = currentImage.getWidth();
            double imageHeight = currentImage.getHeight();
            currentDivider = 1;
            if(imageHeight >= imageWidth && imageHeight > frameSideLength){
                currentDivider = imageHeight / frameSideLength;
            }
            if(imageWidth > imageHeight && imageWidth > frameSideLength){
                currentDivider = imageWidth / frameSideLength;
            }
            compressedImage = currentImage.getScaledInstance((int)Math.floor(imageWidth/currentDivider) ,(int)Math.floor(imageHeight/currentDivider),Image.SCALE_SMOOTH);
            selectedArea = new SelectedArea(currentImage,currentDivider);
        }
        repaint();
    }

    public void setFilteredImage(BufferedImage filteredImage) {
        this.filteredImage = filteredImage;
        repaint();
    }

    public void setSelectCursorMode() {
        this.currentCursorMode = CursorMode.SELECT;
    }

    public void setPointerCursorMode(){
        this.currentCursorMode = CursorMode.POINTER;
        isAreaSelecting = false;
    }

    public boolean isSelectMode(){
        return currentCursorMode == CursorMode.SELECT;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawDottedFrame(g, padding, padding, frameSideLength, frameSideLength);
        drawDottedFrame(g,frameSideLength + 2 * padding, padding, frameSideLength, frameSideLength);
        drawDottedFrame(g,2 * (frameSideLength + padding) + padding, padding, frameSideLength, frameSideLength);

        if(currentImage == null){
            drawFramesNames(g);
        }else{
            drawPicture(g);
            if(isAreaSelecting){
                try {
                    selectedImagePiece = selectedArea.drawSelectedArea(g, mousePosition);
                }catch (RasterFormatException e){}
                g.drawImage(selectedImagePiece,2 * InitView.padding + InitView.frameSideLength, InitView.padding, null);
            }else {
                if(selectedImagePiece != null){
                    g.drawImage(selectedImagePiece,2 * InitView.padding + InitView.frameSideLength, InitView.padding, null);
                }else{
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(Color.WHITE);
                    g2.fillRect(2 * InitView.padding + InitView.frameSideLength, InitView.padding, InitView.frameSideLength, InitView.frameSideLength);
                }
            }
            if(filteredImage!=null) {
                g.drawImage(filteredImage, InitView.padding * 3 + 2 * InitView.frameSideLength, InitView.padding, null);
            }else{
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.WHITE);
                g2.fillRect(3*InitView.padding+2*InitView.frameSideLength, InitView.padding, InitView.frameSideLength, InitView.frameSideLength);
            }

            if(isGraphicsDraw){
                drawGraphics(g);
            }
        }
        revalidate();
    }

    private void drawGraphics(Graphics graphics){
        Point a1, a2;
        a1 = new Point(absorptionGraphic.get(0).x, absorptionGraphic.get(0).y);
        a2 = new Point();
        for(int i = 1; i < absorptionGraphic.size(); i++){
            a2.x = absorptionGraphic.get(i).x;
            a2.y = absorptionGraphic.get(i).y;
            graphics.drawLine(a1.x + InitView.padding, -a1.y + 2 * InitView.padding + InitView.frameSideLength + 100, a2.x + InitView.padding, -a2.y + 2 * InitView.padding + InitView.frameSideLength + 100);
            a1.x = a2.x;
            a1.y = a2.y;

        }

        a1.x = emissionRed.get(0).x;
        a1.y =  emissionRed.get(0).y;

        for(int i = 1; i < emissionRed.size(); i++){
            a2.x =  emissionRed.get(i).x;
            a2.y =  emissionRed.get(i).y;
            Graphics2D graphics2D = (Graphics2D) graphics.create();
            graphics2D.setColor(Color.RED);
            graphics2D.drawLine(a1.x + 500, -(a1.y/2) + 2 * InitView.padding + InitView.frameSideLength + 175, a2.x + 500, -(a2.y/2) + 2 * InitView.padding + InitView.frameSideLength + 175);
            a1.x = a2.x;
            a1.y = a2.y;
        }

        a1.x = emissionGreen.get(0).x;
        a1.y =  emissionGreen.get(0).y;

        for(int i = 1; i < emissionGreen.size(); i++){
            a2.x =  emissionGreen.get(i).x;
            a2.y =  emissionGreen.get(i).y;
            Graphics2D graphics2D = (Graphics2D) graphics.create();
            graphics2D.setColor(Color.GREEN);
            graphics2D.drawLine(a1.x + 500, -(a1.y/2+2) + 2 * InitView.padding + InitView.frameSideLength + 175, a2.x + 500, -(a2.y/2+2) + 2 * InitView.padding + InitView.frameSideLength + 175);
            a1.x = a2.x;
            a1.y = a2.y;
        }

        a1.x = emissionBlue.get(0).x;
        a1.y =  emissionBlue.get(0).y;

        for(int i = 1; i < emissionBlue.size(); i++){
            a2.x =  emissionBlue.get(i).x;
            a2.y =  emissionBlue.get(i).y;
            Graphics2D graphics2D = (Graphics2D) graphics.create();
            graphics2D.setColor(Color.BLUE);
            graphics2D.drawLine(a1.x + 500, -(a1.y/2+4) + 2 * InitView.padding + InitView.frameSideLength + 175, a2.x + 500, -(a2.y/2+4) + 2 * InitView.padding + InitView.frameSideLength + 175);
            a1.x = a2.x;
            a1.y = a2.y;
        }

    }

    public void setRenderGraphics(ArrayList<Point> absorption, ArrayList<Point> emissionRed, ArrayList<Point> emissionGreen, ArrayList<Point> emissionBlue){
        isGraphicsDraw = true;
        absorptionGraphic = absorption;
        this.emissionRed = emissionRed;
        this.emissionGreen = emissionGreen;
        this.emissionBlue = emissionBlue;
        repaint();
    }

    private void drawPicture(Graphics g){
        g.drawImage(compressedImage,padding,padding,null);
    }

    private void drawFramesNames(Graphics g){
        Graphics2D graphics2D = (Graphics2D) g.create();
        graphics2D.setColor(Color.RED);
        Font font = new Font("Times Roman", Font.BOLD, 25);
        graphics2D.setFont(font);
        graphics2D.drawString(firstFieldName,
                findXcoordinateForStringLabel(padding,frameSideLength+padding, firstFieldName, font),
                findYcordinateForStringLabel(padding,frameSideLength+padding,firstFieldName,font));
        graphics2D.drawString(secondFieldName,
                findXcoordinateForStringLabel(frameSideLength+2*padding,frameSideLength*2+2*padding,secondFieldName,font),
                findYcordinateForStringLabel(padding,frameSideLength+padding,secondFieldName,font));
        graphics2D.drawString(thirdFieldName,
                findXcoordinateForStringLabel(2*(frameSideLength+padding)+padding,3*(frameSideLength+padding),thirdFieldName,font),
                findYcordinateForStringLabel(padding,frameSideLength+padding,thirdFieldName,font));
    }

    private int findXcoordinateForStringLabel(int x1, int x2, String string, Font font){
        return  (x2 - x1) / 2 - (int) (font.getStringBounds(string, new FontRenderContext(null, true, true)).getWidth() / 2) + x1;

    }

    private int findYcordinateForStringLabel(int y1, int y2, String string, Font font){
        return (y2-y1)/2  + (int) (font.getStringBounds(string, new FontRenderContext(null, true, true)).getHeight() / 4) + y1;
    }

    private void drawDottedFrame(Graphics g, int x, int y, int width, int height){
        Graphics2D g2d = (Graphics2D) g.create();
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        g2d.drawLine(x-1,y-1,x+width+1,y-1);
        g2d.drawLine(x-1,y-1,x-1,y+height+1);
        g2d.drawLine(x-1,y+height+1,x+width+1,y+height+1);
        g2d.drawLine(x+width+1,y-1,x+width+1,y+height+1);

    }

    public void b2rmp(){
        filteredImage = selectedImagePiece;
        repaint();
    }

    public void r2bmp(){
        selectedImagePiece = filteredImage;
        repaint();
    }


}
