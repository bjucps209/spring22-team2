package model;


public class UsableItem extends Item{
    int useCount;
    int duration;
    Stats buffs;

    public UsableItem(String name, int cooldown, int useCount, int duration, int Strength, int Health, int Speed){
        super(name, cooldown);
        this.useCount = useCount;
        this.duration = duration;
        buffs = new Stats(Strength, Health, Speed);
    }

    @Override
    public void performAction(){}
}
