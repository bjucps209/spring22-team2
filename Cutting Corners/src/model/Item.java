package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Item {
    private String name;
    private int cooldown;
    Stats buffs;

    public Item(String name, int cooldown, Stats buffs){
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

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }


    public void serialize(DataOutputStream file) throws IOException {
        //method overridden by children
    }

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
