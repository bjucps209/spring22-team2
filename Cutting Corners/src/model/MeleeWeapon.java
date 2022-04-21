package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javafx.scene.image.Image;

public class MeleeWeapon extends Equipment{
    private int range;
    private double direction;
    private int damage;
    private int speed;
    private int arc;
    private Image image;

    public MeleeWeapon(String name, int cooldown, int Strength, int Health, int Speed, int range, int arc, Image image){
        super(name, cooldown, EquipmentType.MELEE_WEAPON, new Stats(Strength, Health, Speed));
        this.range = range;
        this.arc = arc;
        this.image = image;
    }

    @Override
    public void performAction(Entity user){
        Swing swing = new Swing(direction, damage, speed, range, arc, image, user);
        World.instance().displayCurrentEntities().add(swing);
    }



    // Getters and Setters -------------------------

    public void setDirection(double direction){
        this.direction = direction;
    }

    public int getArc() {
        return arc;
    }

    public void setArc(int arc) {
        this.arc = arc;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    public Double getDirection(){
        return direction;
    }

    public int getDamage(){
        return damage;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }
    public int getSpeed() {
        return speed;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }


    
    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF("Melee");
        file.writeUTF(getName());
        file.writeInt(getCooldown());
        getBuffs().serialize(file);
        file.writeInt(range);
        file.writeDouble(direction);
        file.writeInt(damage);
        file.writeInt(arc);
    }

    public static MeleeWeapon deserialize(DataInputStream file) throws IOException {
        String name = file.readUTF();
        int cooldown = file.readInt();
        Stats buffs = Stats.deserialize(file);
        int Strength = buffs.getStrength();
        int Health = buffs.getHealth();
        int Speed = buffs.getSpeed();
        int range = file.readInt();
        double direction = file.readDouble();
        int damage = file.readInt();
        int arc = file.readInt();

        Image image = new Image("basecase.png");
        
        MeleeWeapon m = new MeleeWeapon(name, cooldown, Strength, Health, Speed, range, arc, image);
        return m;
    }
}
