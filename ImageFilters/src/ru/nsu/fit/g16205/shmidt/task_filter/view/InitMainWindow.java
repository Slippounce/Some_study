package ru.nsu.fit.g16205.shmidt.task_filter.view;

import ru.nsu.fit.g16205.shmidt.task_filter.filters.*;
import ru.nsu.fit.g16205.shmidt.task_filter.forms.GammaCorrectionForm;
import ru.nsu.fit.g16205.shmidt.task_filter.forms.SliderForm;
import ru.nsu.fit.g16205.shmidt.task_filter.forms.TripleSliderForm;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class InitMainWindow extends MainFrame {

    private InitView graphicsComponent = null;
    private VolumeRenderer volumeRenderer = new VolumeRenderer();
    private ArrayList<Point> graphicAbsorptionPoints;
    private ArrayList<Point> graphicEmissionPointsRed;
    private ArrayList<Point> graphicEmissionPointsGreen;
    private ArrayList<Point> graphicEmissionPointsBlue;
    private HashMap<RenderConfigParser.MyPoint, Integer> chargeElementsMap;

    public InitMainWindow()
    {
        super(1110, 700, "Init application");

        try
        {
            addSubMenu("File", KeyEvent.VK_F);
            addMenuItem("File/Exit", "Exit application", KeyEvent.VK_X, "Exit.gif", "onExit");
            addMenuItem("File/Open", "Open image", KeyEvent.VK_O,"open.png","onOpen");
            addMenuItem("File/Save", "Save image filtered image from zone \"c\" to file", KeyEvent.VK_F, "save.png", "onSave");

            addSubMenu("Edit", KeyEvent.VK_F);
            addMenuItem("Edit/Select","Allows you to select an area of the picture", KeyEvent.VK_F , "select.png","onSelect");
            addMenuItem("Edit/Clear", "Remove images", KeyEvent.VK_F,"clear.png", "onClear");
            addMenuItem("Edit/R2Bmp", "Copy image from zone \"c\" to zone \"b\"", KeyEvent.VK_F,"left arrow.png", "R2Bmp");
            addMenuItem("Edit/B2Rmp", "Copy image from zone \"b\" to zone \"c\"", KeyEvent.VK_F, "right arrow.png", "B2Rmp");

            addSubMenu("Filters", KeyEvent.VK_F);
            addMenuItem("Filters/Emboss", "Apply emboss to zone \"b\"", KeyEvent.VK_F, "emboss.jpg", "onEmboss");
            addMenuItem("Filters/Black and White", "Convert selected image piece to black-white image", KeyEvent.VK_F,"bw.png", "onBlackWhite");
            addMenuItem("Filters/Sharper", "Make image sharper", KeyEvent.VK_F, "sharp.png", "onSharper");
            addMenuItem("Filters/Smooth", "Smooths out", KeyEvent.VK_F, "smooth.png", "onSmooth");
            addMenuItem("Filters/WaterColor", "WaterColor", KeyEvent.VK_F,"watercolor.png", "onWatercolor");
            addMenuItem("Filters/Negative", "Negative", KeyEvent.VK_F, "negative.png", "onNegative");

            addSubMenu("Filters/Find edges", KeyEvent.VK_F);
            addMenuItem("Filters/Find edges/Sobel method", "find edges", KeyEvent.VK_F, "sobel.png", "onSobel");
            addMenuItem("Filters/Find edges/Roberts method", "find edges(Roberts)", KeyEvent.VK_F, "roberts.png", "findRobertsEdges");

            addSubMenu("Filters/Dithering", KeyEvent.VK_F);
            addMenuItem("Filters/Dithering/Ordered dithering", "ordered dithering",KeyEvent.VK_F, "orddiz.gif", "orderedDithering" );
            addMenuItem("Filters/Dithering/Floyd-Steinberg", "Floyd-Steinberg dithering" , KeyEvent.VK_F, "FSdiz.png", "FSdithering");
            addMenuItem("Filters/Gamma Correction", "Gamma correction",KeyEvent.VK_F,"gamma.png", "gammaCorrection");
            addMenuItem("Filters/Rotate", "rotate", KeyEvent.VK_F, "rotate.png", "rotate");
            addMenuItem("Filters/Zoom2x", "zoom 2x", KeyEvent.VK_F, "2x.png", "zoom2x");

            addSubMenu("Filters/Rendering", KeyEvent.VK_F);
            addMenuItem("Filters/Rendering/Open Configuration", "Open config", KeyEvent.VK_F,"config.png", "openConfig");
            addMenuItem("Filters/Rendering/Absorption on \\ off", "Absorption on/off", KeyEvent.VK_F,"absorption.png", "onAbsorption");
            addMenuItem("Filters/Rendering/Emission on \\ off","Emission on/off", KeyEvent.VK_F, "emission.png", "onEmission");
            addMenuItem("Filters/Rendering/Volume Render", "volume render", KeyEvent.VK_F, "render.png", "onRender");

            addSubMenu("Help", KeyEvent.VK_H);
            addMenuItem("Help/About...", "Shows program version and copyright information", KeyEvent.VK_A, "About.gif", "onAbout");

            addToolBarButton("File/Exit");
            addToolBarButton("File/Open");
            addToolBarButton("File/Save");
            addToolBarSeparator();
            addToolBarToggledButton("Edit/Select");
            addToolBarButton("Edit/Clear");
            addToolBarButton("Edit/R2Bmp");
            addToolBarButton("Edit/B2Rmp");
            addToolBarSeparator();
            addToolBarButton("Filters/Emboss");
            addToolBarButton("Filters/Black and White");
            addToolBarButton("Filters/Sharper");
            addToolBarButton("Filters/Smooth");
            addToolBarButton("Filters/WaterColor");
            addToolBarButton("Filters/Negative");
            addToolBarButton("Filters/Find edges/Sobel method");
            addToolBarButton("Filters/Find edges/Roberts method");
            addToolBarButton("Filters/Dithering/Ordered dithering");
            addToolBarButton("Filters/Dithering/Floyd-Steinberg");
            addToolBarButton("Filters/Gamma Correction");
            addToolBarButton("Filters/Rotate");
            addToolBarButton("Filters/Zoom2x");
            addToolBarSeparator();
            addToolBarButton("Filters/Rendering/Open Configuration");
            addToolBarToggledButton("Filters/Rendering/Absorption on \\ off");
            addToolBarToggledButton("Filters/Rendering/Emission on \\ off");
            addToolBarButton("Filters/Rendering/Volume Render");
            addToolBarSeparator();
            addToolBarButton("Help/About...");
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private void setGraphicsComponent(InitView graphicsComponent) {
        this.graphicsComponent = graphicsComponent;
        graphicsComponent.setPreferredSize(new Dimension(1090, 650));
        JScrollPane pane = new JScrollPane(graphicsComponent);
        this.add(pane);
    }

    public void onAbout()
    {
        JOptionPane.showMessageDialog(this, "Application called \"VolumeRenderer\"\nCopyright 2019 Shmidt Maksim, FIT, group 16205", "About application", JOptionPane.INFORMATION_MESSAGE);
    }

    public void onClear(){
        graphicsComponent.setCurrentImage(null);
    }

    public void onExit()
    {
        System.exit(0);
    }

    public void onOpen(){
        File file = getOpenFileName(".bmp", "");
            try {
                BufferedImage image = ImageIO.read(file);
                graphicsComponent.setCurrentImage(image);
                graphicsComponent.repaint();
            } catch (IOException e) {
                System.err.println("can't read file");
                JOptionPane.showMessageDialog(this,"can't read file", "Open file", JOptionPane.INFORMATION_MESSAGE);
            }catch (IllegalArgumentException fileNull){ }

    }

    public void gammaCorrection(){
        if(graphicsComponent.getSelectedImagePiece() != null) {
            GammaCorrectionForm form = new GammaCorrectionForm(graphicsComponent, volumeRenderer,this);
            form.getjButtonApply().addActionListener(e -> {
                if(graphicsComponent.getSelectedImagePiece() != null) {
                    graphicsComponent.setFilteredImage(new GammaCorrectionFilter().getFilteredImage(graphicsComponent.getSelectedImagePiece(), (int) (100 * Double.parseDouble(form.getTextField().getText()))));
                    graphicsComponent.repaint();
                }
            });
        }
    }

    public void onSave(){
        BufferedImage savingImage = graphicsComponent.getFilteredImage();
        if(savingImage != null){
            File file = getSaveFileName(".jpg","filtered image");
            try {
                ImageIO.write(savingImage, "jpg", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(this, "Firstly apply some filters to some image", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void onSelect(){
        if(graphicsComponent.isSelectMode()){
            graphicsComponent.setPointerCursorMode();
        }else {
            graphicsComponent.setSelectCursorMode();
        }
    }

    public void R2Bmp(){
        graphicsComponent.r2bmp();
    }

    public void B2Rmp(){
        graphicsComponent.b2rmp();
    }

    public void onEmboss(){
        if(graphicsComponent.getSelectedImagePiece() != null) {
            BufferedImage bufferedImage = new EmbossFilter().getFilteredImage(graphicsComponent.getSelectedImagePiece());

            graphicsComponent.setFilteredImage(bufferedImage);
            graphicsComponent.repaint();
        }
    }

    public void onBlackWhite(){
        if(graphicsComponent.getSelectedImagePiece() != null) {
            BufferedImage bufferedImage = new BlackWhiteFilter().getFilteredImage(graphicsComponent.getSelectedImagePiece());
            graphicsComponent.setFilteredImage(bufferedImage);
            graphicsComponent.repaint();
        }
    }

    public void onSharper(){
        if(graphicsComponent.getSelectedImagePiece() != null) {
            graphicsComponent.setFilteredImage(new SharpFilter().getFilteredImage(graphicsComponent.getSelectedImagePiece()));
            graphicsComponent.repaint();
        }
    }

    public void onSmooth(){
        if(graphicsComponent.getSelectedImagePiece() != null) {
            graphicsComponent.setFilteredImage(new SmoothFilter().getFilteredImage(graphicsComponent.getSelectedImagePiece(), 1));
            graphicsComponent.repaint();
        }
    }

    public void onWatercolor(){
        if(graphicsComponent.getSelectedImagePiece() != null) {
            graphicsComponent.setFilteredImage(new WatercolorFilter().getFilteredImage(graphicsComponent.getSelectedImagePiece()));
            graphicsComponent.repaint();
        }
    }

    public void onNegative(){
        if(graphicsComponent.getSelectedImagePiece() != null) {
            graphicsComponent.setFilteredImage(new NegativeFilter().getFilteredImage(graphicsComponent.getSelectedImagePiece()));
            graphicsComponent.repaint();
        }
    }

    public void FSdithering(){
        if(graphicsComponent.getSelectedImagePiece() != null) {
            TripleSliderForm tripleSliderForm = new TripleSliderForm(this,"red:", "green:", "blue:", 2, 255, 2);
            tripleSliderForm.getjButtonApply().addActionListener(e -> {
                graphicsComponent.setFilteredImage(new FloydSteinbergDitheringFilter()
                        .getFilteredImage(graphicsComponent.getSelectedImagePiece(),
                        tripleSliderForm.getEditSliderBoxPanel1().getValue(),
                        tripleSliderForm.getEditSliderBoxPanel2().getValue(),
                        tripleSliderForm.getEditSliderBoxPanel3().getValue()));
                graphicsComponent.repaint();
            });
        }
    }

    public void orderedDithering(){
        if(graphicsComponent.getSelectedImagePiece() != null) {
            graphicsComponent.setFilteredImage(new OrderedDitheringFilter().getFilteredImage(graphicsComponent.getSelectedImagePiece()));
            graphicsComponent.repaint();
        }
    }

    public void onSobel(){
        if(graphicsComponent.getSelectedImagePiece() != null) {
            SliderForm form = new SliderForm(this,"Threshhold:", 200, 0, 1000);
            form.getjButtonApply().addActionListener(e -> {
                graphicsComponent.setFilteredImage(new SobelEdgesFilter().getFilteredImage(graphicsComponent.getSelectedImagePiece(), form.getEditSliderBoxPanel().getValue()));
            graphicsComponent.repaint();
            });
        }
    }

    public void findRobertsEdges(){
        if(graphicsComponent.getSelectedImagePiece() != null){
            SliderForm form = new SliderForm(this,"Threshhold:", 30, 0,200);
            form.getjButtonApply().addActionListener(e -> {
                graphicsComponent.setFilteredImage(new RobertsEdgesFilter().getFilteredImage(graphicsComponent.getSelectedImagePiece(), form.getEditSliderBoxPanel().getValue()));
                graphicsComponent.repaint();
            });
        }
    }

    public void rotate(){
        if(graphicsComponent.getSelectedImagePiece() != null){
            SliderForm form = new SliderForm(this,"Degree:", 90,0,180);
            form.getjButtonApply().addActionListener(e -> {
                graphicsComponent.setFilteredImage(new RotateFilter().getFilteredImage(graphicsComponent.getSelectedImagePiece(), form.getEditSliderBoxPanel().getValue()));
                graphicsComponent.repaint();
            });
        }
    }

    public void zoom2x(){
        if(graphicsComponent.getSelectedImagePiece() != null){
            BufferedImage image = graphicsComponent.getSelectedImagePiece();
            BufferedImage subImage = graphicsComponent.getSelectedImagePiece().getSubimage(image.getWidth()/4, image.getHeight()/4, image.getWidth()/2,image.getHeight()/2);
            graphicsComponent.setFilteredImage(new Zoom2xFilter().getFilteredImage(subImage));
        }
    }

    public void openConfig(){
        File file =  getOpenFileName(".txt", "render config");
        RenderConfigParser renderConfigParser = new RenderConfigParser();
        try {
            renderConfigParser.readGraphics(file);
            this.graphicAbsorptionPoints = renderConfigParser.getGraphicAbsorptionPoints();
            this.graphicEmissionPointsRed = renderConfigParser.getGraphicEmissionPointsRed();
            this.graphicEmissionPointsGreen = renderConfigParser.getGraphicEmissionPointsGreen();
            this.graphicEmissionPointsBlue = renderConfigParser.getGraphicEmissionPointsBlue();

            this.chargeElementsMap = renderConfigParser.getChargeElementsMap();
            graphicsComponent.setRenderGraphics(renderConfigParser.getGraphicAbsorptionPoints(),
                    renderConfigParser.getGraphicEmissionPointsRed(),
                    renderConfigParser.getGraphicEmissionPointsGreen(),
                    renderConfigParser.getGraphicEmissionPointsBlue());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (NumberFormatException e2){
            JOptionPane.showMessageDialog(this, "invalid config file");
        }
    }

    public void onAbsorption(){
        volumeRenderer.changeAbsorptionRenderingStatus();
    }

    public void onEmission(){
        volumeRenderer.changeEmissionRenderingStatus();
    }

    public void onRender(){
        if(chargeElementsMap != null){
            JOptionPane.showMessageDialog(this, "Open config first", "Volume rendering", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if(graphicsComponent.getSelectedImagePiece() != null){
                TripleSliderForm form = new TripleSliderForm(this,"nx:", "ny:","nz:", 1, 350,150);
            form.getjButtonApply().addActionListener(e -> {
                try {
                    graphicsComponent.setFilteredImage(volumeRenderer.getVolumeRenderedImage(graphicsComponent.getSelectedImagePiece(), chargeElementsMap, form.getEditSliderBoxPanel1().getValue(), form.getEditSliderBoxPanel2().getValue(), form.getEditSliderBoxPanel3().getValue(), graphicAbsorptionPoints, graphicEmissionPointsRed, graphicEmissionPointsGreen, graphicEmissionPointsBlue));
                    graphicsComponent.repaint();
                }catch (IllegalArgumentException ee){
                    setVisible(false);
                    JOptionPane.showMessageDialog(this,"Turn on emission or absorption to see their influence", "Volume Rendering", JOptionPane.INFORMATION_MESSAGE);
                }
            });
        }
    }

    public static void main(String[] args)
    {
        InitMainWindow mainFrame = new InitMainWindow();
        InitView initView = new InitView();
        mainFrame.setGraphicsComponent(initView);
        mainFrame.setVisible(true);
    }
}


