package model;

import javafx.scene.image.Image;

public class Entity {
    Coordinates coords;
    EntityObserver observer;
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

    public EntityObserver getObserver(){
        return observer;
    }

    public void setObserver(EntityObserver observer){
        this.observer = observer;
    }

    public Image getImage(){
        return image;
    }
}
