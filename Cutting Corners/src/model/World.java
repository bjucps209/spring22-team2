package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class World {
    private static ArrayList<Level> campaign = new ArrayList<Level>();
    public static int currentLevel = 1;
    private int difficulty =2;
    private static World world;
    private ScreenObserver observer;
    private boolean isPaused = false;
    private boolean cheatMode;
    private boolean isLoaded = false;
    private boolean userCampaign;
    private boolean activeBoss;
    private int levelTimer = 45000;
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

    public boolean getIsPaused() {
        return isPaused;
    }

    public void setIsPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public static World instance(){
        if (world == null){
            world = new World();
            populate();
        }
        return world;
    }

    public void passLevel(){
        if((levelTimer/60)/50>100)
        {
            World.instance().getPlayer().addScore(100);
        }
        else
        {
            World.instance().getPlayer().addScore((levelTimer/60)/50);
        }
        ScreenObserver temp;
        if(currentLevel==-1)
        {
            currentLevel=3;
            temp = getCurrentLevel().getObserver();
            currentLevel=-1;
        }
        else
        {
            temp = getCurrentLevel().getObserver();
        }
        currentLevel++;
        levelTimer=45000;
        getCurrentLevel().setCurrentScreen(getCurrentLevel().findScreen(getCurrentLevel().getScreens()
        .get(0).getLocation().getRow(), getCurrentLevel().getScreens().get(0).getLocation().getCol()));
        getCurrentLevel().placeEntity(getCurrentLevel().getScreens()
        .get(0).getLocation().getRow(), getCurrentLevel().getScreens().get(0).getLocation().getCol(), Cirkyle);
        getCurrentLevel().setObserver(temp);

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
            UsableItem item1 = new UsableItem("Health Potion", 2, 1, 5, 0, 5, 0, "media/Player/Effects/health.png");
            UsableItem item2 = new UsableItem("Strength Potion", 2, 1, 60, 3, 0, 0, "media/Player/Effects/strength.png");
            UsableItem item3 = new UsableItem("Rage Potion", 2, 1, 45, 3, 0, 2, "media/Player/Effects/rage.png");
            UsableItem item4 = new UsableItem("Rage Potion II", 2, 1, 30, 5, -2, 3, "media/Player/Effects/rage.png");
            UsableItem item5 = new UsableItem("Ultimate Potion", 2, 1, 30, 2, 10, 3, "media/Player/Effects/ultimate.png");
            UsableItem item6 = new UsableItem("Speed Potion", 2, 1, 45, 0, 0, 4, "media/Player/Effects/speed.png");
            UsableItem item7 = new UsableItem("Stamina Potion", 2, 1, 5, 5, 62, 3, "media/Player/Effects/health.png");
            UsableItem item8 = new UsableItem("Rage Potion IV", 2, 1, 25, 8, -4, 4, "media/Player/Effects/rage.png");
            UsableItem item9 = new UsableItem("Ultimate Potion II", 2, 1, 30, 4, 12, 3, "media/Player/Effects/ultimate.png");
            
            Level secretLevel = new Level(0);

            Screen ss1 = new Screen(3, 0, 0, "media/terrain/secret&boss/secretonewayright.png");
            ss1.fillGrid();
            Screen ss2 = new Screen(0, 1, 0, "media/terrain/secret&boss/secretonewayup.png");
            Screen ss3 = new Screen(6, 1, 0, "media/terrain/secret&boss/secretonewaydown.png");
            Screen ss4 = new Screen(5, 1, 0, "media/terrain/secret&boss/secretthreewayright.png");
            Screen ss5 = new Screen(4, 1, 0, "media/terrain/secret&boss/secretthreewayright.png");
            Screen ss6 = new Screen(3, 1, 0, "media/terrain/secret&boss/secretfourway.png");
            Screen ss7 = new Screen(2, 1, 0, "media/terrain/secret&boss/secretthreewayright.png");
            Screen ss8 = new Screen(1, 1, 0, "media/terrain/secret&boss/secretthreewayright.png");
            Screen ss9 = new Screen(5, 2, 0, "media/terrain/secret&boss/secretthreewaydown.png");
            Screen ss10 = new Screen(4, 2, 0, "media/terrain/secret&boss/secretfourway.png");
            Screen ss11 = new Screen(3, 2, 0, "media/terrain/secret&boss/secretfourway.png");
            Screen ss12 = new Screen(2, 2, 0, "media/terrain/secret&boss/secretfourway.png");
            Screen ss13 = new Screen(1, 2, 0, "media/terrain/secret&boss/secretthreewayup.png");
            Screen ss14 = new Screen(5, 3, 0, "media/terrain/secret&boss/secretthreewaydown.png");
            Screen ss15 = new Screen(4, 3, 0, "media/terrain/secret&boss/secretfourway.png");
            Screen ss16 = new Screen(3, 3, 0, "media/terrain/secret&boss/secretfourway.png");
            Screen ss17 = new Screen(2, 3, 0, "media/terrain/secret&boss/secretfourway.png");
            Screen ss18 = new Screen(1, 3, 0, "media/terrain/secret&boss/secretthreewayup.png");
            Screen ss19 = new Screen(5, 4, 0, "media/terrain/secret&boss/secretthreewaydown.png");
            Screen ss20 = new Screen(4, 4, 0, "media/terrain/secret&boss/secretfourway.png");
            Screen ss21 = new Screen(3, 4, 0, "media/terrain/secret&boss/secretfourway.png");
            Screen ss22 = new Screen(2, 4, 0, "media/terrain/secret&boss/secretfourway.png");
            Screen ss23 = new Screen(1, 4, 0, "media/terrain/secret&boss/secretthreewayup.png");
            Screen ss24 = new Screen(0, 5, 0, "media/terrain/secret&boss/secretonewayup.png");
            Screen ss25 = new Screen(6, 5, 0, "media/terrain/secret&boss/secretonewaydown.png");
            Screen ss26 = new Screen(5, 5, 0, "media/terrain/secret&boss/secretthreewayleft.png");
            Screen ss27 = new Screen(4, 5, 0, "media/terrain/secret&boss/secretthreewayleft.png");
            Screen ss28 = new Screen(3, 5, 0, "media/terrain/secret&boss/secretfourway.png");
            Screen ss29 = new Screen(2, 5, 0, "media/terrain/secret&boss/secretthreewayleft.png");
            Screen ss30 = new Screen(1, 5, 0, "media/terrain/secret&boss/secretthreewayleft.png");
            Screen secretBossRoom = new Screen(3, 6, 0, "media/terrain/secret&boss/bossroom.png");

            DroppedItem itemA = new DroppedItem(200, 500, item1, "media/Player/Item.png", 100, ss2);
            DroppedItem itemB = new DroppedItem(300, 600, item2, "media/Player/Item.png", 100, ss28);
            DroppedItem itemC = new DroppedItem(300, 600, item4, "media/Player/Item.png", 100, ss25);
            
            ss1.setRight(ss6);
            
            ss2.setUp(ss8);
            ss2.addEntity(itemA);
            
            ss3.setDown(ss4);

            ss4.setUp(ss3);
            ss4.setDown(ss5);
            ss4.setRight(ss9);

            ss5.setUp(ss4);
            ss5.setDown(ss6);
            ss5.setRight(ss10);

            ss6.setUp(ss5);
            ss6.setDown(ss7);
            ss6.setRight(ss11);
            ss6.setLeft(ss1);

            ss7.setUp(ss6);
            ss7.setDown(ss8);
            ss7.setRight(ss12);

            ss8.setUp(ss7);
            ss8.setDown(ss2);
            ss8.setRight(ss13);

            ss9.setDown(ss10);
            ss9.setLeft(ss4);
            ss9.setRight(ss14);

            ss10.setUp(ss9);
            ss10.setDown(ss11);
            ss10.setLeft(ss5);
            ss10.setRight(ss15);

            ss11.setUp(ss10);
            ss11.setDown(ss12);
            ss11.setLeft(ss6);
            ss11.setRight(ss16);

            ss12.setUp(ss11);
            ss12.setDown(ss13);
            ss12.setLeft(ss7);
            ss12.setRight(ss17);

            ss13.setUp(ss12);
            ss13.setLeft(ss8);
            ss13.setRight(ss18);

            ss14.setDown(ss15);
            ss14.setLeft(ss9);
            ss14.setRight(ss19);

            ss15.setUp(ss14);
            ss15.setDown(ss16);
            ss15.setLeft(ss10);
            ss15.setRight(ss20);

            ss16.setUp(ss15);
            ss16.setDown(ss17);
            ss16.setLeft(ss11);
            ss16.setRight(ss21);

            ss17.setUp(ss16);
            ss17.setDown(ss18);
            ss17.setLeft(ss12);
            ss17.setRight(ss22);

            ss18.setUp(ss17);
            ss18.setLeft(ss13);
            ss18.setRight(ss23);

            ss19.setLeft(ss14);
            ss19.setDown(ss20);
            ss19.setRight(ss26);

            ss20.setUp(ss19);
            ss20.setDown(ss21);
            ss20.setLeft(ss15);
            ss20.setRight(ss27);

            ss21.setUp(ss20);
            ss21.setDown(ss22);
            ss21.setLeft(ss16);
            ss21.setRight(ss28);

            ss22.setUp(ss21);
            ss22.setDown(ss23);
            ss22.setLeft(ss17);
            ss22.setRight(ss29);

            ss23.setUp(ss22);
            ss23.setLeft(ss18);
            ss23.setRight(ss30);

            ss24.setUp(ss30);

            ss25.setDown(ss26);
            ss25.addEntity(itemC);

            ss26.setUp(ss25);
            ss26.setDown(ss27);
            ss26.setLeft(ss19);

            ss27.setUp(ss26);
            ss27.setDown(ss28);
            ss27.setLeft(ss20);

            ss28.setUp(ss27);
            ss28.setDown(ss29);
            ss28.setLeft(ss21);
            ss28.setRight(secretBossRoom);

            ss29.setUp(ss28);
            ss29.setDown(ss29);
            ss29.setLeft(ss22);

            ss30.setUp(ss29);
            ss30.setDown(ss24);
            ss30.setLeft(ss23);

            secretBossRoom.setLeft(ss28);

            Circle secretBoss = new Circle(11, 6, 4, secretBossRoom);
            secretBossRoom.addEntity(secretBoss);

            secretLevel.addScreen(ss1);
            secretLevel.addScreen(ss2);
            secretLevel.addScreen(ss3);
            secretLevel.addScreen(ss4);
            secretLevel.addScreen(ss5);
            secretLevel.addScreen(ss6);
            secretLevel.addScreen(ss7);
            secretLevel.addScreen(ss8);
            secretLevel.addScreen(ss9);
            secretLevel.addScreen(ss10);
            secretLevel.addScreen(ss11);
            secretLevel.addScreen(ss12);
            secretLevel.addScreen(ss13);
            secretLevel.addScreen(ss14);
            secretLevel.addScreen(ss15);
            secretLevel.addScreen(ss16);
            secretLevel.addScreen(ss17);
            secretLevel.addScreen(ss18);
            secretLevel.addScreen(ss19);
            secretLevel.addScreen(ss20);
            secretLevel.addScreen(ss21);
            secretLevel.addScreen(ss22);
            secretLevel.addScreen(ss23);
            secretLevel.addScreen(ss24);
            secretLevel.addScreen(ss25);
            secretLevel.addScreen(ss26);
            secretLevel.addScreen(ss27);
            secretLevel.addScreen(ss28);
            secretLevel.addScreen(ss29);
            secretLevel.addScreen(ss30);
            secretLevel.addScreen(secretBossRoom);

            world.campaign.add(secretLevel);

            Level level1 = new Level(1);

            Screen screen1 = new Screen(0, 0, 1,"media/terrain/egypt/desertonewayright.png");
            screen1.fillGrid();
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
            

            screen1.addEntity(triangle1);
            screen3.addEntity(triangle2);
            screen2.addEntity(triangle3);
            screen2.addEntity(triangle4);
            screen1.addEntity(itemB);
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

            Level level2 = new Level(2);

            Screen screen5 = new Screen(0, 1, 2, "media/terrain/caveman/cavemanonewayup.png");
            screen5.fillGrid();
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
            Square square12 = new Square(4, 8, 4, screen6);
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

            Level level3 = new Level(3);

            Screen screen14 = new Screen(0, 2, 3, "media/terrain/medieval/medievalonewayup.png");
            screen14.fillGrid();
            Screen screen15 = new Screen(1, 2, 3, "media/terrain/medieval/medievalfourway.png");
            Screen screen16 = new Screen(1, 1, 3, "media/terrain/medieval/medievalthreewayup.png");
            Screen screen17 = new Screen(1, 3, 3, "media/terrain/medieval/medievalthreewayup.png");
            Screen screen18 = new Screen(1,0,3,"media/terrain/medieval/medievalonewayright.png");
            Screen screen19 = new Screen(1,4,3,"media/terrain/medieval/medievalonewayleft.png");
            Screen screen20 = new Screen(2,1,3,"media/terrain/medieval/medievalthreewayleft.png");
            Screen screen21 = new Screen(2,2,3,"media/terrain/medieval/medievaltwowayvertical.png");
            Screen screen22 = new Screen(2,3,3,"media/terrain/medieval/medievalthreewayright.png");
            Screen screen23 = new Screen(2,0,3,"media/terrain/medieval/medievalonewayright.png");
            Screen screen24 = new Screen(2,4,3,"media/terrain/medieval/medievalonewayleft.png");
            Screen screen25 = new Screen(3,1,3,"media/terrain/medieval/medievalonewaydown.png");
            Screen screen26 = new Screen(3,2,3,"media/terrain/medieval/medievalonewaydown.png");
            Screen bossRoom3 = new Screen(3,3,3,"media/terrain/secret&boss/bossroom.png");

            screen14.setUp(screen15);

            screen15.setDown(screen14);
            screen15.setLeft(screen16);
            screen15.setRight(screen17);
            screen15.setUp(screen21);

            screen16.setRight(screen15);
            screen16.setLeft(screen18);
            screen16.setUp(screen20);

            screen17.setLeft(screen15);
            screen17.setRight(screen19);
            screen17.setUp(screen22);

            screen18.setRight(screen16);

            screen19.setLeft(screen17);

            screen20.setDown(screen16);
            screen20.setUp(screen25);
            screen20.setLeft(screen23);

            screen21.setDown(screen15);
            screen21.setUp(screen26);

            screen22.setDown(screen17);
            screen22.setRight(screen24);
            screen22.setUp(bossRoom3);

            screen23.setRight(screen20);

            screen24.setLeft(screen22);

            screen25.setDown(screen20);

            screen26.setDown(screen21);

            bossRoom3.setDown(screen22);

            Hexagon hex1 = new Hexagon(6, 12, 6, screen15);
            Hexagon hex2 = new Hexagon(6, 12, 0, screen15);
            Hexagon hex3 = new Hexagon(6, 0, 6, screen15);
            Hexagon hex4 = new Hexagon(6, 0, 0, screen15);
            Hexagon hex5 = new Hexagon(6, 0, 0, screen16);
            Hexagon hex6 = new Hexagon(6, 0, 0, screen16);
            Hexagon hex7 = new Hexagon(6, 0, 0, screen17);
            Hexagon hex8 = new Hexagon(6, 0, 0, screen17);
            Hexagon hex9 = new Hexagon(6, 0, 0, screen18);
            Hexagon hex10 = new Hexagon(6, 0, 0, screen18);
            Hexagon hex11 = new Hexagon(6, 0, 0, screen18);
            Hexagon hex12 = new Hexagon(6, 0, 0, screen19);
            Hexagon hex13 = new Hexagon(6, 0, 0, screen19);
            Hexagon hex14 = new Hexagon(6, 0, 0, screen19);
            Hexagon hex15 = new Hexagon(6, 0, 0, screen20);
            Hexagon hex16 = new Hexagon(6, 0, 0, screen21);
            Hexagon hex17 = new Hexagon(6, 0, 0, screen21);
            Hexagon hex18 = new Hexagon(6, 0, 0, screen21);
            Hexagon hex19 = new Hexagon(6, 0, 0, screen22);
            Hexagon hex20 = new Hexagon(6, 0, 0, screen23);
            Hexagon hex21 = new Hexagon(6, 0, 0, screen23);
            Hexagon hex22 = new Hexagon(6, 0, 0, screen23);
            Hexagon hex23 = new Hexagon(6, 0, 0, screen24);
            Hexagon hex24 = new Hexagon(6, 0, 0, screen24);
            Octagon oct = new Octagon(6, 0, 0, screen25);
            Hexagon hex27 = new Hexagon(6, 0, 0, screen26);
            Hexagon hex28 = new Hexagon(6, 0, 0, screen26);
            Dodecahedron dodecaBoss = new Dodecahedron(11, 0, 0, bossRoom3);

            screen15.addEntity(hex1);
            screen15.addEntity(hex2);
            screen15.addEntity(hex3);
            screen15.addEntity(hex4);

            screen16.addEntity(hex5);
            screen16.addEntity(hex6);

            screen17.addEntity(hex7);
            screen17.addEntity(hex8);

            screen18.addEntity(hex9);
            screen18.addEntity(hex10);
            screen18.addEntity(hex11);

            screen19.addEntity(hex12);
            screen19.addEntity(hex13);
            screen19.addEntity(hex14);

            screen20.addEntity(hex15);

            screen21.addEntity(hex16);
            screen21.addEntity(hex17);
            screen21.addEntity(hex18);

            screen22.addEntity(hex19);

            screen23.addEntity(hex20);
            screen23.addEntity(hex21);
            screen23.addEntity(hex22);

            screen24.addEntity(hex23);
            screen24.addEntity(hex24);

            screen25.addEntity(oct);

            screen26.addEntity(hex27);
            screen26.addEntity(hex28);

            bossRoom3.addEntity(dodecaBoss);

            level3.addScreen(screen14);
            level3.addScreen(screen15);
            level3.addScreen(screen16);
            level3.addScreen(screen17);
            level3.addScreen(screen18);
            level3.addScreen(screen19);
            level3.addScreen(screen20);
            level3.addScreen(screen21);
            level3.addScreen(screen22);
            level3.addScreen(screen23);
            level3.addScreen(screen24);
            level3.addScreen(screen25);
            level3.addScreen(screen26);
            level3.addScreen(bossRoom3);

            world.campaign.add(level3);
            

            Cirkyle = new Player(100, 100);
            level1.placeEntity(0, 0, Cirkyle);
        }
    }

    

    public void updateView(){
        levelTimer--;
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
        if(getPlayer()!=null)
        {
            getPlayer().performMovement();
        }
    }



    public void setDifficulty(int difficulty) {
        world.difficulty = difficulty;
        System.out.println(difficulty);
    }
    public int getDifficulty() {
        return world.difficulty;
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
        //World.instance().isPaused=true;
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

    public ScreenObserver getObserver() {
        return observer;
    }
}
