package model;

import java.util.*;

public class World {
    private ArrayList<Level> campaign;
    private int currentLevel = 0;
    private int difficulty;
    private static World world;

    private World(){}

    public static World instance(){
        if (world == null){world = new World();}
        return world;
    }

    public void passLevel(){currentLevel++;}

    public void displayAllEntities(){
        Level current = campaign.get(currentLevel);
        
    }
}
