package model;

public class MeleeWeapon extends Equipment{
    int range;

    public MeleeWeapon(String name, int cooldown, int Strength, int Health, int Speed, int range){
        super(name, cooldown, EquipmentType.MELEE_WEAPON, new Stats(Strength, Health, Speed));
        this.range = range;
    }

    @Override
    public void performAction(){}
}
