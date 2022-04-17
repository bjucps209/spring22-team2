package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javafx.beans.property.IntegerProperty;
import javafx.scene.image.Image;

public class Entity {
    private Coordinates coords;
    private Image image;

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

    public Coordinates getCoords() {
        return coords;
    }

    public void setCoords(Coordinates coords) {
        this.coords = coords;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void performMovement(){

    }


    //methods overridden by children
    public void serialize(DataOutputStream file) throws IOException {}
    public void deserialize(DataInputStream file) throws IOException {}
}
