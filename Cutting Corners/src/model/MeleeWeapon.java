package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MeleeWeapon extends Equipment{
    private int range;

    public MeleeWeapon(String name, int cooldown, int Strength, int Health, int Speed, int range){
        super(name, cooldown, EquipmentType.MELEE_WEAPON, new Stats(Strength, Health, Speed));
        this.range = range;
    }

    @Override
    public void performAction(){}


    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }


    
    public void serialize(DataOutputStream file) throws IOException {
        file.writeInt(range);
    }

    public void deserialize(DataInputStream file) throws IOException {
        this.range = file.readInt();
    }
}
