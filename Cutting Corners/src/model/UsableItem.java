//--------------------------------
// UsableItem.java
// Defines the items that can be picked up by the player and used
// Such as potions.  Inherits Item
//----------------------------------
package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.jar.Attributes.Name;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class UsableItem extends Item{
    private boolean used;
    private int useCount;
    private int duration;
    private effectCountdown countdown;
    private String Image;

    public UsableItem(String name, int cooldown, int useCount, int duration, int Strength, int Health, int Speed, String Image){
        super(name, cooldown, new Stats(Strength, Speed, Health), Image);
        this.Image = Image;
        this.useCount = useCount;
        this.duration = duration;
    }

    public void applyBuffs(Entity user, Effect effect){
        if (user instanceof Enemy){
            Enemy enemy = (Enemy) user;
            enemy.getStats().ApplyBuffs(this.getBuffs());
        }
        else if (user instanceof Player){
            Player player = (Player) user;
            

            player.addEffects(effect);
        }
    }

    public void unApplyBuffs(Entity user){
        if (user instanceof Enemy){
            Enemy enemy = (Enemy) user;
            enemy.getStats().unApplyBuffs(this.getBuffs());
        }
        else if (user instanceof Player){
            Player player = (Player) user;
            player.getStats().unApplyBuffs(this.getBuffs());
        }
    }

    @Override
    public void performAction(Entity user){
        Effect effect = new Effect(duration, super.getBuffs());
        if (used){return;}
        applyBuffs(user, effect);
        used = true;
        useCount--;
        System.out.println(effect);
        if (countdown != null){

            countdown.showEffectTimer(effect, super.getName(), Image);
        }
    }


    // Getters and Setters---------------------------

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setCountdown(effectCountdown countdown){
        this.countdown = countdown;
    }

    public effectCountdown getCountdown(){
        return countdown;
    }




    @Override
    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF("UsableItem"); //type of item
        file.writeUTF(this.getName());
        // file.writeInt(this.getCooldown());
        file.writeInt(getCooldown());
        getBuffs().serialize(file);
        file.writeInt(useCount);
        file.writeInt(duration);
        file.writeUTF(super.getImage());
    }


    public static UsableItem deserialize(DataInputStream file) throws IOException {
        String name = file.readUTF();
        int cooldown = file.readInt();
        int useCount = file.readInt();
        int duration = file.readInt();
        Buffs buffs = Buffs.deserialize(file);
        int strength = buffs.getStrengthBuff();
        int health = buffs.getHealthBuff();
        int speed = buffs.getSpeedBuff();
        String image = file.readUTF();

        UsableItem item = new UsableItem(name, cooldown, useCount, duration, strength, health, speed, image);
        return item;
    }
}
