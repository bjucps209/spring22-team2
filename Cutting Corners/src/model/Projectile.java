package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javafx.scene.image.Image;

public class Projectile extends Entity{
    private int damage;
    private int speed;
    private int range;
    private int width;
    private int direction;

    public Projectile(int damage, int speed, int startX, int startY, int range, Image image, int width, int direction, int size){
        super(startX, startY, image, size);
        this.speed = speed;
        this.damage = damage;
        this.range = range;
        this.width = width;
        this.direction = direction;
    }

    @Override
    public void performMovement(){}
    
    public Entity chickIfHit(){return null;}



    // Getters and Setters ---------------------

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }


    @Override
    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF("Projectile");
        file.writeInt(getSize());
        file.writeInt(getX());
        file.writeInt(getY());
        file.writeInt(damage);
        file.writeInt(speed);
        file.writeInt(range);
        file.writeInt(width);
        file.writeInt(direction);
    }

    public static Projectile deserialize(DataInputStream file) throws IOException {
        int size = file.readInt();
        int x = file.readInt();
        int y = file.readInt();
        int damage = file.readInt();
        int speed = file.readInt();
        int range = file.readInt();
        int width = file.readInt();
        int direction = file.readInt();

        Image image = new Image("basecase.png");

        Projectile p = new Projectile(damage, speed, x, y, range, image, width, direction, size);
        return p;
    }
}
