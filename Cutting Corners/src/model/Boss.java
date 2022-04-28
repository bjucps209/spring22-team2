package model;

import javafx.scene.media.AudioClip;

public class Boss extends Enemy {
    int attackCooldown;
    boolean dead = false;

    public Boss(int sides, int size, int xCoord, int yCoord, String image, Screen homeScreen, int vision, Stats stats,
            int totalHealth, int score) {
        super(sides, size, xCoord, yCoord, image, homeScreen, vision, null, stats, image, image, totalHealth, 100,
                score);
    }

    // performs the boss's movement based on the descendants' movements
    @Override
    public void performMovement() {
        if (attackCooldown == 0) {
            switch ((int) Math.random() * 3) {
                case 0:
                    performAttack1();
                    break;
                case 1:
                    performAttack2();
                    break;
                case 2:
                    performAttack3();
                    break;
            }
        } else {
            attackCooldown--;
        }
    }

    // empty method to perform the boss's first attack, as specified by the
    // descendant
    public void performAttack1() {
    }

    // empty method to perform the boss's second attack, as specified by the
    // descendant
    public void performAttack2() {
    }

    // empty method to perform the boss's third attack, as specified by the
    // descendant
    public void performAttack3() {
    }

    // decrements the boss's health upon an attack from the player
    @Override
    public void takeDamage(int damage, Direction d) {
        super.getStats().subHealth(damage);
        if (super.getStats().getHealth() <= 0) {
            performDie();
        }
    }

    // called when the boss's health reaches 0, removes the boss from the current
    // screen, adds @experience and @score to the player, and calls the worlds
    // passLevel() method or finishGame()
    @Override
    public void performDie() {
        World.instance().getPlayer().addExperience(super.getScore());
        World.instance().getPlayer().addScore(super.getScore());
        World.instance().displayCurrentEntities().remove(this);
        World.instance().setActiveBoss(false);
        dead = true;
        if (!World.instance().getCamapign()) {
            if (World.instance().getCurrentLevel().getCurrentLevel() != World.instance().getNumLevels() - 1) {
                World.instance().getMusic().stop();
                World.instance().passLevel();
                World.instance().getCurrentLevel().getObserver().Initialize(World.instance().isLoaded());
            } else {
                World.finishGame();
            }
        }

    }
}
