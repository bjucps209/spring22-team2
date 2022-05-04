//--------------------------------
// Triangle.java
// The first of many enemies the player must face
// Carries a basic sword, inherits from enemy
//----------------------------------
package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Triangle extends Enemy{
    static Equipment weapon = new MeleeWeapon("Basic Sword", 1, 1, 0, 0, 150, "media/Player/swordwalk.gif");
    static String image = "media/Enemies/triangle.png";
    static String walking = "media/Enemies/trianglewalk.gif";
    static String attacking = "media/Enemies/triangleattack.gif";

    public Triangle(int size, int xCoord, int yCoord, Screen homeScreen){
        super(3, size, xCoord, yCoord, image, homeScreen, 600, weapon, sizeToStats(size),walking,attacking,size,size*3, 10);
    }
    
    // Converts the stats based on the size of the enemy
    public static Stats sizeToStats(int size){

        // int speed = (int) 30 / size;
        int speed = 20 / size;
        int strength = (int) size / 2;
        if(strength<1)
        {
            strength=1;
        }
        int health = (int) size;
        return new Stats(strength, speed, health);
    }

    @Override
    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF("Triangle");
        file.writeInt(getSize());
        file.writeInt(getX());
        file.writeInt(getY());
    }

    public static Triangle deserialize(DataInputStream file, Screen homeScreen) throws IOException {
        int size = file.readInt();
        int x = file.readInt();
        int y = file.readInt();

        Triangle t = new Triangle(size, x, y, homeScreen);
        return t;
    }

}
