package model;

import java.util.Random;

public class Coordinates {
    private int xCoord;
    private int yCoord;

    public Coordinates(int xCoord, int yCoord){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public static Coordinates randomCoords(int WIDTH, int HEIGHT){
        Random rand = new Random();
        Coordinates coords = new Coordinates(0,0);
        coords.setxCoord(rand.nextInt(WIDTH));
        coords.setyCoord(rand.nextInt(HEIGHT));
        return coords;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

}
