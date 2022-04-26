package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Pyramid extends Boss{
    private static String image = "media/Enemies/pyramidboss.png";
    private EnemyState state = EnemyState.patrolling;
    private int attackCount = 150;
    private int experience = 100;
    private int currentAttack=0;

    public Pyramid(int size, int xCoord, int yCoord, Screen homeScreen){
        super(3, size, xCoord, yCoord, image, homeScreen, 700, new Stats(10, 9, 25), 25, 100);
    }
    @Override
    public void performMovement()
    {
        if(attackCount==149)
        {
            state=EnemyState.patrolling;
            super.getObserver().changeImage(image, Direction.left);
            currentAttack=0;
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
                            super.getObserver().changeImage("media/enemies/pyramidfloat2.gif", Direction.left);
                            attackCount=300;
                            state=EnemyState.stunned;
                            currentAttack=1;
                            break;
                        case 2:
                            performAttack2();
                            super.getObserver().changeImage("media/enemies/pyramidspike2.gif", Direction.left);
                            attackCount=225;
                            state=EnemyState.stunned;
                            currentAttack=2;
                            break;
                        case 3:
                            performAttack3();
                            super.getObserver().changeImage("media/enemies/pyramidbounce2.gif", Direction.left);
                            state=EnemyState.stunned;
                            attackCount=350;
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
        if(attackCount==250)
        {
            if(World.instance().getPlayer().getCoords().getxCoord()>100)
            {
                World.instance().getPlayer().takeDamage(5, Direction.left);
            }
        }
        if(attackCount==200)
        {
            if(World.instance().getPlayer().getCoords().getxCoord()<1000)
            {
                World.instance().getPlayer().takeDamage(5, Direction.right);
            }
        }
        if(attackCount==150)
        {
            if(World.instance().getPlayer().getCoords().getxCoord()>500&&World.instance().getPlayer().getCoords().getxCoord()<750)
            {
                World.instance().getPlayer().takeDamage(5, Direction.up);
            }
        }
    }

    @Override
    public void performAttack2()
    {
        if(attackCount==185)
        {
            if(World.instance().getPlayer().getCoords().getxCoord()>500&&World.instance().getPlayer().getCoords().getxCoord()<750)
            {
                World.instance().getPlayer().takeDamage(10, Direction.up);
            }
        }
    }

    @Override
    public void performAttack3()
    {
        if(attackCount==300)
        {
            if(World.instance().getPlayer().getCoords().getxCoord()>100)
            {
                World.instance().getPlayer().takeDamage(5, Direction.left);
            }
        }
        if(attackCount==250)
        {
            if(World.instance().getPlayer().getCoords().getxCoord()>500&&World.instance().getPlayer().getCoords().getxCoord()<750)
            {
                World.instance().getPlayer().takeDamage(5, Direction.up);
            }
        }
        if(attackCount==200)
        {
            if(World.instance().getPlayer().getCoords().getxCoord()<1000)
            {
                World.instance().getPlayer().takeDamage(5, Direction.right);
            }
        }
        if(attackCount==150)
        {
            if(World.instance().getPlayer().getCoords().getxCoord()>500&&World.instance().getPlayer().getCoords().getxCoord()<750)
            {
                World.instance().getPlayer().takeDamage(5, Direction.up);
            }
        }
    }

    @Override
    public void performDie()
    {
        System.out.println("Dead");
        World.instance().getPlayer().addExperience(experience);
        World.instance().getPlayer().addScore(experience);
        super.performDie();
    }


    @Override
    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF("Pyramid");
        file.writeInt(getSize());
        file.writeInt(getX());
        file.writeInt(getY());
    }

    public static Pyramid deserialize(DataInputStream file, Screen homeScreen) throws IOException {
        int size = file.readInt();
        int x = file.readInt();
        int y = file.readInt();

        Pyramid t = new Pyramid(size, x, y, homeScreen);
        return t;
    }
}
