package model;

public class Octagon extends Enemy{
    static Equipment weapon = new MeleeWeapon("Basic Sword", 1, 1, 0, 0, 150, "media/Player/swordwalk.gif");
    static String image = "media/Enemies/octagon.png";
    static String walking = "media/Enemies/octagonwalk.gif";
    static String attacking = "media/Enemies/octagonattack.gif";

    public Octagon(int size, int xCoord, int yCoord, Screen homeScreen,int totalHealth){
        super(3, size, xCoord, yCoord, image, homeScreen, 600, weapon, sizeToStats(size),walking,attacking,totalHealth);
    }

    public static Stats sizeToStats(int size){

        //int speed = (int) 30 / size;
        int speed = 5;
        int strength = (int) size / 2;
        int health = (int) size;
        return new Stats(strength, speed, health);
    }
}
