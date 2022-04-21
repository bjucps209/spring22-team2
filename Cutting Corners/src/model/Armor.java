package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Armor extends Equipment{

    public Armor(String name, int Strength, int Health, int Speed){
        super(name, 0, EquipmentType.ARMOR, new Stats(Strength, Health, Speed));
    }

    @Override
    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF("Armor");
        file.writeUTF(getName());
        getBuffs().serialize(file);
    }

    public static Armor deserialize(DataInputStream file) throws IOException {
        String name = file.readUTF();
        Stats buffs = Stats.deserialize(file);
        int Strength = buffs.getStrength();
        int Health = buffs.getHealth();
        int Speed = buffs.getSpeed();

        Armor a = new Armor(name, Strength, Health, Speed);
        return a;
    }
}
