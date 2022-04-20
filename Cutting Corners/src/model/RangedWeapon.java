package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RangedWeapon extends Equipment{
    private int range;
    private Projectile projectile;

    public RangedWeapon(String name, int cooldown, int Strength, int Health, int Speed, int range, Projectile projectile){
        super(name, cooldown, EquipmentType.RANGED_WEAPON, new Stats(Strength, Health, Speed));
        this.range = range;
        this.projectile = projectile;
    }

    @Override
    public void performAction(Entity user){}


    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public void setProjectile(Projectile projectile) {
        this.projectile = projectile;
    }



    
    public void serialize(DataOutputStream file) throws IOException {
        file.writeInt(range);
        projectile.serialize(file);
    }

    // public void deserialize(DataInputStream file) throws IOException {
    //     this.range = file.readInt();
    //     projectile.deserialize(file);
    // }
}
