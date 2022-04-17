package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javafx.beans.property.IntegerProperty;
import javafx.scene.image.Image;

public class Entity {
    Coordinates coords;
    Image image;
    int size;

    public Entity(int xCoord, int yCoord, Image image, int size){
        coords = new Coordinates(xCoord, yCoord);
        this.image = image;
    }

    public int getSize(){
        return size;
    }

    public int getX(){
        return coords.getxCoord();
    }

    public int getY(){
        return coords.getyCoord();
    }

    public IntegerProperty getXProperty(){
        return coords.getXProperty();
    }

    public IntegerProperty getYProperty(){
        return coords.getYProperty();
    }

    public Image getImage(){
        return image;
    }

    public void performMovement(){}

    public void takeDamage(int damage){}

    public void performDie(){
        World.instance().displayCurrentEntities().remove(this);
        World.instance().getCurrentLevel().getObserver().Initialize();
    }

    public void performAttack(){}
    
    public void serialize(DataOutputStream file) throws IOException {
    
    }

    public void deserialize(DataInputStream file) throws IOException {
        
    }
}
