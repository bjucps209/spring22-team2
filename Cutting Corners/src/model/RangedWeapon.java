package model;

public class RangedWeapon extends Equipment{
    int range;
    Projectile projectile;

    public RangedWeapon(String name, int cooldown, int Strength, int Health, int Speed, int range, Projectile projectile){
        super(name, cooldown, EquipmentType.RANGED_WEAPON, new Stats(Strength, Health, Speed));
        this.range = range;
        this.projectile = projectile;
    }

    @Override
    public void performAction(){}
}
