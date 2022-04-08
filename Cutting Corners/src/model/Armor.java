package model;

public class Armor extends Equipment{
    Buffs buffs;

    public Armor(String name, int Strength, int Health, int Speed){
        super(name, 0, EquipmentType.ARMOR);
        buffs = new Buffs(Strength, Health, Speed);
    }
}
