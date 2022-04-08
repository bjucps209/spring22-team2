package model;


public class UsableItem extends Item{
    boolean reusable;
    int duration;
    Buffs buffs;

    public UsableItem(String name, int cooldown, boolean reusable, int duration, int Strength, int Health, int Speed){
        super(name, cooldown);
        this.reusable = reusable;
        this.duration = duration;
        buffs = new Buffs(Strength, Health, Speed);
    }
}
