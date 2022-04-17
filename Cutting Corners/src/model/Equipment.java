package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Equipment extends Item {
    EquipmentType type;

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




    public void serialize(DataOutputStream file) throws IOException {
    
    }

    public void deserialize(DataInputStream file) throws IOException {
        
    }
}
