package model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Effect {
    Duration duration;
    Stats effects;

    public Effect(Duration duration, Stats effects) {
        this.duration = duration;
        this.effects = effects;
    }

    public void giveEffect(Player user){
        user.getStats().ApplyBuffs(effects);
        KeyFrame frames = new KeyFrame(duration, me -> user.getStats().unApplyBuffs(effects));
        Timeline timer = new Timeline(frames);
        timer.play();
    }
}
