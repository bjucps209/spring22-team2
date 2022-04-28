//--------------------------------
// Buffs.java
// Buffs are given to the player when drinking a potion
// Buffs increase Stats
//----------------------------------

package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Buffs {
    private int strengthBuff;
    private int speedBuff;
    private int healthBuff;

    public Buffs(int strengthBuff, int healthBuff, int speedBuff) {
        this.strengthBuff = strengthBuff;
        this.speedBuff = speedBuff;
        this.healthBuff = healthBuff;
    }


    // Getters and Setters ----------------

    public int getStrengthBuff() {
        return strengthBuff;
    }
    public void setStrengthBuff(int strengthBuff) {
        this.strengthBuff = strengthBuff;
    }
    public int getSpeedBuff() {
        return speedBuff;
    }
    public void setSpeedBuff(int speedBuff) {
        this.speedBuff = speedBuff;
    }
    public int getHealthBuff() {
        return healthBuff;
    }
    public void setHealthBuff(int healthBuff) {
        this.healthBuff = healthBuff;
    }




    public void serialize(DataOutputStream file) throws IOException {
        file.writeInt(strengthBuff);
        file.writeInt(speedBuff);
        file.writeInt(healthBuff);
    }

    public static Buffs deserialize(DataInputStream file) throws IOException {

        int strengthBuff = file.readInt();
        int speedBuff = file.readInt();
        int healthBuff = file.readInt();

        Buffs buffs = new Buffs(strengthBuff, healthBuff, speedBuff);
        return buffs;
    }
}
