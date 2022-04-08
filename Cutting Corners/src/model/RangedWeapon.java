package model;

public class RangedWeapon extends Equipment{
    Buffs buffs;
    int range;
    Projectile projectile;

    public RangedWeapon(String name, int cooldown, int Strength, int Health, int Speed, int range, Projectile projectile){
        super(name, cooldown, EquipmentType.RANGED_WEAPON);
        buffs = new Buffs(Strength, Health, Speed);
        this.range = range;
        this.projectile = projectile;
    }
}
