package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Swing extends Entity{
    private int damage;
    private int speed;
    private int radius;
    private int arc;

    public Swing(int startX, int startY, int damage, int speed, int radius, int arc) {
        super(startX, startY, null);
        this.damage = damage;
        this.speed = speed;
        this.radius = radius;
        this.arc = arc;
    }

    @Override
    public void performMovement(){}

    public Entity checkIfHit(){return null;}




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

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getArc() {
        return arc;
    }

    public void setArc(int arc) {
        this.arc = arc;
    }




    public void serialize(DataOutputStream file) throws IOException {
        file.writeInt(damage);
        file.writeInt(speed);
        file.writeInt(radius);
        file.writeInt(arc);
    }

    public void deserialize(DataInputStream file) throws IOException {
        this.damage = file.readInt();
        this.speed = file.readInt();
        this.radius = file.readInt();
        this.arc = file.readInt();
    }
}
