package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Item {
    String name;
    int cooldown;

    public Item(String name, int cooldown){
        this.name = name;
        this.cooldown = cooldown;
    }

    public void performAction(){}



    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF(name);
        file.writeInt(cooldown);
    }

    public void deserialize(DataInputStream file) throws IOException {
        this.name = file.readUTF();
        this.cooldown = file.readInt();
    }
}
