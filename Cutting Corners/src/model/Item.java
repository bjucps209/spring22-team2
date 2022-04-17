package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Item {
    private String name;
    private int cooldown;

    public Item(String name, int cooldown){
        this.name = name;
        this.cooldown = cooldown;
    }

    public void performAction(){}

    

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


    //methods overridden by children
    public void serialize(DataOutputStream file) throws IOException {}
    public void deserialize(DataInputStream file) throws IOException {}
}
