package model;

import java.util.ArrayList;

public class DummyLevel {
    String playerScrStrID;
    ArrayList<DummyScreen> dScreenList;

    public DummyLevel() {
        dScreenList = new ArrayList<DummyScreen>();
    }

    public void addDScreen(DummyScreen scr) {
        dScreenList.add(scr);
    }

    public ArrayList<DummyScreen> getDumScreens() {
        return dScreenList;
    }

    //G&S
    public String getPlayerScrStrID() {
        return playerScrStrID;
    }
    public void setPlayerScrStrID(String playerScrStrID) {
        this.playerScrStrID = playerScrStrID;
    }

}
