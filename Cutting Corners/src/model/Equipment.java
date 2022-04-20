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
    public void performAction(Entity user) {
        super.performAction(user);
    }



    // Getters and Setters ----------------------

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
        file.writeUTF("Equipment"); // type of item
        file.writeUTF(this.getName());
        file.writeInt(this.getCooldown());
        this.getBuffs().serialize(file);
        file.writeUTF(type.toString()); //save the type of equipment as string
    }

    public static Equipment deserialize(DataInputStream file) throws IOException {
        String name = file.readUTF();
        int cooldown = file.readInt();
        Stats stats = Stats.deserialize(file);
        String equipmentType = file.readUTF();
        EquipmentType type = EquipmentType.MELEE_WEAPON;
        switch (equipmentType) {
            case "RANGED_WEAPON": {
                type = EquipmentType.RANGED_WEAPON;
            }
            case "MELEE_WEAPON": {
                type = EquipmentType.MELEE_WEAPON;
            }
            case "ARMOR": {
                type = EquipmentType.ARMOR;
            }
        }

        Equipment e = new Equipment(name, cooldown, type, stats);
        return e;
    }
}
