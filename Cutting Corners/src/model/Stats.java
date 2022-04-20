package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Stats {
    private int strength;
    private int speed;
    private int health;
    
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

    public void subHealth(int damage) {
        health -= damage;
    }

    public void ApplyBuffs(Item item){
        strength += item.buffs.strength;
        health += item.buffs.health;
        speed += item.buffs.speed;
    }

    public void unApplyBuffs(Item item){
        strength -= item.buffs.strength;
        health -= item.buffs.health;
        speed -= item.buffs.speed;
    }

    public void serialize(DataOutputStream file) throws IOException {
        file.writeInt(strength);
        file.writeInt(speed);
        file.writeInt(health);
    }

    
    public static Stats deserialize(DataInputStream file) throws IOException {
        int strength = file.readInt();
        int speed = file.readInt();
        int health = file.readInt();
        Stats s = new Stats(strength, speed, health)
        return s;
    }   
}
