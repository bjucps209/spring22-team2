package model;

import javafx.util.Duration;

public class Triangle extends Enemy{
    static Equipment weapon = new MeleeWeapon("Basic Sword", Duration.seconds(1), 1, 0, 0, 150, "media/Player/swordwalk.gif");
    static String image = "media/Enemies/triangle.png";
    static String walking = "media/Enemies/trianglewalk.gif";
    static String attacking = "media/Enemies/triangleattack.gif";

    public Triangle(int size, int xCoord, int yCoord, Screen homeScreen){
        super(3, size, xCoord, yCoord, image, homeScreen, 600, weapon, sizeToStats(size),walking,attacking,size,size*3, 10);
    }

    public static Stats sizeToStats(int size){

        // int speed = (int) 30 / size;
        int speed = 20 / size;
        int strength = (int) size / 2;
        int health = (int) size;
        return new Stats(strength, speed, health);
    }

}
