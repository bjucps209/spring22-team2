package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javafx.scene.image.Image;

public class Triangle extends Enemy{
    static Equipment weapon = new MeleeWeapon("Basic Sword", 1, 1, 0, 0, 150, new Image("media/Player/swordwalk.gif"));
    static Image image = new Image("media/Enemies/triangle.png");
    static Image walking = new Image("media/Enemies/trianglewalk.gif");
    static Image attacking = new Image("media/Enemies/triangleattack.gif");

    public Triangle(int size, int xCoord, int yCoord, Screen homeScreen){
        super(3, size, xCoord, yCoord, image, homeScreen, 600, weapon, sizeToStats(size),walking,attacking, size);
    }

    public static Stats sizeToStats(int size){

        // int speed = (int) 30 / size;
        int speed = 20 / size;
        int strength = (int) size / 2;
        int health = (int) size;
        return new Stats(strength, speed, health);
    }


}
