package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javafx.util.Duration;

public abstract class Equipment extends Item {
    private EquipmentType type;
    private Stats buffs;

    public Equipment(String name, int cooldown, EquipmentType type, Stats buffs, String image){
        super(name, cooldown, buffs, image);
        this.type = type;
    }

    public void applyBuffs(Entity user){
        if (user instanceof Enemy){
            Enemy enemy = (Enemy) user;
            enemy.getStats().ApplyBuffs(super.getBuffs());
        }
        else if (user instanceof Player){
            Player player = (Player) user;
            System.out.println(super.getBuffs()==null);
            player.getStats().ApplyBuffs(super.getBuffs());
        }
    }

    public void unApplyBuffs(Entity user){
        if (user instanceof Enemy){
            Enemy enemy = (Enemy) user;
            enemy.getStats().unApplyBuffs(super.getBuffs());
        }
        else if (user instanceof Player){
            Player player = (Player) user;
            player.getStats().unApplyBuffs(super.getBuffs());
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


    public abstract void serialize(DataOutputStream file) throws IOException;

    public static Equipment deserialize(DataInputStream file) throws IOException {
        String equipmentType = file.readUTF();
        if (equipmentType.equals("Ranged")) {
            RangedWeapon e = RangedWeapon.deserialize(file);
            return e;
        } else if (equipmentType.equals("Melee")) {
            MeleeWeapon e = MeleeWeapon.deserialize(file);
            return e;
        } else {
            Armor e = Armor.deserialize(file);
            return e;
        }
    }
}
