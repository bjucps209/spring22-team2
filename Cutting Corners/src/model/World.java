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
            Level currentLev = populate();
            currentLev.currentScreen = currentLev.findScreen(0, 0);
            world.campaign.add(currentLev);
            Player Cirkyle = new Player(100, 100);
            currentLev.placeEntity(0, 0, Cirkyle);
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

    public static Level populate(){
    //     int colCount = 0;
    //     int rowCount = -1;

        // for (int i = 0; i < (levelSize * levelSize); i++){
        //     colCount = (colCount + 1) % levelSize;
        //     if (colCount == 0){rowCount++;}
            
        //     Screen screen = new Screen(rowCount, colCount, currentLevel);

        //     try{screen.setLeft(emptyLevel.findScreen(rowCount, colCount - 1));}
        //     catch(NullPointerException n){}

        //     try{screen.setRight(emptyLevel.findScreen(rowCount, colCount + 1));}
        //     catch(NullPointerException n){}

        //     try{screen.setUp(emptyLevel.findScreen(rowCount - 1, colCount));}
        //     catch(NullPointerException n){}

        //     try{screen.setDown(emptyLevel.findScreen(rowCount + 1, colCount));}
        //     catch(NullPointerException n){}

        //     emptyLevel.screens.add(screen);
        // }

        // return emptyLevel;
        Level level1 = new Level(1);

        Screen screen1 = new Screen(0, 0, 1);
        Screen screen2 = new Screen(0, 1, 1);
        Screen screen3 = new Screen(1, 0, 1);
        Screen screen4 = new Screen(1, 1, 1);

        screen1.setUp(screen3);
        screen1.setRight(screen2);

        screen2.setLeft(screen1);
        screen2.setUp(screen4);
        
        screen3.setDown(screen1);
        screen3.setRight(screen4);

        screen4.setDown(screen2);
        screen4.setLeft(screen3);

        level1.addScreen(screen1);
        level1.addScreen(screen2);
        level1.addScreen(screen3);
        level1.addScreen(screen4);

        return level1;
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
