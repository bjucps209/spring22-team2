package model;

import javafx.scene.image.Image;

public class Triangle extends Enemy{
    Equipment weapon;
    Stats stats;
    static Image image = new Image("media/Enemies/triangle.png");

    public Triangle(int size, int xCoord, int yCoord, Screen homeScreen){
        super(3, size, xCoord, yCoord, image, homeScreen, 600);
        sizeToStats();
    }

    public void sizeToStats(){
        stats = null;
        super.stats = stats;
    }

    

    public void complexMovement(){
        
    }
}
