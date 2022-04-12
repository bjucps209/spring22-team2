package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Buffs {
    int strengthBuff;
    int speedBuff;
    int healthBuff;

    public Buffs(int strengthBuff, int healthBuff, int speedBuff) {
        this.strengthBuff = strengthBuff;
        this.speedBuff = speedBuff;
        this.healthBuff = healthBuff;
    }




    public void serialize(DataOutputStream file) throws IOException {
    
    }

    public void deserialize(DataInputStream file) throws IOException {
        
    }
}
