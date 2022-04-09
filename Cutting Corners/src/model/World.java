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
            Level currentLev = populate(new Level(2, 1), 2, 1);
            currentLev.currentScreen = currentLev.findScreen(0, 0);
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

    public Player getPlayer(){
        for (Entity entity: displayCurrentEntities()){
            if (entity instanceof Player){return (Player) entity;}
        }
        return null;
    }

    public static Level populate(Level emptyLevel, int levelSize, int currentLevel){
        int colCount = 0;
        int rowCount = -1;

        for (int i = 0; i < (levelSize * levelSize); i++){
            colCount = (colCount + 1) % levelSize;
            if (colCount == 0){rowCount++;}
            emptyLevel.screens.add(new Screen(rowCount, colCount, currentLevel));
        }

        return emptyLevel;
    }
}
