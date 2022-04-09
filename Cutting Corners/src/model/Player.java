package model;

import java.security.Key;
import java.util.*;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Player extends Entity {
    ArrayList<Item> inventory;
    UsableItem equippedItem;
    Equipment armor;
    Equipment weapon;
    Stats stats = new Stats(2, 5, 1);
    static Image playerImage = new Image("media/Cirkyle v1.png");
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
        switch (keys.get(index)){
            case W: {
                super.coords.subYCoord(stats.speed);
                if (keys.size() > index + 1){KeyPressed((index + 1));}
                // index--;}
                break;
            }
            case A: {
                super.coords.subXCoord(stats.speed);
                if (keys.size() > index + 1){KeyPressed((index + 1)); }
                // index--;}
                break;
            }
            case S: {
                super.coords.addYCoord(stats.speed);
                if (keys.size() > index + 1){KeyPressed((index + 1));} 
                // index--;}
                break;
            }
            case D: {
                super.coords.addXCoord(stats.speed);
                if (keys.size() > index + 1){KeyPressed((index + 1));}
                // index--;}
                break;
            }
            default: return;
        }
    }

    public void multipleKeysPressed(){
        
    }
}
