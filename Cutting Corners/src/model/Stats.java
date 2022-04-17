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

    public void deserialize(DataInputStream file) throws IOException {
        this.strength = file.readInt();
        this.speed = file.readInt();
        this.health = file.readInt();
    }   
}
