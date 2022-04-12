package model;

import javafx.scene.image.Image;

public class Projectile extends Entity{
    int damage;
    int speed;
    int range;
    int width;
    int direction;

    public Projectile(int damage, int speed, int startX, int startY, int range, Image image, int width, int direction){
        super(startX, startY, image);
        this.speed = speed;
        this.damage = damage;
        this.range = range;
        this.width = width;
        this.direction = direction;
    }

    @Override
    public void performMovement(){}
    
    public Entity chickIfHit(){return null;}
}
