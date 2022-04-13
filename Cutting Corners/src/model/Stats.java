package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Stats {
    int strength;
    int speed;
    int health;
    
    public Stats(int strength, int speed, int health) {
        this.strength = strength;
        this.speed = speed;
        this.health = health;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }





    public void serialize(DataOutputStream file) throws IOException {
    
    }

    public void deserialize(DataInputStream file) throws IOException {
        
    }
}
