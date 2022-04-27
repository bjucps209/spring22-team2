package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class World {
    private static ArrayList<Level> campaign = new ArrayList<Level>();
    public static int currentLevel = 0;
    private int difficulty;
    private static World world;
    private ScreenObserver observer;
    private boolean isPaused = false;
    private boolean cheatMode;
    private boolean isLoaded = false;
    private boolean userCampaign;
    private boolean activeBoss;
    private static Player Cirkyle = new Player(0, 0);

    public boolean isActiveBoss() {
        return activeBoss;
    }

    public void setActiveBoss(boolean activeBoss) {
        this.activeBoss = activeBoss;
    }

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
        ScreenObserver temp = getCurrentLevel().getObserver();
        currentLevel++;
        getCurrentLevel().setCurrentScreen(getCurrentLevel().findScreen(getCurrentLevel().getScreens()
        .get(0).getLocation().getRow(), getCurrentLevel().getScreens().get(0).getLocation().getCol()));
        getCurrentLevel().placeEntity(getCurrentLevel().getScreens()
        .get(0).getLocation().getRow(), getCurrentLevel().getScreens().get(0).getLocation().getCol(), Cirkyle);
        getCurrentLevel().setObserver(temp);
        //observer=temp;
        observer.Initialize(isLoaded);

    }

    public Level getCurrentLevel(){
        Level current = campaign.get(currentLevel);
        return current;
    }

    public int getNumLevels()
    {
        return campaign.size();
    }

    public static ArrayList<Entity> displayCurrentEntities(){
        Level current = campaign.get(currentLevel);
        return current.getCurrentScreen().getEntities();
    }

    public static Player getPlayer(){
        for (Entity entity: displayCurrentEntities()){
            if (entity instanceof Player){return (Player) entity;}
        }
        return null;
    }

    public static void populate(){
        if(!World.instance().getCamapign())
        {
            Level level1 = new Level(0);

            Screen screen1 = new Screen(0, 0, 1,"media/terrain/egypt/desertonewayright.png");
            Screen screen2 = new Screen(0, 1, 1,"media/terrain/egypt/desertthreewayleft.png");
            Screen screen3 = new Screen(1, 0, 1,"media/terrain/egypt/desertonewayright.png");
            Screen screen4 = new Screen(1, 1, 1,"media/terrain/egypt/desertthreewaydown.png");
            Screen bossScreen = new Screen(1, 2, 1,"media/terrain/secret&boss/bossroom.png");

            Triangle triangle1 = new Triangle(10, 1, 1, screen1);
            Triangle triangle2 = new Triangle(2, 2, 6, screen3);
            Triangle triangle3 = new Triangle(1, 1, 1, screen2);
            Triangle triangle4 = new Triangle(2, 2, 6, screen2);
            Triangle triangle5 = new Triangle(1, 1, 1, screen4);
            Triangle triangle6 = new Triangle(2, 2, 6, screen4);
            Pyramid triangleBoss = new Pyramid(11, 0, 0, bossScreen);

            UsableItem item1 = new UsableItem("Health Potion", 2, 1, 5, 0, 5, 0, "media/Player/Effects/health.png");
            UsableItem item2 = new UsableItem("Strength Potion", 2, 1, 60, 3, 0, 0, "media/Player/Effects/strength.png");
            UsableItem item3 = new UsableItem("Rage Potion", 2, 1, 45, 3, 0, 2, "media/Player/Effects/rage.png");
            UsableItem item4 = new UsableItem("Rage Potion II", 2, 1, 30, 5, -2, 3, "media/Player/Effects/rage.png");
            UsableItem item5 = new UsableItem("Ultimate Potion", 2, 1, 30, 2, 10, 3, "media/Player/Effects/ultimate.png");
            UsableItem item6 = new UsableItem("Speed Potion", 2, 1, 45, 0, 0, 4, "media/Player/Effects/speed.png");
            UsableItem item7 = new UsableItem("Stamina Potion", 2, 1, 5, 5, 62, 3, "media/Player/Effects/health.png");
            UsableItem item8 = new UsableItem("Rage Potion IV", 2, 1, 25, 8, -4, 4, "media/Player/Effects/rage.png");
            UsableItem item9 = new UsableItem("Ultimate Potion II", 2, 1, 30, 4, 12, 3, "media/Player/Effects/ultimate.png");

            
            DroppedItem itemA = new DroppedItem(200, 500, item1, "media/Player/Item.png", 100, screen3);
            DroppedItem itemB = new DroppedItem(300, 600, item2, "media/Player/Item.png", 100, screen1);
            DroppedItem itemC = new DroppedItem(300, 600, item4, "media/Player/Item.png", 100, bossScreen);
            

            screen1.addEntity(triangle1);
            screen1.addEntity(itemB);
            screen2.addEntity(triangle2);
            screen2.addEntity(triangle3);
            screen3.addEntity(triangle4);
            screen3.addEntity(itemA);
            screen4.addEntity(triangle5);
            screen4.addEntity(triangle6);
            bossScreen.addEntity(triangleBoss);
            bossScreen.addEntity(itemC);

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

            Level level2 = new Level(1);

            Screen screen5 = new Screen(0, 1, 2, "media/terrain/caveman/cavemanonewayup.png");
            Screen screen6 = new Screen(1, 1, 2, "media/terrain/caveman/cavemanthreewaydown.png");
            Screen screen7 = new Screen(1, 0, 2, "media/terrain/caveman/cavemanthreewayright.png");
            Screen screen8 = new Screen(1, 2, 2, "media/terrain/caveman/cavemanthreewayleft.png");
            Screen screen9 = new Screen(0, 0, 2, "media/terrain/caveman/cavemanonewayup.png");
            Screen screen10 = new Screen(0, 2, 2, "media/terrain/caveman/cavemanonewayup.png");
            Screen screen11 = new Screen(2, 0, 2, "media/terrain/caveman/cavemantwowayvertical.png");
            Screen screen12 = new Screen(2, 2, 2, "media/terrain/caveman/cavemantwowayvertical.png");
            Screen screen13 = new Screen(3, 0, 2, "media/terrain/caveman/cavemanonewaydown.png");
            Screen bossScreen2 = new Screen(3, 2, 2, "media/terrain/secret&boss/bossroom.png");
            
           

            screen5.setUp(screen6);

            screen6.setDown(screen5);
            screen6.setLeft(screen7);
            screen6.setRight(screen8);

            screen7.setRight(screen6);
            screen7.setDown(screen9);
            screen7.setUp(screen11);

            screen8.setLeft(screen6);
            screen8.setDown(screen10);
            screen8.setUp(screen12);

            screen9.setUp(screen7);

            screen10.setUp(screen8);

            screen11.setDown(screen7);
            screen11.setUp(screen13);

            screen12.setDown(screen8);
            screen12.setUp(bossScreen2);

            screen13.setDown(screen11);
            
            bossScreen2.setDown(screen12);

            Square square1 = new Square(4, 0, 0, screen6);
            Square square2 = new Square(4, 6, 0, screen6);
            Square square3 = new Square(4, 0, 0, screen7);
            Square square4 = new Square(4, 0, 0, screen8);
            Square square5 = new Square(4, 0, 0, screen8);
            Square square6 = new Square(4, 0, 0, screen12);
            Square square7 = new Square(4, 0, 0, screen12);
            Square square8 = new Square(6, 0, 0, screen9);
            Square square9 = new Square(6, 0, 0, screen10);
            Square square10 = new Square(6, 0, 0, screen11);
            Square square11 = new Square(4, 0, 0, screen13);
            Square square12 = new Square(4, 4, 8, screen6);
            Cube cubeBoss = new Cube(11, 0, 0, bossScreen2);

            DroppedItem itemD = new DroppedItem(300, 600, item3, "media/Player/Item.png", 100, screen6);

            screen6.addEntity(square1);
            screen6.addEntity(square2);
            screen6.addEntity(square12);
            screen6.addEntity(itemD);
            screen7.addEntity(square3);
            screen8.addEntity(square4);
            screen8.addEntity(square5);
            screen9.addEntity(square8);
            screen10.addEntity(square9);
            screen11.addEntity(square10);
            screen12.addEntity(square6);
            screen12.addEntity(square7);
            screen13.addEntity(square11);
            bossScreen2.addEntity(cubeBoss);

            level2.addScreen(screen5);
            level2.addScreen(screen6);
            level2.addScreen(screen7);
            level2.addScreen(screen8);
            level2.addScreen(screen9);
            level2.addScreen(screen10);
            level2.addScreen(screen11);
            level2.addScreen(screen12);
            level2.addScreen(screen13);
            level2.addScreen(bossScreen2);

            world.campaign.add(level2);

            Cirkyle = new Player(100, 100);
            level1.placeEntity(0, 0, Cirkyle);
        }
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
        World.currentLevel = currentLevel;
    }
    public boolean getCamapign()
    {
        return userCampaign;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    public void setCheatMode(Boolean cheatMode) 
    {
        this.cheatMode=cheatMode;
    }

    public boolean getCheatMode()
    {
        return cheatMode;
    }

    public void setCampaign(Boolean userCampaign)
    {
        this.userCampaign=userCampaign;
    }

    public static void finishGame() {
        World.instance().isPaused=true;
        HighScoreManager scores = new HighScoreManager();
        try
        {
            scores.load();
            scores.addScore(new HighScore(World.getPlayer().getScore(),"Player"));
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }


    /**
     * opens a file and calls the serialize methods for each object to write to the file
     * @param filename - the file that will hold the saved game
     */
    public void save(String filename) throws IOException {
        try (DataOutputStream writer = new DataOutputStream(new FileOutputStream(filename)))
        {  // SaveGame.dat
            
            writer.writeInt(World.currentLevel);
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

            World.currentLevel = reader.readInt();
            this.difficulty = reader.readInt();

            Level lvl = Level.deserialize(reader);
            lvl.setCurrentScreen(lvl.findScreen(0, 0));

            World.campaign.set(currentLevel, lvl);


        }
    }
}
