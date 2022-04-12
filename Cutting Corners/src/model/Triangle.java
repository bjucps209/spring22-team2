package model;

import javafx.scene.image.Image;

public class Triangle extends Enemy{
    static Equipment weapon = null;
    static Image image = new Image("media/Enemies/triangle.png");

    public Triangle(int size, int xCoord, int yCoord, Screen homeScreen){
        super(3, size, xCoord, yCoord, image, homeScreen, 600, weapon, sizeToStats(size));
    }

    public static Stats sizeToStats(int size){
        return null;
    }

    public void performAttack(){}
}
