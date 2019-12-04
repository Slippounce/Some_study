package ru.nsu.fit.g16205.shmidt.task_filter.filters;

import java.nio.ByteBuffer;

class RGBintChecker {
    int getRGBint(int red, int green, int blue){
        byte[] bytes = new byte[4];
        if(red < 0){
            red = 0;
        }
        if(green < 0){
            green = 0;
        }
        if(blue < 0){
            blue = 0;
        }
        if(red > 255){
            red = 255;
        }
        if(green > 255){
            green = 255;
        }
        if(blue > 255){
            blue = 255;
        }
        bytes[0] = -1;
        bytes[1] = (byte) red;
        bytes[2] = (byte) green;
        bytes[3] = (byte) blue;
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        return bb.getInt();
    }
}
