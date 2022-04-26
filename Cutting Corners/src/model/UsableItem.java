package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.jar.Attributes.Name;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class UsableItem extends Item{
    private int useCount;
    private Duration duration;
    private Stats buffs;
    private effectCountdown countdown;

    public UsableItem(String name, Duration cooldown, int useCount, Duration duration, int Strength, int Health, int Speed, String Image){
        super(name, cooldown, new Stats(Strength, Speed, Health), Image);
        this.useCount = useCount;
        this.duration = duration;
    }

    public void applyBuffs(Entity user){
        if (user instanceof Enemy){
            Enemy enemy = (Enemy) user;
            enemy.getStats().ApplyBuffs(this.getBuffs());
        }
        else if (user instanceof Player){
            Player player = (Player) user;
            if (super.buffs.getHealth() + player.getStats().getHealth() > player.getTotalHealth()){
                player.getStats().setHealth((int) player.getTotalHealth());
                super.buffs.setHealth(0);
            }
            Effect effect = new Effect(duration, super.buffs);
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
        applyBuffs(user);
        if (countdown != null){
            countdown.showEffectTimer((int)duration.toSeconds(), super.getName(), super.getImage());
        }
        KeyFrame frames = new KeyFrame(duration, me -> unApplyBuffs(user));
        Timeline duration = new Timeline(frames);
        duration.play();
    }


    // Getters and Setters---------------------------

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setCountdown(effectCountdown countdown){
        this.countdown = countdown;
    }

    public effectCountdown getCountdown(){
        return countdown;
    }

    public Stats getBuffs() {
        return buffs;
    }

    public void setBuffs(Stats buffs) {
        this.buffs = buffs;
    }



    @Override
    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF("UsableItem"); //type of item
        file.writeUTF(this.getName());
        // file.writeInt(this.getCooldown());
        file.writeInt((int)this.getCooldown().toSeconds());
        buffs.serialize(file);
        file.writeInt(useCount);
        file.writeDouble(duration.toSeconds());
        file.writeUTF(super.getImage());
    }


    public static UsableItem deserialize(DataInputStream file) throws IOException {
        String name = file.readUTF();
        int cooldown = file.readInt();
        int useCount = file.readInt();
        double duration = file.readDouble();
        Buffs buffs = Buffs.deserialize(file);
        int strength = buffs.getStrengthBuff();
        int health = buffs.getHealthBuff();
        int speed = buffs.getSpeedBuff();
        String image = file.readUTF();

        UsableItem item = new UsableItem(name, Duration.seconds(cooldown), useCount, Duration.seconds(duration), strength, health, speed, image);
        return item;
    }
}
