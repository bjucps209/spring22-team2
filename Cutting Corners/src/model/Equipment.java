package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Equipment extends Item {
    private EquipmentType type;
    private Stats buffs;

    public Equipment(String name, int cooldown, EquipmentType type, Stats buffs){
        super(name, cooldown);
        this.type = type;
        this.buffs = buffs;
    }

    public void applyBuffs(){}

    @Override
    public void performAction() {
        // TODO Auto-generated method stub
        super.performAction();
    }


    public EquipmentType getType() {
        return type;
    }

    public void setType(EquipmentType type) {
        this.type = type;
    }

    public Stats getBuffs() {
        return buffs;
    }

    public void setBuffs(Stats buffs) {
        this.buffs = buffs;
    }

    public void serialize(DataOutputStream file) throws IOException {
        super.serialize(file);
        file.writeUTF(type.toString()); //save the type of equipment
        buffs.serialize(file);
    }

    public void deserialize(DataInputStream file) throws IOException {
        super.deserialize(file);
        // this.type = file.readUTF();
    }
}
