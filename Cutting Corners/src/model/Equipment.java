package model;

public class Equipment extends Item {
    EquipmentType type;

    public Equipment(String name, int cooldown, EquipmentType type){
        super(name, cooldown);
        this.type = type;
    }
}
