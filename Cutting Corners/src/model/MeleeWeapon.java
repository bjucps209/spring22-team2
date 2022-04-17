package model;

import javafx.scene.image.Image;

public class MeleeWeapon extends Equipment{
    int range;
    double direction;
    int damage;
    int speed;
    int arc;
    Image image;

    public MeleeWeapon(String name, int cooldown, int Strength, int Health, int Speed, int range, int arc, Image image){
        super(name, cooldown, EquipmentType.MELEE_WEAPON, new Stats(Strength, Health, Speed));
        this.range = range;
        this.arc = arc;
        this.image = image;
    }

    public void setDirection(double direction){
        this.direction = direction;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    @Override
    public void performAction(Entity user){
        Swing swing = new Swing(direction, damage, speed, range, arc, image, user);
        World.instance().displayCurrentEntities().add(swing);
    }
}
