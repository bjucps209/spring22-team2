package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Circle extends Boss{
    static String image = "media/enemies/evil.png";

    public Circle(int size, int xCoord, int yCoord, Screen homeScreen){
        super(3, size, xCoord, yCoord, image, homeScreen, 700, new Stats(10, 9, 20), 20*World.instance().getDifficulty(), 500);
    }

    @Override
    public void performMovement()
    {
        PlayerRelation relation = PlayerInVision();
        if(relation!=null)
        {
            if(World.instance().getPlayer().getCoords().getxCoord()==super.getX()&&World.instance().getPlayer().getCoords().getyCoord()==super.getY())
            {
                performAttack1();
            }
            else if(Math.abs(World.instance().getPlayer().getCoords().getxCoord()-super.getX())>=Math.abs(World.instance().getPlayer().getCoords().getyCoord()-super.getY()))
            {
                super.setCoords(new Coordinates(super.getX()+super.getStats().getSpeed(), super.getY()));
            }
            else
            {
                super.setCoords(new Coordinates(super.getX(), super.getY()+super.getStats().getSpeed()));
            }
        }
    }

    public PlayerRelation PlayerInVision(){
        for (Entity entity: super.getHomeScreen().getEntities()){
            if (entity instanceof Player){
                int xDifference = super.getX() - entity.getX();
                int yDifference = super.getY() - entity.getY();
                double square = Math.pow(xDifference, 2) + Math.pow(yDifference, 2);
                double distance = Math.sqrt(square);
                return new PlayerRelation(xDifference, yDifference, distance, (Player) entity);
            }
        }
        return null;
    }

    @Override
    public void performAttack1()
    {
        World.instance().getPlayer().takeDamage(15, Direction.down);
    }

    @Override
    public void performDie()
    {
        World.instance().getPlayer().addExperience(super.getScore());
            World.instance().getPlayer().addScore(super.getScore());
            World.instance().displayCurrentEntities().remove(this);
            World.instance().setActiveBoss(false);
            dead=true;
            if(!World.instance().getCamapign()&&World.instance().getCurrentLevel().getCurrentLevel()!=0)
            {     
                World.instance().setCurrentLevel(2);
                World.instance().passLevel();
                World.instance().getCurrentLevel().getObserver().Initialize(World.instance().isLoaded());
            }
    }



    @Override
    public void serialize(DataOutputStream file) throws IOException {
        file.writeUTF("Circle");
        file.writeInt(getSize());
        file.writeInt(getX());
        file.writeInt(getY());
    }

    public static Circle deserialize(DataInputStream file, Screen homeScreen) throws IOException {
        int size = file.readInt();
        int x = file.readInt();
        int y = file.readInt();

        Circle t = new Circle(size, x, y, homeScreen);
        return t;
    }
}

