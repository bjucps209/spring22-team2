package model;

import java.util.*;

public class World {
    private ArrayList<Level> campaign = new ArrayList<Level>();
    private int currentLevel = 0;
    private int difficulty;
    private static World world;

    private World(){}

    public static World instance(){
        if (world == null){
            world = new World();
            Level currentLev = populate(new Level(2, 1));

            world.campaign.add(currentLev);
        }
        return world;
    }

    public void passLevel(){currentLevel++;}

    public Level getCurrentLevel(){
        Level current = campaign.get(currentLevel);
        return current;
    }

    public ArrayList<Entity> displayCurrentEntities(){
        Level current = campaign.get(currentLevel);
        return current.currentScreen.entities;
    }

    public static Level populate(Level emptyLevel){
        for (Screen[] row: emptyLevel.screens){
            for (Screen screen: row){
                screen = new Screen();
                screen.randomize();
            }
        }
        return emptyLevel;
        
    }
}
