package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javafx.util.Duration;

public abstract class Equipment extends Item {
    private EquipmentType type;

    public Equipment(String name, int cooldown, EquipmentType type, Stats buffs, String image) {
        super(name, cooldown, buffs, image);
        this.type = type;
    }

    // applies the @buffs to its @user, calling their ApplyBuffs() methods
    public void applyBuffs(Entity user) {
        if (user instanceof Enemy) {
            Enemy enemy = (Enemy) user;
            enemy.getStats().ApplyBuffs(super.getBuffs());
        } else if (user instanceof Player) {
            Player player = (Player) user;
            player.getStats().ApplyBuffs(super.getBuffs());
        }
    }

    // unapplies the @buffs to its @user, calling their unApplyBuffs() methods
    public void unApplyBuffs(Entity user) {
        if (user instanceof Enemy) {
            Enemy enemy = (Enemy) user;
            enemy.getStats().unApplyBuffs(super.getBuffs());
        } else if (user instanceof Player) {
            Player player = (Player) user;
            player.getStats().unApplyBuffs(super.getBuffs());
        }
    }

    // Getters and Setters ----------------------

    public EquipmentType getType() {
        return type;
    }

    public void setType(EquipmentType type) {
        this.type = type;
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
