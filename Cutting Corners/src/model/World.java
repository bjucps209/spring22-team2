package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
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
    private boolean isLoaded = false;
    private static Player Cirkyle = new Player(0, 0);



    private World(){}

    public static void reset() {
        world = null;
    }

    public static World instance(){
        if (world == null){
            world = new World();
            populate();
            
        }
        return world;
    }

    public void passLevel(){
        currentLevel++;
        campaign.get(currentLevel).placeEntity(0, 0, Cirkyle);

    }

    public Level getCurrentLevel(){
        Level current = campaign.get(currentLevel);
        return current;
    }

    public ArrayList<Entity> displayCurrentEntities(){
        Level current = campaign.get(currentLevel);
        return current.getCurrentScreen().getEntities();
    }

    public Player getPlayer(){
        for (Entity entity: displayCurrentEntities()){
            if (entity instanceof Player){return (Player) entity;}
        }
        return null;
    }

    public static void populate(){
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

        Screen screen1 = new Screen(0, 0, 1,"media/terrain/egypt/desertonewayright.png");
        Screen screen2 = new Screen(0, 1, 1,"media/terrain/egypt/desertthreewayleft.png");
        Screen screen3 = new Screen(1, 0, 1,"media/terrain/egypt/desertonewayright.png");
        Screen screen4 = new Screen(1, 1, 1,"media/terrain/egypt/desertthreewaydown.png");
        Screen bossScreen = new Screen(1, 2, 1,"media/terrain/secret&boss/bossroom.png");

        Triangle triangle1 = new Triangle(1, 1, 1, screen1);
        Triangle triangle2 = new Triangle(2, 2, 6, screen3);
        Triangle triangle3 = new Triangle(1, 1, 1, screen2);
        Triangle triangle4 = new Triangle(2, 2, 6, screen2);
        Triangle triangle5 = new Triangle(1, 1, 1, screen4);
        Triangle triangle6 = new Triangle(2, 2, 6, screen4);
        Pyramid triangleBoss = new Pyramid(11, 0, 0, bossScreen);

        UsableItem item1 = new UsableItem("Health Potion", Duration.seconds(2), 1, Duration.INDEFINITE, 0, 5, 0, "media/Player/Bow.png");
        
        DroppedItem item = new DroppedItem(200, 500, item1, "media/Player/Bow.png", 100, screen3);

        screen1.addEntity(triangle1);
        screen2.addEntity(triangle2);
        screen2.addEntity(triangle3);
        screen3.addEntity(triangle4);
        screen3.addEntity(item);
        screen4.addEntity(triangle5);
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
        level1.setCurrentScreen(level1.findScreen(0, 0));
        world.campaign.add(level1);

        Level level2 = new Level(2);

        Screen screen5 = new Screen(0, 1, 2, "media/terrain/caveman/cavemanonewayup.png");
        Screen screen6 = new Screen(1, 1, 2, "media/terrain/caveman/cavemanthreewaydown.png");
        Screen screen7 = new Screen(1, 0, 2, "media/terrain/caveman/cavemanthreewayright.png");
        Screen screen8 = new Screen(1, 2, 2, "media/terrain/caveman/cavemanthreewayleft.png");
        Screen screen9 = new Screen(0, 0, 2, "media/terrain/caveman/cavemanonewayup.png");
        Screen screen10 = new Screen(0, 2, 2, "media/terrain/caveman/cavemanonewayup.png");
        Screen screen11 = new Screen(2, 0, 2, "media/terrain/caveman/cavemantwowayvertical.png");
        Screen screen12 = new Screen(2, 2, 2, "media/terrain/caveman/cavemantwowayvertical.png");
        Screen screen13 = new Screen(3, 0, 2, "media/terrain/caveman/cavemanonewaydown.png");
        Screen screen14 = new Screen(3, 2, 2, "media/terrain/secrets&boss/bossroom.png");

        screen5.setUp(screen6);

        screen6.setDown(screen5);
        screen6.setLeft(screen7);
        screen6.setRight(screen8);

        Cirkyle = new Player(100, 100);
        level1.placeEntity(0, 0, Cirkyle);
    }

    

    public void updateView(){
        try{for (Entity entity: displayCurrentEntities()){
            if (! (entity instanceof Player)&&!isPaused){
            entity.performMovement();
            }
        }
        if(observer!=null)
        {
            observer.Initialize(isLoaded);
        }
        }catch(ConcurrentModificationException c){return;}
    }

    public void updatePlayer(){
        if(getPlayer()!=null&&!isPaused)
        {
            getPlayer().performMovement();
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

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
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
            Level lvl = getCurrentLevel();
            lvl.serialize(writer);

            
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

            Level lvl = Level.deserialize(reader);
            lvl.setCurrentScreen(lvl.findScreen(0, 0));

            this.campaign.set(currentLevel, lvl);


        }
    }
}
