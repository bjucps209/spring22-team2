package model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

public class Effect {
    IntegerProperty duration = new SimpleIntegerProperty();
    Stats effects;
    boolean applied = false;

    public Effect(int duration, Stats effects) {
        this.duration.set(duration*50);
        this.effects = effects;
    }

    public void giveEffect(Player user){
        applied = true;
        if (effects.getHealth() > 50){user.setTotalHealth(user.getTotalHealth() + 5);}
        user.getStats().ApplyBuffs(effects);
    }

    public void removeEffect(Player user){
        applied = false;
        effects.setHealth(0);
        user.getStats().unApplyBuffs(effects);
    }

    public boolean getApplied(){
        return applied;
    }

    public void setApplied(boolean applied){
        this.applied = applied;
    }

    public int getDuration(){
        return duration.get();
    }

    public IntegerProperty DurationProperty(){
        return duration;
    }

    public void decrementDuration(){
        duration.set(duration.get() - 1);
    }
}
