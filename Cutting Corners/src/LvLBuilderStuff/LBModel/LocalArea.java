//-----------------------------------------------------------
//File:   Screen.java
//Desc:   Represents a single area of the level.
//        I had no idea what else to call it.
//        Needs rework
//----------------------------------------------------------- 
package LvLBuilderStuff.LBModel;

public class LocalArea {
    private int[] IDSeries;
    private String[] adjacentIDs; // N S E W Up Down
    
    public LocalArea(int x, int y, int z) {
        IDSeries = new int[] {x, y, z};
        adjacentIDs = new String[6];
    }

    public String getStrID() {
        return String.valueOf(IDSeries[0]) + "," + String.valueOf(IDSeries[1]) + "," + String.valueOf(IDSeries[2]);
    }

    public static String ConvertToStrID(int x, int y , int z) {
        return String.valueOf(x) + "," + String.valueOf(y) + "," + String.valueOf(z);
    }

    public static String ConvertToStrID(int[] id) {
        return String.valueOf(id[0]) + "," + String.valueOf(id[1]) + "," + String.valueOf(id[2]);
    }

    public int[] getIDSeries() {
        return IDSeries;
    }

    public void setAdjacentLocArea(int side, int adjacentid) {
        return;
    }

    public void setAdjacentID(int direction, String id) {
        adjacentIDs[direction] = id;
    }
}
