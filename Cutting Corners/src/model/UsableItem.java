package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UsableItem extends Item{
    private int useCount;
    private int duration;
    private Stats buffs;

    public UsableItem(String name, int cooldown, int useCount, int duration, int Strength, int Health, int Speed){
        super(name, cooldown, new Stats(Strength, Health, Speed));
        this.useCount = useCount;
        this.duration = duration;
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
    public void performAction(Entity user){}




    public void serialize(DataOutputStream file) throws IOException {
        file.writeInt(useCount);
        file.writeInt(duration);
        buffs.serialize(file);
    }

    public void deserialize(DataInputStream file) throws IOException {
        this.useCount = file.readInt();
        this.duration = file.readInt();
        buffs.deserialize(file);
    }
}
