package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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



    public void serialize(DataOutputStream file) throws IOException {
    
    }

    public void deserialize(DataInputStream file) throws IOException {
        
    }
}
