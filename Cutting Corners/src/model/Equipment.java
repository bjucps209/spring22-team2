package model;

public class Equipment extends Item {
    EquipmentType type;
    Stats buffs;

    public Equipment(String name, int cooldown, EquipmentType type, Stats buffs){
        super(name, cooldown);
        this.type = type;
        this.buffs = buffs;
    }

    public void applyBuffs(){}

    @Override
    public void performAction() {
        // TODO Auto-generated method stub
        super.performAction();
    }
}
