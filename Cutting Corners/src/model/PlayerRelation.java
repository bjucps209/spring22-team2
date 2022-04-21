package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerRelation {
    int xDifference;
    int yDifference;
    double distance;
    Player player;
    Direction direction;

    public PlayerRelation(int xDifference, int yDifference, double distance, Player player) {
        this.xDifference = xDifference;
        this.yDifference = yDifference;
        this.distance = distance;
        this.player = player;
        if(Math.abs(xDifference)>Math.abs(yDifference))
        {
            if(xDifference<0)
            {
                direction=Direction.left;
            }
            else
            {
                direction=Direction.right;
            }
        }
        else
        {
            if(yDifference<0)
            {
                direction=Direction.down;
            }
            else
            {
                direction = Direction.up;
            }
        }
    }

    public int getxDifference() {
        return xDifference;
    }

    public void setxDifference(int xDifference) {
        this.xDifference = xDifference;
    }

    public int getyDifference() {
        return yDifference;
    }

    public void setyDifference(int yDifference) {
        this.yDifference = yDifference;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    public Direction getDirection()
    {
        return direction;
    }


    
   
}
