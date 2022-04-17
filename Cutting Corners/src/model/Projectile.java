package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javafx.scene.image.Image;

public class Projectile extends Entity{
    int damage;
    int speed;
    int range;
    int width;
    int direction;

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




    
    public void serialize(DataOutputStream file) throws IOException {
    
    }

    public void deserialize(DataInputStream file) throws IOException {
        
    }
}
