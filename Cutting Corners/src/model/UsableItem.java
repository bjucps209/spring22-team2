package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UsableItem extends Item{
    private int useCount;
    private int duration;
    private Stats buffs;

    public UsableItem(String name, int cooldown, int useCount, int duration, int Strength, int Health, int Speed){
        super(name, cooldown);
        this.useCount = useCount;
        this.duration = duration;
        buffs = new Stats(Strength, Health, Speed);
    }

    @Override
    public void performAction(){}




    public void serialize(DataOutputStream file) throws IOException {
        file.writeInt(useCount);
        file.writeInt(duration);
        buffs.serialize(file);
    }

    public void deserialize(DataInputStream file) throws IOException {
        this.useCount = file.readInt();
        this.duration = file.readInt();
        buffs.deserialize(file);
    }
}
