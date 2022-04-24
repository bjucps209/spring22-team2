package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javafx.scene.image.Image;

public class UsableItem extends Item{
    private int useCount;
    private int duration;
    private Stats buffs;

    public UsableItem(String name, int cooldown, int useCount, int duration, int Strength, int Health, int Speed, String Image){
        super(name, cooldown, new Stats(Strength, Health, Speed), Image);
        this.useCount = useCount;
        this.duration = duration;
    }

    public void applyBuffs(Entity user){
        if (user instanceof Enemy){
            Enemy enemy = (Enemy) user;
            enemy.getStats().ApplyBuffs(this);
        }
        else if (user instanceof Player){
            Player player = (Player) user;
            player.getStats().ApplyBuffs(this);
        }
    }

    public void unApplyBuffs(Entity user){
        if (user instanceof Enemy){
            Enemy enemy = (Enemy) user;
            enemy.getStats().unApplyBuffs(this);
        }
        else if (user instanceof Player){
            Player player = (Player) user;
            player.getStats().unApplyBuffs(this);
        }
    }

    @Override
    public void performAction(Entity user){}


    // Getters and Setters---------------------------

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Stats getBuffs() {
        return buffs;
    }

    public void setBuffs(Stats buffs) {
        this.buffs = buffs;
    }



    @Override
    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF("UsableItem"); //type of item
        file.writeUTF(this.getName());
        // file.writeInt(this.getCooldown());
        file.writeDouble(this.getCooldown());
        buffs.serialize(file);
        file.writeInt(useCount);
        file.writeInt(duration);
        file.writeUTF(super.getImage());
    }


    public static UsableItem deserialize(DataInputStream file) throws IOException {
        String name = file.readUTF();
        int cooldown = file.readInt();
        int useCount = file.readInt();
        int duration = file.readInt();
        Buffs buffs = Buffs.deserialize(file);
        int strength = buffs.getStrengthBuff();
        int health = buffs.getHealthBuff();
        int speed = buffs.getSpeedBuff();
        String image = file.readUTF();

        UsableItem item = new UsableItem(name, cooldown, useCount, duration, strength, health, speed, image);
        return item;
    }
}
