package model;

import java.util.*;

public class Player extends Entity {
    ArrayList<Item> inventory;
    UsableItem equippedItem;
    EntityObserver observer;
    Equipment armor;
    Equipment weapon;
    Stats stats;
    Coordinates coords;


}
