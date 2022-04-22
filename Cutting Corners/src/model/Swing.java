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
    private Direction direction;

    public Swing(Direction direction, int damage, int radius, Entity swinger) {
        super(swinger.getX(), swinger.getY(), null, 0);
        this.damage = damage;
        this.radius = radius;
        this.direction = direction;
        this.swinger = swinger;
    }

    // public void checkIfHit(ArrayList<Entity> enemies, Direction direction){
    //     for(int i=0;i<enemies.size();i++)
    //         {
                
    //                 if(direction==Direction.up&&enemies.get(i).getCoords().getyCoord()>super.getY())
    //                 {
    //                     enemies.get(i).takeDamage(damage);
    //                     System.out.println("Hit!");
    //                     continue;
    //                 }
    //                 else if(direction==Direction.down&&enemies.get(i).getCoords().getyCoord()<super.getY())
    //                 {
    //                     enemies.get(i).takeDamage(damage);
    //                     System.out.println("Hit!");
    //                     continue;
    //                 }
    //                 if(direction==Direction.left&&enemies.get(i).getCoords().getxCoord()>super.getX())
    //                 {
    //                     enemies.get(i).takeDamage(damage);
    //                     System.out.println("Hit!");
    //                     continue;
    //                 }
    //                 else if(direction==Direction.right&&enemies.get(i).getCoords().getxCoord()<super.getX())
    //                 {
    //                     enemies.get(i).takeDamage(damage);
    //                     System.out.println("Hit!");
    //                     continue;
    //                 }
                
    //         }
        // double slope = direction;

        // double yIntercept = super.getY() - (slope * super.getX());
        
        // for (Entity entity: World.instance().displayCurrentEntities()){
        //     int size = entity.getSize();
        //     int yValue = entity.getY() - size / 2;
        //     int xValue = entity.getX() - size / 2;

        //     double aValue = (slope * slope) + 1;
        //     double bValue = 2 * (((yIntercept - yValue) * slope) - xValue);
        //     double cValue = (yValue - yIntercept) * (yValue - yIntercept) + (xValue * xValue) - 
        //     (size * size);

        //     double determinant = (bValue * bValue) - (4 * aValue * cValue);
            // if (determinant < 0){
            //     if (! hitEntities.contains(entity) && entity != swinger){
            //         entity.takeDamage(damage);
            //         hitEntities.add(entity);
            //     }
            //     return;
            // }
            // System.out.println(determinant);
            // if (determinant < 0){return;}

            // double x1 = (Math.sqrt(determinant) - bValue) / (2 * aValue) - super.getX();
            // double x2 = (Math.sqrt(determinant) + bValue) / (-2 * aValue) - super.getX();

            // double y1 = slope * x1 + yIntercept - super.getY();
            // double y2 = slope * x2 + yIntercept - super.getY();

            // double distance1 = Math.sqrt(x1 * x1 + y1 * y1);
            // double distance2 = Math.sqrt(x2 * x2 + y2 * y2);

            // if (distance1 > radius && distance2 > radius){return;}

            // if (! hitEntities.contains(entity) && entity != swinger){
            //     entity.takeDamage(damage);
            //     hitEntities.add(entity);
            // }
        // }
    //}




    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }



    public void serialize(DataOutputStream file) throws IOException {
        file.writeInt(damage);
        // file.writeInt(speed);
        file.writeInt(radius);
        // file.writeInt(arc);
    }

    // public static void deserialize(DataInputStream file) throws IOException {
    //     this.damage = file.readInt();
    //     this.speed = file.readInt();
    //     this.radius = file.readInt();
    //     this.arc = file.readInt();
    // }
}
