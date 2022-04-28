//--------------------------------
// Square.java
// One of the many enemies the player must face.
// carries a sword, inherits from enemy
//----------------------------------
package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Square extends Enemy{
    static Equipment weapon = new MeleeWeapon("Basic Sword", 1, 1, 0, 0, 150, "media/Player/swordwalk.gif");
    static String image = "media/Enemies/square.png";
    static String walking = "media/Enemies/squarewalk.gif";
    static String attacking = "media/Enemies/squareattack.gif";

    public Square(int size, int xCoord, int yCoord, Screen homeScreen){
        super(4, size, xCoord, yCoord, image, homeScreen, 600, weapon, sizeToStats(size),walking,attacking,(int)(size*1.5),size*4, 15);
    }

    public static Stats sizeToStats(int size){

        //int speed = (int) 30 / size;
        int speed = 5;
        int strength = (int) size / 3;
        int health = (int) (size * 1.5);
        return new Stats(strength, speed, health);
    }

    @Override
    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF("Square");
        file.writeInt(getSize());
        file.writeInt(getX());
        file.writeInt(getY());
    }

    public static Square deserialize(DataInputStream file, Screen homeScreen) throws IOException {
        int size = file.readInt();
        int x = file.readInt();
        int y = file.readInt();

        Square t = new Square(size, x, y, homeScreen);
        return t;
    }
}
