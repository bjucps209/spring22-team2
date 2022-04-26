package model;

public class Boss extends Enemy{
    int attackCooldown;
    int hitX = 640;
    int hitY = 700;
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
        World.instance().displayCurrentEntities().remove(this);
        World.instance().getCurrentLevel().getObserver().Initialize(World.instance().isLoaded());
        World.instance().setActiveBoss(false);
        if(!World.instance().getCamapign())
        {
            if(World.instance().getCurrentLevel().getCurrentLevel()!=World.instance().getNumLevels()-1)
            {
                World.instance().passLevel();
            }
            else
            {
                World.finishGame();
            }
        }
    }
}
