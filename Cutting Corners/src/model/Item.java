package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Item {
    private String name;
    private double cooldown;
    Stats buffs;
    private String Image;

    public Item(String name, double cooldown, Stats buffs, String Image){
        this.name = name;
        this.cooldown = cooldown;
        this.buffs = buffs;
    }

    public void performAction(Entity user){}

    
    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public void setBuffs(Stats buffs){
        this.buffs = buffs;
    }

    public Stats getBuffs(){
        return buffs;
    }

    public void setImage(String Image){
        this.Image = Image;
    }

    public String getImage(){
        return Image;
    }
    

    public abstract void serialize(DataOutputStream file) throws IOException;

    public static Item deserialize(DataInputStream file) throws IOException {
        String type = file.readUTF();
        if (type.equals("UsableItem")) {
            UsableItem item = UsableItem.deserialize(file);
            return item;
        } else {
            Equipment item = Equipment.deserialize(file);
            return item;
        }
    }
}
