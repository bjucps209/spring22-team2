package model;

public class Cube extends Boss{
    static String image = "media/Enemies/cube.png";
    private EnemyState state = EnemyState.patrolling;
    private int attackCount = 150;
    private int currentAttack =0;

    public Cube(int size, int xCoord, int yCoord, Screen homeScreen){
        super(3, size, xCoord, yCoord, image, homeScreen, 700, new Stats(10, 9, 30), 30);
    }
    public void performMovement()
    {
        if(attackCount==149)
        {
            state=EnemyState.patrolling;
            currentAttack=0;
            super.getObserver().changeImage(image, Direction.left);
        }
        attackCount--;
        switch(state)
        {
            case patrolling:
                
                if(attackCount<=0)
                {
                    state=EnemyState.attacking;
                }
                break;
            case attacking:
                if(World.instance().getPlayer()!=null)
                {   
                    switch((int)(Math.random()*3)+1)
                    {
                        case 1:
                            performAttack1();
                            super.getObserver().changeImage("media/enemies/cubeexpand2.gif", Direction.left);
                            attackCount=225;
                            state=EnemyState.stunned;
                            currentAttack=1;
                            break;
                        case 2:
                            performAttack2();
                            super.getObserver().changeImage("media/enemies/cubeslam2.gif", Direction.left);
                            attackCount=225;
                            state=EnemyState.stunned;
                            currentAttack=2;
                            break;
                        case 3:
                            performAttack3();
                            super.getObserver().changeImage("media/enemies/cubeslide2.gif", Direction.left);
                            state=EnemyState.stunned;
                            attackCount=225;
                            currentAttack=3;
                            break;
                    }
                }
                break;
            case stunned:
                switch(currentAttack)
                {
                    case 1:
                        performAttack1();
                        break;
                    case 2:
                        performAttack2();
                        break;
                    case 3:
                        performAttack3();
                        break;
                }
                break;
        }
    }
    @Override
    public void performAttack1()
    {
        if(attackCount>150&&attackCount<190)
        {
            if(World.instance().getPlayer().getCoords().getxCoord()>500&&World.instance().getPlayer().getCoords().getxCoord()<750)
            {
                World.instance().getPlayer().takeDamage(1, Direction.left);
            }
        }
    }

    @Override
    public void performAttack2()
    {
        if(attackCount==150)
        {
            if(World.instance().getPlayer().getCoords().getxCoord()>500&&World.instance().getPlayer().getCoords().getxCoord()<750)
            {
                World.instance().getPlayer().takeDamage(10, Direction.left);
            }
        }
    }

    @Override
    public void performAttack3()
    {
        if(World.instance().getPlayer().getCoords().getyCoord()<700)
        {
            if(attackCount>195)
            {
                if(World.instance().getPlayer().getCoords().getxCoord()-(((attackCount-195)/30)*640)<10)
                {
                    World.instance().getPlayer().takeDamage(2, Direction.up);
                }
            }
            if(attackCount<195&&attackCount>170)
            {
                if(Math.abs(World.instance().getPlayer().getCoords().getxCoord()-(1280-(((attackCount-170)/25)*1280)))<10)
                {
                    World.instance().getPlayer().takeDamage(2, Direction.up);
                }
            }
            if(attackCount<170&&attackCount>150)
            {
                if(Math.abs(World.instance().getPlayer().getCoords().getxCoord()-(2*(((attackCount-150)/20)*1280)))<10)
                {
                    World.instance().getPlayer().takeDamage(2, Direction.up);
                }
            }
        }
    }
}

