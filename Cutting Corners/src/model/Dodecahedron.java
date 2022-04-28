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

    // state machine to determine the actions of the boss, whether they be resting
    // after attacking, attacking using performAttack1() 2() or 3(), or stunned
    // after a player attack
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
                            super.getObserver().changeImage("media/enemies/dodecabig2.gif", Direction.left);
                            attackCount=225;
                            state=EnemyState.stunned;
                            currentAttack=1;
                            break;
                        case 2:
                            performAttack2();
                            super.getObserver().changeImage("media/enemies/dodecadown2.gif", Direction.left);
                            attackCount=325;
                            state=EnemyState.stunned;
                            currentAttack=2;
                            break;
                        case 3:
                            performAttack3();
                            super.getObserver().changeImage("media/enemies/dodecaspin2.gif", Direction.left);
                            state=EnemyState.stunned;
                            attackCount=250;
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

    // shoots a projectile, @fireball, at the player
    @Override
    public void performAttack1()
    {
        if(World.instance().getPlayer()!=null)
        {
            if(attackCount==175)
            {
                Projectile fireball = new Projectile(7*World.instance().getDifficulty(), 5, 640, 200, 500, "media/Player/fireball.png", 2, World.instance().getPlayer().getX(), World.instance().getPlayer().getY(), 2);
                World.instance().getCurrentLevel().getCurrentScreen().addEntity(fireball);
                World.instance().getCurrentLevel().getObserver().Initialize(World.instance().isLoaded());
            }
        }
    }

    // shoots three @fireballs at the player
    @Override
    public void performAttack2()
    {
        if(World.instance().getPlayer()!=null)
        {
            if(attackCount==265)
            {
                Projectile fireball = new Projectile(2*World.instance().getDifficulty(), 5, 200, 200, 500, "media/Player/fireball.png", 2, 200, 900, 2);
                World.instance().getCurrentLevel().getCurrentScreen().addEntity(fireball);
                World.instance().getCurrentLevel().getObserver().Initialize(World.instance().isLoaded());
            }
            if(attackCount==220)
            {
                Projectile fireball = new Projectile(2*World.instance().getDifficulty(), 5, 1000, 200, 500, "media/Player/fireball.png", 2, 1000, 900, 2);
                World.instance().getCurrentLevel().getCurrentScreen().addEntity(fireball);
                World.instance().getCurrentLevel().getObserver().Initialize(World.instance().isLoaded());
            }
            if(attackCount==175)
            {
                Projectile fireball = new Projectile(2*World.instance().getDifficulty(), 5, 640, 200, 500, "media/Player/fireball.png", 2, 640, 900, 2);
                World.instance().getCurrentLevel().getCurrentScreen().addEntity(fireball);
                World.instance().getCurrentLevel().getObserver().Initialize(World.instance().isLoaded());
            }
        }
    }

    //shoots a lot of @fireballs at the player
    @Override
    public void performAttack3()
    {
        if(World.instance().getPlayer()!=null)
        {
            if(attackCount>175&&attackCount<225&&attackCount%5==0)
            {
                Projectile fireball = new Projectile(2*World.instance().getDifficulty(), 5, 640, 200, 500, "media/Player/fireball.png", 2, World.instance().getPlayer().getX(), World.instance().getPlayer().getY(), 2);
                World.instance().getCurrentLevel().getCurrentScreen().addEntity(fireball);
                World.instance().getCurrentLevel().getObserver().Initialize(World.instance().isLoaded());
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

