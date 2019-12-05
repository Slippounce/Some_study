package ru.nsu.fit.g16205.shmidt.konwaygraphic;

import ru.nsu.fit.g16205.shmidt.konwaylogic.GameField;
import ru.nsu.fit.g16205.shmidt.konwaylogic.LogicModule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Stack;

public class InitView extends JPanel{
    private int xStep;
    private int yStep;
    private final GameField gameField;
    private BufferedImage gameFieldFrame;
    private int fieldFrameHeight = 0;
    private int fieldFrameWidth = 0;
    private JPanel me = this;
    private LogicModule logicModule;
    private boolean isImpactsDisplay = true;
    public InitView(GameField gf, LogicModule module){
        this.gameField = gf;
        this.logicModule = module;
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(gameField.isGameModeReplace()) {
                    super.mouseDragged(e);
                    if (e.getX() > 0 && e.getY()>0 && e.getX() < gameFieldFrame.getWidth() && e.getY() < gameFieldFrame.getHeight() && gameFieldFrame.getRGB(e.getX(), e.getY()) != Color.BLACK.getRGB() && gameFieldFrame.getRGB(e.getX(), e.getY()) != Color.WHITE.getRGB()) {
                        Point draggedPoint = findClickedCell(e.getX(), e.getY());
                            if (!gameField.getCell(draggedPoint.y, draggedPoint.x).isAlive()) {
                                gameField.getCell(draggedPoint.y, draggedPoint.x).setCellAlive();
                                logicModule.recountCellsImpact();
                                repaint();
                            }
                    }
                }

            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getX() < fieldFrameWidth && e.getY() < fieldFrameHeight) {
                    if (gameFieldFrame.getRGB(e.getX(), e.getY()) != Color.BLACK.getRGB() && gameFieldFrame.getRGB(e.getX(), e.getY()) != Color.WHITE.getRGB()) {
                        if (gameField.isGameModeXOR()) {
                            Point point = findClickedCell(e.getX(), e.getY());
//                        System.out.println("Clicked on " + e.getX() + "," + e.getY() + " This is cell " + point.y + " , " + point.x);
                            gameField.changeLifeConditionInCertainCell(point.y, point.x);
                            logicModule.recountCellsImpact();
                            repaint();
                        }
                        if (gameField.isGameModeReplace()) {
                            Point point = findClickedCell(e.getX(), e.getY());
                            if (!gameField.getCell(point.y, point.x).isAlive()) {
                                gameField.getCell(point.y, point.x).setCellAlive();
                                repaint();
                            }
                        }
                    }
                }
            }
        });
        this.setBackground(Color.WHITE);

    }

    private Point findClickedCell(int ex, int ey){
        int i1, j1;
        Point point = new Point();
        int y1,y2;
        float sloyStepx;
        float sloyStepy;
        sloyStepx = (float)xStep/2;
        sloyStepy = (float)yStep/3;
        i1 = (int) Math.ceil((float) ey /  sloyStepy ); // номер слоя-строки
        j1 = (int) Math.ceil((((float)ex -((float)gameField.getCellSizeInPixels()-sloyStepx)) /  sloyStepx ));  // номер слоя-столбца

//        System.out.println("i1 = "+ i1+"  j1 = " +j1);

        point.x = ((int) Math.ceil((double)j1/ (double)2)) - 1; // номер столбца (m)

        if((i1-1)%3 == 0 ){//значит есть конфликт между двумя клетками
            y1 = ((int) Math.ceil((double) (i1-1)/(double)3)) - 1; // первый ряд-конкурент
            y2 = y1+1;  // второй ряд конкурент
//            System.out.println("y1 = " + y1+"  y2 = "+y2 + " point.x = "+point.x);

            int coordxCenter2;
            //version2
            int coordxCenter1 = point.x*xStep+gameField.getCellSizeInPixels();
            if(y1%2 == 0){
                if(j1 % 2 == 0){
                    coordxCenter2 = (int) (coordxCenter1+sloyStepx);
                }else{
                    coordxCenter2 = (int) (coordxCenter1-sloyStepx);
                }
            }else{
                if(j1%2 == 0){
                    coordxCenter1+=sloyStepx;
                    coordxCenter2 = (int) (coordxCenter1-sloyStepx);
                }else{
                    coordxCenter1-=sloyStepx;
                    coordxCenter2 = (int) (coordxCenter1+sloyStepx);
                }
            }

            int coordyCenter1 = y1*yStep + gameField.getCellSizeInPixels();
            int coordyCenter2 = y2*yStep + gameField.getCellSizeInPixels();

//            System.out.println("centerx1 = "+coordxCenter1+" centerY1 = "+coordyCenter1+" centerX2 = "+coordxCenter2+"  centerY2 = "+ coordyCenter2);

            if(nominalDistance(ex,ey, coordxCenter1, coordyCenter1) < nominalDistance(ex,ey, coordxCenter2,coordyCenter2)){
                point.y = y1;
            }else {
                point.y = y2;
            }

        }else{
            point.y = ((int) Math.ceil((double) (i1-1)/(double)3)) - 1;
        }
        if(point.y % 2 != 0) {
            point.x = ((int) Math.ceil((double) (j1 - 1) / (double) 2)) - 1;
        }
        return point;
    }


    private int nominalDistance(int x1, int y1, int x2, int y2){
        return /*(int) Math.sqrt(*/(int) Math.round( Math.pow(x2-x1, 2) + Math.pow(y2-y1,2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        xStep = Math.round((float) (2*gameField.getCellSizeInPixels() * Math.sin(Math.toRadians(60))));
        yStep = (Math.round((float) (3*gameField.getCellSizeInPixels() * Math.cos(Math.toRadians(60)))));

        if(gameFieldFrame == null || gameFieldFrame.getWidth() < 2*gameField.getM()*gameField.getCellSizeInPixels() || gameFieldFrame.getHeight() < 2*gameField.getN()*gameField.getCellSizeInPixels()){
            fieldFrameHeight = 2*gameField.getN()*gameField.getCellSizeInPixels();
            fieldFrameWidth = 2*gameField.getM()*gameField.getCellSizeInPixels();
            if(gameFieldFrame != null) {
                me.setPreferredSize(new Dimension(fieldFrameWidth, fieldFrameHeight));
                me.revalidate();
            }
            gameFieldFrame = new BufferedImage(fieldFrameWidth,fieldFrameHeight, BufferedImage.TYPE_INT_RGB);
        }
        Graphics2D imageGraphics = gameFieldFrame.createGraphics();
        imageGraphics.setColor(Color.WHITE);
        imageGraphics.fillRect(0,0, gameFieldFrame.getWidth(), gameFieldFrame.getHeight());
        drawGameField(imageGraphics, gameFieldFrame);
        g.drawImage(gameFieldFrame,0,0, this);

    }

    private class SpanInterval{
        private int x1;
        private int x2;
        private int y;

       SpanInterval(int x1, int x2, int y) {
           this.x1 = x1;
           this.x2 = x2;
           this.y = y;
       }
       SpanInterval() {
           this.x1 = -1;
           this.x2 = -1;
           this.y = -1;
       }
   }

    private void drawGameField(Graphics graphics, BufferedImage image){

        for(int i = 0; i < gameField.getN(); i++){
            drawRow((i%2)*(xStep/2),i*yStep+gameField.getSeparatorLineWidthInPixels(), graphics, i, image);
        }
    }

    private void drawRow(int xs, int ys, Graphics graphics, int rowNumber, BufferedImage image ){
        for(int i = 0; i < gameField.getM()-1; i++){
            drawHexagon(xs + i*xStep,ys,graphics, rowNumber , i, image);
        }
        if(rowNumber%2 == 0){
            drawHexagon(xs + (gameField.getM()-1)*xStep,ys,graphics, rowNumber, gameField.getM()-1, image);
        }
    }

    private void drawHexagon(int x, int y, Graphics g, int rowNumber, int columnNumber, BufferedImage image){
        int radius = gameField.getCellSizeInPixels();
        int[] coorX = new int[6];
        int[] coorY = new int[6];
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.BLACK);
        g2D.setStroke(new BasicStroke(gameField.getSeparatorLineWidthInPixels()));
        for (int i = 0; i < 6; i++) {
            coorX[i] = x+radius+Math.round((float) (radius * Math.sin(Math.toRadians(60 * i))));
            coorY[i] = y+radius-Math.round((float) (radius * Math.cos(Math.toRadians(60 * i))));
        }


        drawBresenhamLine(coorX[0], coorY[0], coorX[5], coorY[5],g,gameField.getSeparatorLineWidthInPixels());
        for (int i = 0; i < 5; i++) {
            drawBresenhamLine(coorX[i], coorY[i], coorX[i + 1], coorY[i + 1], g, gameField.getSeparatorLineWidthInPixels());
        }
//        if(rowNumber %2 == 0 )
//            if(rowNumber ==0 || columnNumber==0)
//        drawBresenhamLine(coorX[0], coorY[0], coorX[5], coorY[5],g,gameField.getSeparatorLineWidthInPixels());
//
//            for (int i = 0; i < 5; i++) {
//                if(i==0){
//                    if(rowNumber %2 == 0){
//                        if(rowNumber==0 || columnNumber==gameField.getM()-1)
//                        drawBresenhamLine(coorX[i], coorY[i], coorX[i + 1], coorY[i + 1], g, gameField.getSeparatorLineWidthInPixels());
//                    }
//                }else{
//                    drawBresenhamLine(coorX[i], coorY[i], coorX[i + 1], coorY[i + 1], g, gameField.getSeparatorLineWidthInPixels());
//                }
////                if(i != 0 || rowNumber%2 ==0)
////                    if(i!=2 || rowNumber%2 == 0 || rowNumber == gameField.getN()-1)
////                if(i!=0 || rowNumber%2==0 || rowNumber==0/* columnNumber!=4*/)
////                drawBresenhamLine(coorX[i], coorY[i], coorX[i + 1], coorY[i + 1], g, gameField.getSeparatorLineWidthInPixels());
//            }
////        }else{
////            drawBresenhamLine(coorX[1], coorY[1], coorX[2], coorY[2], g, gameField.getSeparatorLineWidthInPixels());
////            drawBresenhamLine(coorX[4], coorY[4], coorX[5], coorY[5], g, gameField.getSeparatorLineWidthInPixels());
//        }
        Color color = gameField.getCell(rowNumber, columnNumber).isAlive()?Color.GREEN:new Color(31, 135, 38);
        spanFill(x+radius, y+radius,color,g,image);

//        drawImpactCellValue();
        if(isImpactsDisplay) {
            int fontSize = 16;
            if (2 * gameField.getCellSizeInPixels() > fontSize * 1.5 + gameField.getSeparatorLineWidthInPixels()) {
                g2D.setColor(new Color(0, 0, 1));
                Font font = new Font("Times Roman", Font.PLAIN, fontSize);
                g2D.setFont(font);
                DecimalFormat df = new DecimalFormat("#.#");
                df.setRoundingMode(RoundingMode.HALF_UP);
                String impact = df.format(gameField.getCell(rowNumber, columnNumber).getCurrentImpact());
                g2D.drawString(impact, x + radius - (int) (font.getStringBounds(impact, new FontRenderContext(null, true, true)).getWidth() / 2), y + radius + (int) (font.getStringBounds(impact, new FontRenderContext(null, true, true)).getHeight() / 4));
                g2D.setColor(Color.BLACK);
            }
        }
    }

//    public void drawImpactCellValue(){}

    private void spanFill(int x, int y, Color colorToBePainted, Graphics g, BufferedImage image) {
        Color oldColor = new Color(image.getRGB(x,y));
        Stack<SpanInterval> spans = new Stack<>();
        spans.push(findSpanInterval(x,y,oldColor,image));
        do{
            SpanInterval span = spans.pop();
            paintSpan(span, colorToBePainted, g);
            addNewSpansToSpansStack(spans, span, oldColor, image);
        }while (!spans.empty());
    }

    private void paintSpan(SpanInterval neededToPaintSpan, Color colorForPainting, Graphics ruchka){
        Graphics2D g = (Graphics2D) ruchka;
        Color previousColor = g.getColor();
        g.setColor(colorForPainting);
        Stroke previousStroke =  g.getStroke();
        g.setStroke(new BasicStroke(1));
        g.drawLine(neededToPaintSpan.x1, neededToPaintSpan.y, neededToPaintSpan.x2, neededToPaintSpan.y);
        g.setColor(previousColor);
        g.setStroke(previousStroke);
    }


    private void addNewSpansToSpansStack(Stack<SpanInterval> spans, SpanInterval searchSpan, Color oldColor, BufferedImage image) {
        boolean isSpanFound = false;
        int x1 = 0,x2;
        for(int y = searchSpan.y-1; y < searchSpan.y+2; y+=2){
            for(int xt = searchSpan.x1+1; xt < searchSpan.x2+1; xt++ ){
                if(image.getRGB(xt,y) == oldColor.getRGB() && !isSpanFound){
                    x1 = xt;
                    isSpanFound = true;
                    continue;
                }
                if(image.getRGB(xt, y) != oldColor.getRGB() && isSpanFound){
                    x2 = xt - 1;
                    if(x1 == searchSpan.x1+1){
                        int r = x1;
                        while(image.getRGB(r, y) == oldColor.getRGB() && r>0){
                            r--;
                        }
                        if(r<0){
                            throw new RuntimeException();
                        }
                        x1 = r+1;
                    }
                    isSpanFound = false;
                    spans.push(new SpanInterval(x1,x2,y));
                }
            }
            if(isSpanFound){
                spans.push(findSpanInterval(x1,y,oldColor, image));
            }
        }
    }

    private SpanInterval findSpanInterval(int x, int y, Color color, BufferedImage image){
        int xt = x;
        SpanInterval spanInterval = new SpanInterval();
        while(image.getRGB(xt,y) == color.getRGB() && xt < this.getWidth()){
            xt++;
        }
        spanInterval.x2 = xt-1;
        spanInterval.y = y;
        xt = x-1;
        while(image.getRGB(xt,y) == color.getRGB() && xt >= 0){
            xt--;
        }
        spanInterval.x1 = xt+1;
        return spanInterval;
    }

    private int sign (int x) {
        return Integer.compare(x, 0);
    }

    private void drawBresenhamLine(int xstart, int ystart, int xend, int yend, Graphics g, int width){
        int x, y, dx, dy, incrementX, incrementY, xShift, yShift, iterationNumber, err;
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(width));
        dx = xend - xstart;
        dy = yend - ystart;
        incrementX = sign(dx);
        incrementY = sign(dy);
        if (dx < 0) dx = -dx;
        if (dy < 0) dy = -dy;
        if (dx > dy) {
            xShift = incrementX;	yShift = 0;
            iterationNumber = dx;
        }
        else{
            xShift = 0;	yShift = incrementY;
            iterationNumber = dy;
        }

        x = xstart;
        y = ystart;

        err = 0;
        g2.setColor(Color.BLACK);
        g2.drawLine(x,y,x,y);
        for (int t = 0; t < iterationNumber; t++){
            err +=  2*dy;
            if (err > dx){
                err -= 2*dx;
                x += incrementX;
                y += incrementY;
            }else{
                x += xShift;
                y += yShift;
            }
            g2.drawLine (x, y, x, y);
        }
    }

    public void changeDisplayImpactsOption(){
        if(isImpactsDisplay){
            isImpactsDisplay = false;
        }else{
            isImpactsDisplay = true;
        }
    }

}
