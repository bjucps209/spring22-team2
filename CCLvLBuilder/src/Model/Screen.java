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
    private Screen[] adjacentScreens; // N S E W Up Down
    private String backgroundPathName;
   
    private ObjType[][] objectLocations; // y then x
    private ArrayList<LvLObject> objectList;

    private static int currentObjID; 
    
    public Screen(int x, int y, int z, Vector gridDim) {
        IDSeries = new int[] {x, y, z};
        adjacentScreens = new Screen[6];
        objectList = new ArrayList<LvLObject>();
        objectLocations = new ObjType[gridDim.getY()][gridDim.getX()];
        backgroundPathName = "";
    }

 /*    public LvLObject attemptCreateObject(String name, ) {

    } */

    public LvLObject createObject(String name, String imgPath, ObjType objtype, Vector topleftcell, Vector dimensions) {
        currentObjID++; //increment object id of screen
        LvLObject newobject = new LvLObject(null, null, 0, null, null, null);
        switch (objtype) { //defines newobject based on type
            case Player:
                newobject = new LvLObject(name, imgPath, currentObjID, ObjType.Player, dimensions, topleftcell);
                DataManager.DaMan().setPlayerScrID(this.getStrID());
                break;
            default:
                newobject = new LvLObject(name, imgPath, currentObjID, objtype, dimensions, topleftcell);
                break;
        }
        objectList.add(newobject);
        populateArea(newobject);
        return newobject;
    }

    public void moveObject(LvLObject theObj, Vector newGrid) {
        purgeArea(theObj);
        theObj.setTopLeftCell(newGrid);
        populateArea(theObj);
    }

    public void deleteObject(int id) {
        LvLObject curobj = findObject(id);
        purgeArea(curobj);
        objectList.remove(curobj);
    }

    //Checks if area in objectlocations is empty
    public boolean areaIsEmpty(Vector topLeftCell, Vector dimensions) {
        for (int yindex = topLeftCell.getY(); yindex < topLeftCell.getY() + dimensions.getY(); yindex++) {
            for (int xindex = topLeftCell.getX(); xindex < topLeftCell.getX() + dimensions.getX(); xindex++) {
                if (objectLocations[yindex][xindex] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    //fills area with objType references (can be null)
    public void fillArea(LvLObject an_object, ObjType theType) {
        Vector tlc = an_object.getTopLeftCell();
        Vector dim = an_object.getDimensions();
        for (int yy = tlc.getY(); yy < tlc.getY() + dim.getY(); yy++) {
            for (int xx = tlc.getX(); xx < tlc.getX() + dim.getX(); xx++) {
                objectLocations[yy][xx] = theType;
            }
        }
    }
    //purges area of an_object
    public void purgeArea(LvLObject an_object) {
        fillArea(an_object, null);
    }
    //fills area with an_object
    public void populateArea(LvLObject an_object) { 
        fillArea(an_object, an_object.getObjType());
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


    //Normal getters and setters
    public int[] getIDSeries() {
        return IDSeries;
    }


    public String getBackgroundPathName() {
        return backgroundPathName;
    }

    public void setBackgroundPathName(String backgroundPathName) {
        this.backgroundPathName = backgroundPathName;
    }

    /// Adjacent Screen manipulation
    //
    public Screen[] getAdjacentScreens(){
        return adjacentScreens;
    }

    public Screen getAdjacentScreen(Direction direction) {
        Direction[] stuff = Direction.values();
        for (int i = 0; i < stuff.length; i++) {
            if (stuff[i] == direction) {
                return adjacentScreens[i];
            }
        }
        return null;
    }

    public void setAdjacentScreen(Direction direction, Screen adjScreen) {
        Direction[] dirs = Direction.values();
        for (int i = 0; i < dirs.length; i++) {
            if(dirs[i] == direction) {
                adjacentScreens[i] = adjScreen;                
            }
        }
    }

    public void removeAdjacentScreen(Screen screen) {
        for (int scr = 0; scr < adjacentScreens.length; scr++) {
            if (adjacentScreens[scr] == screen) {
                adjacentScreens[scr] = null;
            }
        }
    }

    /// Object Stuff
    //
    public ArrayList<LvLObject> getObjects() {
        return objectList;
    }

    public LvLObject findObject(int id) {
        for (LvLObject obj : objectList) {
            if (obj.getId() == id) {
                return obj;
            }
        }
        return null;
    }
}
