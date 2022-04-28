//--------------------------------
// Effect.java
// Effects are used for the application of buffs to the player
//----------------------------------
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
        this.duration.set(duration * 50);
        this.effects = effects;
    }

    // applies any stats in @effects to the player, adds to the player's
    // @totalHealth if effects.getHealth() is greater than 50
    public void giveEffect(Player user) {
        applied = true;

        if (effects.getHealth() > 50) {
            user.setTotalHealth(user.getTotalHealth() + 5);
        }

        if (effects.getHealth() + user.getStats().getHealth() > user.getTotalHealth()) {
            user.getStats().setHealth((int) user.getTotalHealth());
            effects.setHealth(0);
        }
        user.getStats().ApplyBuffs(effects);
    }

    // removes the @effects of the Effect from the player, generally at the end of
    // their duration
    public void removeEffect(Player user) {
        applied = false;
        effects.setHealth(0);
        user.getStats().unApplyBuffs(effects);
    }

    public boolean getApplied() {
        return applied;
    }

    public void setApplied(boolean applied) {
        this.applied = applied;
    }

    public int getDuration() {
        return duration.get();
    }

    public IntegerProperty DurationProperty() {
        return duration;
    }

    public void decrementDuration() {
        duration.set(duration.get() - 1);
    }
}
