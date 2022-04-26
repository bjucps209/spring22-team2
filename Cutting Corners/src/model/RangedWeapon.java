package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javafx.util.Duration;

public class RangedWeapon extends Equipment{
    private int range;
    private Projectile projectile;

    public RangedWeapon(String name, Duration cooldown, int Strength, int Health, int Speed, int range, Projectile projectile, String image){
        super(name, cooldown, EquipmentType.RANGED_WEAPON, new Stats(Strength, Health, Speed), image);
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



    @Override
    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF("Ranged");
        file.writeUTF(getName());
        file.writeInt((int)getCooldown().toSeconds());
        getBuffs().serialize(file);
        
        file.writeInt(range);
        projectile.serialize(file);
        file.writeUTF(super.getImage());
    }

    public static RangedWeapon deserialize(DataInputStream file) throws IOException {
        String name = file.readUTF();
        int cooldown = file.readInt();

        Stats buffs = Stats.deserialize(file);
        int Strength = buffs.getStrength();
        int Health = buffs.getHealth();
        int Speed = buffs.getSpeed();

        int range = file.readInt();

        Projectile projectile = Projectile.deserialize(file);

        String image = file.readUTF();

        RangedWeapon r = new RangedWeapon(name, Duration.seconds(cooldown), Strength, Health, Speed, range, projectile, image);
        return r;
    }
}
