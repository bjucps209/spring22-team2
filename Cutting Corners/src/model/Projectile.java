//--------------------------------
// Projectile.java
// Projectiles inherit from entity and are thus moving entities across a screen.
// This class defines an entity that is shot from a location to a target x and y.
// Utilized as a fireball for the octagon enemy
//----------------------------------
package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Projectile extends Entity{
    private int damage;
    private int speed;
    private int range;
    private int width;
    private int targetX;
    private int targetY;

    public Projectile(int damage, int speed, int startX, int startY, int range, String image, int width, int targetX, int targetY, int size){
        super(startX, startY, image, size);
        this.speed = speed;
        this.damage = damage;
        this.range = range;
        this.width = width;
        this.targetX=targetX;
        this.targetY=targetY;
    }

    @Override
    public void performMovement()
    {
        if(Math.abs(super.getX()-targetX)>=Math.abs(super.getY()-targetY))
        {
            if(super.getX()<targetX)
            {
                super.getCoords().setxCoord(super.getX()+speed);
            }
            else
            {
                super.getCoords().setxCoord(super.getX()-speed);
            }
        }
        else
        {
            if(super.getY()<targetY)
            {
                super.getCoords().setyCoord(super.getY()+speed);
            }
            else
            {
                super.getCoords().setyCoord(super.getY()-speed);
            }
        }
        if(Math.sqrt((Math.pow(super.getX()-World.instance().getPlayer().getX(),2)+(Math.pow(super.getY()-World.instance().getPlayer().getY(),2))))<50)
        {
            World.instance().getPlayer().takeDamage(damage, Direction.left);
            this.performDie();
        }
        if(super.getX()==targetX&&super.getY()==targetY)
        {
            this.performDie();
            System.out.println("didn't reach target");
        }
    }



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


    /**
     * Saves the state of this class with the necessary variables to a binary file
     * @param file
     * @throws IOException
     */
    @Override
    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF("Projectile");
        file.writeInt(getSize());
        file.writeInt(getX());
        file.writeInt(getY());
        file.writeUTF(getImage());
        file.writeInt(damage);
        file.writeInt(speed);
        file.writeInt(range);
        file.writeInt(width);
        file.writeInt(targetX);
        file.writeInt(targetY);
    }

    public static Projectile deserialize(DataInputStream file) throws IOException {
        int size = file.readInt();
        int x = file.readInt();
        int y = file.readInt();
        String image = file.readUTF();
        int damage = file.readInt();
        int speed = file.readInt();
        int range = file.readInt();
        int width = file.readInt();
        int targetX = file.readInt();
        int targetY = file.readInt();

        Projectile p = new Projectile(damage, speed, x, y, range, image, width, targetX, targetY, size);
        return p;
    }
}
