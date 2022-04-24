//-----------------------------------------------------------
//File:   blahblah.java
//Desc:   Stores all the important stuff
//----------------------------------------------------------- 
package Model;

import java.util.ArrayList;

//TODO: move over Screens from DaMan
public class Level {
    private String screenWithPlayer;

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

    public Screen findScreen(String StrID) {
        for (Screen scr : Screens) {
            if (scr.getStrID().equals(StrID)) return scr;
        }
        return null;
    }

    public ArrayList<Screen> getScreens() {
        return Screens;
    }


    public String getScreenWithPlayer() {
        return screenWithPlayer;
    }


    public void setScreenWithPlayer(String screenWithPlayer) {
        this.screenWithPlayer = screenWithPlayer;
    }

    
}
