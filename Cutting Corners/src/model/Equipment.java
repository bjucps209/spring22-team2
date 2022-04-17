package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Equipment extends Item {
    private EquipmentType type;
    private Stats buffs;

    public Equipment(String name, int cooldown, EquipmentType type, Stats buffs){
        super(name, cooldown, buffs);
        this.type = type;
    }

    public void applyBuffs(Entity user){
        if (user instanceof Enemy){
            Enemy enemy = (Enemy) user;
            enemy.stats.ApplyBuffs(this);
        }
        else if (user instanceof Player){
            Player player = (Player) user;
            player.stats.ApplyBuffs(this);
        }
    }

    public void unApplyBuffs(Entity user){
        if (user instanceof Enemy){
            Enemy enemy = (Enemy) user;
            enemy.stats.unApplyBuffs(this);
        }
        else if (user instanceof Player){
            Player player = (Player) user;
            player.stats.unApplyBuffs(this);
        }
    }

    @Override
    public void performAction(Entity user) {
        super.performAction(user);
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




    @Override
    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF(this.getName());
        file.writeInt(this.getCooldown());
        file.writeUTF(type.toString()); //save the type of equipment as string
        buffs.serialize(file);
    }

    @Override
    public void deserialize(DataInputStream file) throws IOException {
        this.setName(file.readUTF());
        this.setCooldown(file.readInt());
        // this.type = file.readUTF();
        buffs.deserialize(file);
    }
}
