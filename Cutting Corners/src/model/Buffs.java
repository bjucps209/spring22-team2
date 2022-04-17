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

    public void deserialize(DataInputStream file) throws IOException {
        this.strengthBuff = file.readInt();
        this.speedBuff = file.readInt();
        this.healthBuff = file.readInt();

    }
}
