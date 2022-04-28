//--------------------------------
// Stats.java
// This class defines the strength, speed, and health for the player adn enemies
// These stats are changed when damage is taken or a buff is provided
//----------------------------------
package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Stats {
    private int strength;
    private int speed;
    private IntegerProperty health = new SimpleIntegerProperty();
    
    public Stats(int strength, int speed, int health) {
        this.strength = strength;
        this.speed = speed;
        this.health.set(health);
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
        return health.get();
    }

    public void setHealth(int health) {
        this.health.set(health);
    }

    public void addHealth(int addition) {
        health.set(health.get() + addition);
    }

    public void subHealth(int damage) {
        health.set(health.get() - damage);
    }

    public IntegerProperty healthProperty(){
        return health;
    }

    public void ApplyBuffs(Stats buffs){
        strength += buffs.strength;
        addHealth(buffs.getHealth());
        speed += buffs.speed;
    }

    public void unApplyBuffs(Stats buffs){
        strength -= buffs.strength;
        subHealth(buffs.getHealth());
        speed -= buffs.speed;
    }


    /**
     * Saves the state of this class with the necessary variables to a binary file
     * @param file
     * @throws IOException
     */
    public void serialize(DataOutputStream file) throws IOException {
        file.writeInt(strength);
        file.writeInt(speed);
        file.writeInt(health.get());
    }

    
    /**
     * Factory method
     * Reads the variables left in the file by serialize.
     * Creates an instance of this class using those variables.
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static Stats deserialize(DataInputStream file) throws IOException {
        int strength = file.readInt();
        int speed = file.readInt();
        int health = file.readInt();
        Stats s = new Stats(strength, speed, health);
        return s;
    }   
}
