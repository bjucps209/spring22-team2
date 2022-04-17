package model;


public class UsableItem extends Item{
    int useCount;
    int duration;
    Stats buffs;

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
}
