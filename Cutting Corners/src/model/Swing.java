package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.image.Image;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;

public class Swing extends Entity{
    private Entity swinger;
    private int damage;
    private int radius;
    private int arc;
    private double direction;
    private ArrayList<Entity> hitEntities = new ArrayList<Entity>();
    private double directionChange;
    private int speed;

    public Swing(double direction, int damage, int speed, int radius, int arc, Image image, Entity swinger) {
        super(swinger.getX(), swinger.getY(), image, 0);
        this.damage = damage;
        this.radius = radius;
        this.arc = arc;
        this.direction = direction * 180 / Math.PI;
        this.speed = speed;
        directionChange = arc * speed / 50;
        this.swinger = swinger;
    }

    @Override
    public void performMovement(){
        direction -= directionChange;
        arc -= directionChange;
        // super.coords.setxCoord(swinger.getX());
        // super.coords.setyCoord(swinger.getY());
        super.getCoords().setxCoord((int) (super.getX() - speed * Math.cos(direction * Math.PI / 180)));
        super.getCoords().setyCoord((int) (super.getY() - speed * Math.sin(direction * Math.PI / 180)));
        
        if (arc <= 0){super.performDie();}
        checkIfHit();
        World.instance().getCurrentLevel().getObserver().Initialize();
    }

    public void checkIfHit(){
        double slope = direction / 180 * Math.PI;
        double yIntercept = super.getY() - (slope * super.getX());
        
        for (Entity entity: World.instance().displayCurrentEntities()){
            int yValue = entity.getY();
            int xValue = entity.getX();
            int size = entity.getSize();

            double aValue = (slope * slope) + 1;
            double bValue = 2 * (((yIntercept - yValue) * slope) - xValue);
            double cValue = (yValue - yIntercept) * (yValue - yIntercept) + (xValue * xValue) - 
            (size * size);

            double determinant = (bValue * bValue) - (4 * aValue * cValue);

            if (determinant < 0){
                if (! hitEntities.contains(entity) && entity != swinger){
                    entity.takeDamage(damage);
                    hitEntities.add(entity);
                }
                return;
            }

            double x1 = (Math.sqrt(determinant) - bValue) / (2 * aValue) - super.getX();
            double x2 = (Math.sqrt(determinant) + bValue) / (-2 * aValue) - super.getX();

            double y1 = slope * x1 + yIntercept - super.getY();
            double y2 = slope * x2 + yIntercept - super.getY();

            double distance1 = Math.sqrt(x1 * x1 + y1 * y1);
            double distance2 = Math.sqrt(x2 * x2 + y2 * y2);

            if (distance1 > radius && distance2 > radius){return;}

            if (! hitEntities.contains(entity) && entity != swinger){
                entity.takeDamage(damage);
                hitEntities.add(entity);
            }
        }
    }




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

    // public static void deserialize(DataInputStream file) throws IOException {
    //     this.damage = file.readInt();
    //     this.speed = file.readInt();
    //     this.radius = file.readInt();
    //     this.arc = file.readInt();
    // }
}
