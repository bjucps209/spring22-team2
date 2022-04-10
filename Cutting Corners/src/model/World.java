package model;

import java.io.FilenameFilter;
import java.util.*;

public class World {
    private ArrayList<Level> campaign = new ArrayList<Level>();
    private int currentLevel = 0;
    private int difficulty;
    private static World world;

    private World(){}

    public static void reset() {
        world = new World();
    }

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




    
    /**
     * opens a file and calls the serialize methods for each object to write to the file
     * @param filename - the file that will hold the saved game
     */
    public void save(String filename) {
        //world.instance().getCurrentLevel().serialize()
        //...
            //the above would save all the instance variables from the current level to the file
    }

    /**
     * opens a file and calls the deserialize methods for each object to load the game
     * @param filename - the saved file
     */
    public void load(String filename) {
        //call objects load method methods, and then load values from file
    }
}
