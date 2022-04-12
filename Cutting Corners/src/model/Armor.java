package model;

public class Armor extends Equipment{

    public Armor(String name, int Strength, int Health, int Speed){
        super(name, 0, EquipmentType.ARMOR, new Stats(Strength, Health, Speed));
    }

}
