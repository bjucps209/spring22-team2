package model;

import java.util.*;

import javafx.scene.image.Image;

public class Player extends Entity {
    ArrayList<Item> inventory;
    UsableItem equippedItem;
    EntityObserver observer;
    Equipment armor;
    Equipment weapon;
    Stats stats;
    static Image playerImage = new Image("media/Cirkyle v1.png");

    public Player(int xCoord, int yCoord){
        super(xCoord, yCoord, playerImage);
    }

}
