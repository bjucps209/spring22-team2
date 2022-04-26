package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class MeleeWeapon extends Equipment{
    private int range;
    private Direction direction;
    private int damage;
    private int speed;
    private String image;
    private ArrayList<Entity> enemies;

    public MeleeWeapon(String name, double cooldown, int Strength, int Speed, int Health, int range, String image){
        super(name, cooldown, EquipmentType.MELEE_WEAPON, new Stats(Strength, Speed, Health), image);
        this.range = range;
        this.image = image;
    }

    @Override
    public void performAction(Entity user){
        Swing swing = new Swing(direction, damage, range, user);
        //swing.checkIfHit(enemies, direction);
    }



    // Getters and Setters -------------------------
    public void setEnemies(ArrayList<Entity> enemies){
        this.enemies = enemies;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    public Direction getDirection(){
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


    @Override
    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF("Melee");
        file.writeUTF(getName());
        file.writeDouble(getCooldown());

        file.writeInt(this.getBuffs().getStrength());
        file.writeInt(this.getBuffs().getSpeed());
        file.writeInt(this.getBuffs().getHealth());

        file.writeInt(range);
        file.writeInt(damage);
        file.writeInt(speed);
        file.writeUTF(image);
    }

    public static MeleeWeapon deserialize(DataInputStream file) throws IOException {
        String name = file.readUTF();
        int cooldown = file.readInt();

        int Strength = file.readInt();
        int Speed = file.readInt();        
        int Health = file.readInt();

        int range = file.readInt();
        int damage = file.readInt();
        int speed = file.readInt();
        String image = file.readUTF();
        
        MeleeWeapon m = new MeleeWeapon(name, cooldown, Strength, Health, Speed, range, image);
        return m;
    }
}
