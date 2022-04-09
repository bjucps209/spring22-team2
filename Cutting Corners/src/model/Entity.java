package model;

import javafx.beans.property.IntegerProperty;
import javafx.scene.image.Image;

public class Entity {
    Coordinates coords;
    Image image;

    public Entity(int xCoord, int yCoord, Image image){
        coords = new Coordinates(xCoord, yCoord);
        this.image = image;
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

    public void performMovement(){

    }
}
