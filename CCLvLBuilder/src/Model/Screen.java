//-----------------------------------------------------------
//File:   Screen.java
//Desc:   Represents a single area of the level.
//        I had no idea what else to call it.
//        Needs rework
//----------------------------------------------------------- 
package Model;

import java.util.ArrayList;

public class Screen {
    private int[] IDSeries;
    private Screen[] adjacentIDs; // N S E W Up Down
    
    private ArrayList<LvLObject> objectList;
    private static int currentObjID;

    public Screen(int x, int y, int z) {
        IDSeries = new int[] {x, y, z};
        adjacentIDs = new Screen[6];
    }

    //public void addObject(LvLObject blah) (should give object id here)

    public String getStrID() {
        return String.valueOf(IDSeries[0]) + "," + String.valueOf(IDSeries[1]) + "," + String.valueOf(IDSeries[2]);
    }

    public static String ConvertToStrID(int x, int y , int z) {
        return String.valueOf(x) + "," + String.valueOf(y) + "," + String.valueOf(z);
    }

    public static String ConvertToStrID(int[] id) {
        return String.valueOf(id[0]) + "," + String.valueOf(id[1]) + "," + String.valueOf(id[2]);
    }


    //Normal getters and setters
    public int[] getIDSeries() {
        return IDSeries;
    }

    public void setAdjacentLocArea(int side, int adjacentid) {
        return;
    }

    public void setAdjacentID(int direction, Screen adjScreen) {
        adjacentIDs[direction] = adjScreen;
    }

    public ArrayList<LvLObject> getObjects() {
        return objectList;
    }
}
