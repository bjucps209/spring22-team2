package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class World {
    private ArrayList<Level> campaign = new ArrayList<Level>();
    public int currentLevel = 0;
    private int difficulty;
    private static World world;
    ScreenObserver observer;
    boolean isPaused = false;



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
        Screen bossScreen = new Screen(1, 2, 1);

        Triangle triangle1 = new Triangle(1, 100, 100, screen1);
        Triangle triangle2 = new Triangle(2, 200, 600, screen1);
        Triangle triangle3 = new Triangle(3, 700, 100, screen2);
        Triangle triangle4 = new Triangle(4, 300, 600, screen2);
        Triangle triangle5 = new Triangle(5, 400, 600, screen3);
        Triangle triangle6 = new Triangle(6, 200, 600, screen4);
        Pyramid triangleBoss = new Pyramid(11, 100, 100, bossScreen);

        screen1.addEntity(triangle1);
        screen1.addEntity(triangle2);
        screen2.addEntity(triangle3);
        screen2.addEntity(triangle4);
        screen3.addEntity(triangle5);
        screen4.addEntity(triangle6);
        bossScreen.addEntity(triangleBoss);

        screen1.setUp(screen3);
        screen1.setRight(screen2);

        screen2.setLeft(screen1);
        screen2.setUp(screen4);
        
        screen3.setDown(screen1);
        screen3.setRight(screen4);

        screen4.setDown(screen2);
        screen4.setLeft(screen3);
        screen4.setRight(bossScreen);

        bossScreen.setLeft(screen4);

        level1.addScreen(screen1);
        level1.addScreen(screen2);
        level1.addScreen(screen3);
        level1.addScreen(screen4);
        level1.addScreen(bossScreen);

        return level1;
    }

    

    public void updateView(){
        try{for (Entity entity: displayCurrentEntities()){
            if (! (entity instanceof Player)&&!isPaused){
            entity.performMovement();
            }
        }
        if(observer!=null)
        {
            observer.Initialize();
        }
        }catch(ConcurrentModificationException c){return;}
    }

    public void updatePlayer(){
        if(getPlayer()!=null&&!isPaused)
        {
            getPlayer().performMovement();
        }
        else
        {
            
        }
    }



    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    public int getDifficulty() {
        return difficulty;
    }
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }


    /**
     * opens a file and calls the serialize methods for each object to write to the file
     * @param filename - the file that will hold the saved game
     */
    public void save(String filename) throws IOException {
        try (DataOutputStream writer = new DataOutputStream(new FileOutputStream(filename))) 
        {  // SaveGame.dat
            writer.writeInt(this.currentLevel);
            writer.writeInt(this.difficulty);
            //getCurrentLevel().serialize(writer);

            
        }

    }

    /**
     * opens a file and calls the deserialize methods for each object to load the game
     * @param filename - the saved file
     */
    public void load(String filename) throws IOException {

        try (DataInputStream reader = new DataInputStream(new FileInputStream(filename))) 
        {   

            this.currentLevel = reader.readInt();
            this.difficulty = reader.readInt();
            //getCurrentLevel().deserialize(reader);

        }
    }
}
