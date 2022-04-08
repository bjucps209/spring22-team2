package model;

public class MeleeWeapon extends Equipment{
    Buffs buffs;
    int range;

    public MeleeWeapon(String name, int cooldown, int Strength, int Health, int Speed, int range){
        super(name, cooldown, EquipmentType.MELEE_WEAPON);
        buffs = new Buffs(Strength, Health, Speed);
        this.range = range;
    }
}
