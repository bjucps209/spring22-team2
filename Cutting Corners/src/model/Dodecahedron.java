package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Dodecahedron extends Boss{
    static String image = "media/enemies/Dodeca.png";
    int attackCount =150;
    int currentAttack=0;
    EnemyState state = EnemyState.patrolling;

    public Dodecahedron(int size, int xCoord, int yCoord, Screen homeScreen){
        super(3, size, xCoord, yCoord, image, homeScreen, 700, new Stats(10, 9, 11), 11, 200);
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
                            super.getObserver().changeImage("media/enemies/dodecabig.gif", Direction.left);
                            attackCount=225;
                            state=EnemyState.stunned;
                            currentAttack=1;
                            break;
                        case 2:
                            performAttack2();
                            super.getObserver().changeImage("media/enemies/dodecadown.gif", Direction.left);
                            attackCount=225;
                            state=EnemyState.stunned;
                            currentAttack=2;
                            break;
                        case 3:
                            performAttack3();
                            super.getObserver().changeImage("media/enemies/dodecaspin.gif", Direction.left);
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
        if(World.instance().getPlayer()!=null)
        {
            if(attackCount>150&&attackCount<190)
            {
                if(World.instance().getPlayer().getCoords().getxCoord()>500&&World.instance().getPlayer().getCoords().getxCoord()<750)
                {
                    World.instance().getPlayer().takeDamage(1, Direction.left);
                }
            }
        }
    }

    @Override
    public void performAttack2()
    {
        if(World.instance().getPlayer()!=null)
        {
            if(attackCount==150)
            {
                if(World.instance().getPlayer().getCoords().getxCoord()>500&&World.instance().getPlayer().getCoords().getxCoord()<750)
                {
                    World.instance().getPlayer().takeDamage(10, Direction.left);
                }
            }
        }
    }

    @Override
    public void performAttack3()
    {
        if(World.instance().getPlayer().getCoords().getyCoord()<700)
        {
            if(World.instance().getPlayer()!=null)
            {
                if(attackCount>195)
                {
                    double temp = attackCount-195;
                    double divide = temp/30;
                    double percent = divide*640;
                    double pos = World.instance().getPlayer().getCoords().getxCoord();
                    double distance = Math.abs(pos-percent);
                    if(distance<10)
                    {
                        World.instance().getPlayer().takeDamage(2, Direction.up);
                    }
                }
                if(attackCount<195&&attackCount>170)
                {
                    double temp = attackCount-170;
                    double divide = temp/25;
                    double percent = divide*1280;
                    double dispalce = 1280-percent;
                    double pos = World.instance().getPlayer().getCoords().getxCoord();
                    double distance = Math.abs(pos-dispalce);
                    if(distance<10)
                    {
                        World.instance().getPlayer().takeDamage(2, Direction.up);
                    }
                }
                if(attackCount<170&&attackCount>150)
                {
                    double temp = attackCount-150;
                    double divide = temp/20;
                    double percent = 2*divide*1280;
                    double pos = World.instance().getPlayer().getCoords().getxCoord();
                    double distance = Math.abs(pos-percent);
                    if(distance<10)
                    {
                        World.instance().getPlayer().takeDamage(2, Direction.up);
                    }
                }
            }
        }
    }


    @Override
    public EnemyState getState()
    {
        return state;
    }

    @Override
    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF("Dodecahedron");
        file.writeInt(getSize());
        file.writeInt(getX());
        file.writeInt(getY());
    }

    public static Dodecahedron deserialize(DataInputStream file, Screen homeScreen) throws IOException {
        int size = file.readInt();
        int x = file.readInt();
        int y = file.readInt();

        Dodecahedron t = new Dodecahedron(size, x, y, homeScreen);
        return t;
    }
}

