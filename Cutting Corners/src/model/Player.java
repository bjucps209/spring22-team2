package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.Key;
import java.util.*;

import javax.print.attribute.standard.DialogOwner;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Player extends Entity {
    ArrayList<Item> inventory;
    UsableItem equippedItem;
    Equipment armor;
    Stats stats = new Stats(2, 5, 4);
    static Image playerImage = new Image("media/Player/Cirkyle v1.png");
    ArrayList<KeyCode> keys = new ArrayList<KeyCode>();

    public Player(int xCoord, int yCoord){
        super(xCoord, yCoord, playerImage);
    }

    public void addKey(KeyCode event){
        keys.add(event);
    }

    public void removeKey(KeyCode event){
        keys.remove(event);
    }

    @Override
    public void performMovement(){
        if (keys.size() > 0){KeyPressed(0);}
    }

    public int getKeysSize(){
        return keys.size();
    }

    public void KeyPressed(int index){
        Direction direction = CheckIfOutOfBounds();
        switch (keys.get(index)){
            case W: {
                if (keys.size() > index + 1){KeyPressed((index + 1));}
            if (direction != Direction.up){super.coords.subYCoord(stats.speed);}
                break;
            }
            case A: {
                if (keys.size() > index + 1){KeyPressed((index + 1)); }
            if (direction != Direction.left){super.coords.subXCoord(stats.speed);}
                break;
            }
            case S: {
                if (keys.size() > index + 1){KeyPressed((index + 1));} 
            if (direction != Direction.down){super.coords.addYCoord(stats.speed);}
                break;
            }
            case D: {
                if (keys.size() > index + 1){KeyPressed((index + 1));}
            if (direction != Direction.right){super.coords.addXCoord(stats.speed);}
                break;
            }
            default: return;
        }
    }

    public Direction CheckIfOutOfBounds(){
        Level currentLevel = World.instance().getCurrentLevel();
        if (super.coords.getxCoord() > 1000 && currentLevel.getCurrentScreen().getRight() != null){
            currentLevel.goRight(); 
        }
        else if (super.coords.getxCoord() > 1000){
            return Direction.right;
        }
        if (super.coords.getxCoord() < 0 && currentLevel.getCurrentScreen().getLeft() != null){
            currentLevel.goLeft(); 
        }
        else if (super.coords.getxCoord() < 0){
            return Direction.left;
        }
        if (super.coords.getyCoord() > 700 && currentLevel.getCurrentScreen().getDown() != null){
            currentLevel.goDown(); 
        }
        else if (super.coords.getyCoord() > 700){
            return Direction.down;
        }
        if (super.coords.getyCoord() < 0 && currentLevel.getCurrentScreen().getUp() != null){
            currentLevel.goUp(); 
        }
        else if (super.coords.getyCoord() < 0){
            return Direction.up;
        }
        return null;
    }



    public void serialize(DataOutputStream file) throws IOException {
        stats.serialize(file);


    }

    public void deserialize(DataInputStream file) throws IOException {
        stats.deserialize(file);

    }
}
