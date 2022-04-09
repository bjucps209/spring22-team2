//-----------------------------------------------------------
//File:   wefwefa.java
//Desc:   Represents a single area of the level.
//        I had no idea what else to call it.
//        Needs rework
//----------------------------------------------------------- 
package LvLBuilderStuff.LBModel;

public class LocalArea {
    private int ID;
    private int[] adjacentIDs; // N S E W (Not easily compatible with the main game)
    
    private static int currentID;
    
    public LocalArea() {
        ID = ++currentID;
        adjacentIDs = new int[] {0, 0, 0, 0}; //0 is essentially null
    }

    public int getID() {
        return ID;
    }

    public void setAdjacentLocArea(int side, int adjacentid) {
        return;
    }
}
