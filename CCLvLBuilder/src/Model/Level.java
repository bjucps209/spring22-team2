//-----------------------------------------------------------
//File:   blahblah.java
//Desc:   Stores all the important stuff
//----------------------------------------------------------- 
package Model;

import java.util.ArrayList;

//TODO: move over Screens from DaMan
public class Level {
    private Screen screenWithPlayer;
    private int totalEnemies = 0;

    private ArrayList<Screen> Screens;

    public Level() {
        Screens = new ArrayList<Screen>();
    }


    public void addScreen(Screen newscreen) {
        Screens.add(newscreen);
    }

    public void setScreens(ArrayList<Screen> newscreens) {
        Screens = newscreens;
    }

    public ArrayList<Screen> getScreens() {
        return Screens;
    }
}
