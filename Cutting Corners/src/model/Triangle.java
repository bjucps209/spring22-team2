package model;

import javafx.scene.image.Image;

public class Triangle extends Enemy{
    Equipment weapon;
    EntityObserver observer;
    Stats stats;
    Coordinates coords;
    static Image image = new Image("triangle.png");

    public Triangle(int size, int xCoord, int yCoord){
        super(3, size, xCoord, yCoord, image);
        sizeToStats();
    }

    public void sizeToStats(){stats = null;}
}
