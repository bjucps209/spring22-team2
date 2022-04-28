//--------------------------------
// Boss.java
// Boss class inherits from enemy
// Bosses are found at the end of each level to be defeated
//----------------------------------

package model;

import javafx.scene.media.AudioClip;

public class Boss extends Enemy{
    int attackCooldown;
    boolean dead = false;
    public Boss(int sides, int size, int xCoord, int yCoord, String image, Screen homeScreen, int vision, Stats stats, int totalHealth, int score){
        super(sides, size, xCoord, yCoord, image, homeScreen, vision, null, stats,image,image, totalHealth, 100, score);
    }
    @Override
    public void performMovement()
    {
        if(attackCooldown==0)
        {
            switch((int)Math.random()*3)
            {
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
        }
        else
        {
            attackCooldown--;
        }
    }
    public void performAttack1(){}

    public void performAttack2(){}

    public void performAttack3(){}
    @Override
    public void takeDamage(int damage,Direction d)
    {
        super.getStats().subHealth(damage);
        if (super.getStats().getHealth() <= 0){performDie();}
    }
    @Override
    public void performDie()
    {
            World.instance().getPlayer().addExperience(super.getScore());
            World.instance().getPlayer().addScore(super.getScore());
            World.instance().displayCurrentEntities().remove(this);
            World.instance().setActiveBoss(false);
            dead=true;
            if(!World.instance().getCamapign())
            {
                if(World.instance().getCurrentLevel().getCurrentLevel()!=World.instance().getNumLevels()-1)
                {
                    World.instance().getMusic().stop();
                    World.instance().passLevel();
                    World.instance().getCurrentLevel().getObserver().Initialize(World.instance().isLoaded());
                }
                else
                {
                    World.finishGame();
                }
            }
        
    }
}
