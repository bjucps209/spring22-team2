package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javafx.beans.property.IntegerProperty;
import javafx.scene.image.Image;

public class Entity {
    private int size;
    private Coordinates coords;
    private Image image;
    private imageObserver imgObserver;
    private imageFlipper imgFlipper;
    private EntityObserver observer;

    public Entity(int xCoord, int yCoord, Image image, int size){
        coords = new Coordinates(xCoord, yCoord);
        this.image = image;
    }

    public int getSize(){
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }

    public void setEventObservers(imageObserver observer, imageFlipper flipper){
        imgObserver = observer;
        imgFlipper = flipper;
    }
    public void setObserver(EntityObserver o)
    {
        observer=o;
    }

    public EntityObserver getObserver(){
        return observer;
    }

    public imageObserver getEObserver()
    {
        return imgObserver;
    }

    public imageFlipper getFlipper(){
        return imgFlipper;
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
    public Coordinates getCoords() {
        return coords;
    }

    public void setCoords(Coordinates coords) {
        this.coords = coords;
    }

    public void setImage(Image image) {
        this.image = image;
    }


    public void takeDamage(int damage, Direction direction){
    }

    public void performDie(){
        World.instance().displayCurrentEntities().remove(this);
        World.instance().getCurrentLevel().getObserver().Initialize();
    }

    public void performAttack(){}

    //methods overridden by children
    public void serialize(DataOutputStream file) throws IOException {}

    public static Entity deserialize(DataInputStream file) throws IOException {
        String type = file.readUTF();
        if (type.equals("Enemy")) {
            Entity e = Enemy.deserialize(file);
            return e;
        } else if (type.equals("Projectile")) {
            Entity p = Projectile.deserialize(file);
            return p;
        } else {
            Entity player = Player.deserialize(file);
            return player;
        }
    }
}
