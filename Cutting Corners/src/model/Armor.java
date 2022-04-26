package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javafx.util.Duration;

public class Armor extends Equipment{

    public Armor(String name, int Strength, int Health, int Speed, String image){
        super(name, Duration.seconds(0), EquipmentType.ARMOR, new Stats(Strength, Health, Speed), image);
    }

    @Override
    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF("Armor");
        file.writeUTF(getName());
        getBuffs().serialize(file);
        file.writeUTF(super.getImage());
    }

    public static Armor deserialize(DataInputStream file) throws IOException {
        String name = file.readUTF();
        Stats buffs = Stats.deserialize(file);
        int Strength = buffs.getStrength();
        int Health = buffs.getHealth();
        int Speed = buffs.getSpeed();
        String image = file.readUTF();

        Armor a = new Armor(name, Strength, Health, Speed, image);
        return a;
    }
}
