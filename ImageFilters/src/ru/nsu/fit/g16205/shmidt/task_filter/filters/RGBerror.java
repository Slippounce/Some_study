package ru.nsu.fit.g16205.shmidt.task_filter.filters;

public class RGBerror {
    private double red;
    private double green;
    private double blue;

    public RGBerror(){
        red = 0;
        blue = 0;
        green = 0;
    }

    public double getRed() {
        return red;
    }

    public double getGreen() {
        return green;
    }

    public double getBlue() {
        return blue;
    }

    public  void addShift(double toRed, double toGreen, double toBlue){
        red += toRed;
        green += toGreen;
        blue += toBlue;
    }
    public void reset(){
        red = 0;
        green = 0;
        blue = 0;
    }
}
