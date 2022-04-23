//-----------------------------------------------------------
//File:   .java
//Desc:   The Brain (other option for temporary
//        data storage vs DataCache)
//----------------------------------------------------------- 
package Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class DataManager {
    LevelObserver mrObserver; //LevelObserver
    private Level theLevel;
    public static final Vector gridDimensions = new Vector(7, 13); 

    private Screen currentScreen;
    //private ArrayList<Screen> Screens;

    ///Save and Load
    //

    public void load(String fileName) throws FileNotFoundException, IOException { 
        String bleh = CCLvLFileReader.load(theLevel, fileName);
        
    }

    public String save(String fileName, boolean currentWork) throws FileNotFoundException, IOException {
         return CCLvLFileReader.save(theLevel, fileName, false);
    } 

    ///Object stuff
    //
    //Checks if object is within bounds, sends error if not
    //Makes currentScreen do the rest (Besides observers)
    public void createObject(String name, String imgPath,ObjType objtype, Vector topLeftCell, Vector dimensions) {
        if (topLeftCell.getY() < 0 || topLeftCell.getY() + dimensions.getY() - 1 >= gridDimensions.getY()) {
            if(mrObserver != null) { mrObserver.updateActionStatement("Out of Bounds");}
            return;
        } if (topLeftCell.getX() < 0 || topLeftCell.getX() + dimensions.getX() - 1 >= gridDimensions.getX()) {
            if(mrObserver != null) {  mrObserver.updateActionStatement("Out of Bounds"); }
            return;
        }
        if (!currentScreen.areaIsEmpty(topLeftCell, dimensions)) {
            if(mrObserver != null) { mrObserver.updateActionStatement("Selected area isn't empty"); }
            return;
        }

        LvLObject newObject = currentScreen.createObject(name, imgPath, objtype, topLeftCell, dimensions);
        if (mrObserver != null) {
             mrObserver.addLvLObject(newObject); 
             mrObserver.updateActionStatement(name + " placed at " + newObject.getTopLeftCell().getX() + "," + newObject.getTopLeftCell().getY() + " on " + currentScreen.getStrID());
        }
    }

    public void moveObject(int id, Vector newGrid) {
        LvLObject curObject = currentScreen.findObject(id);
        if (outOfBounds(newGrid, curObject.getDimensions())) {
            if(mrObserver != null) { 
                mrObserver.updateActionStatement("Out of Bounds");
                mrObserver.moveLvLObject(curObject);
            }
            return;        
        }
        if (!currentScreen.areaIsEmpty(newGrid, curObject.getDimensions())) {
            if(mrObserver != null) { 
                mrObserver.moveLvLObject(curObject); //Moves it back
                mrObserver.updateActionStatement("Area Occupied");
            }
            return;
        }
        currentScreen.moveObject(curObject, newGrid);
        if (mrObserver != null) {
            mrObserver.moveLvLObject(curObject);
            mrObserver.updateActionStatement(curObject.getName() + " moved to " + curObject.getTopLeftCell().getX() + "," + curObject.getTopLeftCell().getY() + " on " + currentScreen.getStrID());
        }
    }

    //User shouldn't be able to cause an error in here
    public void deleteObject(int id) {
        currentScreen.deleteObject(id);
        if (mrObserver != null) {
            mrObserver.deleteLvLObject(id);
            mrObserver.updateActionStatement( currentScreen.findObject(id).getName() + " deleted on " + currentScreen.getStrID());
        }
    }

    private boolean outOfBounds(Vector gridloc, Vector dimensions) {
        if (gridloc.getY() < 0 || gridloc.getY() + dimensions.getY() - 1 >= gridDimensions.getY()) {
            if(mrObserver != null) { mrObserver.updateActionStatement("Out of Bounds");}
            return true;
        } if (gridloc.getX() < 0 || gridloc.getX() + dimensions.getX() - 1 >= gridDimensions.getX()) {
            if(mrObserver != null) {  mrObserver.updateActionStatement("Out of Bounds"); }
            return true;
        }
        return false;
    }

    /// Screen Methods ///
    ///
    //Returns new id number in the specified direction from currentScreen
    public int[] getAdjacentID(Direction direction, int[] screenID) {
        int nx = screenID[0]; int ny = screenID[1]; int nz = screenID[2];    
        switch(direction) {
            case North:
                ny++;
                break;
            case South:
                ny--;
                break;
            case East:
                nx++;
                break;
            case West:
                nx--;
                break;
            case Up:
                nz++;
                break;
            case Down:
                nz--;
                break;
        }
        return new int[] {nx, ny, nz};
    }

    //Attempts to create a screen from the current screen depending on the given direction
    public void attemptCreateScreen(Direction direction) {
        int[] tempID = getAdjacentID(direction, currentScreen.getIDSeries());

        //Check if Screen already exists (Won't need later) -----------------------------------------------------
        String moveID = Screen.ConvertToStrID(tempID[0], tempID[1], tempID[2]);
        for (Screen aScreen : theLevel.getScreens()) {
            if (moveID.equals(aScreen.getStrID())) {
                return;
            }
        }

        //Fill adjacent ID's if adjacent screens exist
        Screen newScreen = new Screen(tempID[0], tempID[1], tempID[2], gridDimensions);
        Direction[] directions = Direction.values();
        Direction[] opposingDir = new Direction[] {Direction.South, Direction.North, Direction.West, Direction.East, Direction.Down, Direction.Up};
        for (int dir = 0; dir < directions.length; dir++) { //Goes through all the directions
            var idCan =  Screen.ConvertToStrID(getAdjacentID(directions[dir], tempID)); //Possible screenID
            var screenCan = theLevel.getScreens().stream().filter((eh) -> eh.getStrID().equals(idCan)).findFirst(); //Finds screen with ID
            if (screenCan.isPresent()){ //Checks if Optional exists
                newScreen.setAdjacentScreen(directions[dir], screenCan.get()); // Sets as adjacentScreen in direction
                screenCan.get().setAdjacentScreen(opposingDir[dir], newScreen); // Sets found screen's adjacent screen
            }
        }
        theLevel.getScreens().add(newScreen);
        currentScreen = newScreen;  

        if (mrObserver != null) { mrObserver.createScreen(moveID); }
    }

    public void attemptMoveToScreen(Direction direction) {
        Screen nextScreen = currentScreen.getAdjacentScreen(direction);
        if (nextScreen != null) {
            currentScreen = nextScreen;
            if (mrObserver != null) { mrObserver.movetoScreen(currentScreen.getStrID()); }
            return;
        }

    }
    
    //Deletes the current screen and moves to an adjacent one
    public void deleteScreen() {
        var adjscrs = currentScreen.getAdjacentScreens();
        String oldScrStrID = currentScreen.getStrID();
        Screen newScreen = null;
        theLevel.getScreens().remove(currentScreen); //Removes screen from scrList

        for (int i = 0; i < adjscrs.length; i++) {
            if (adjscrs[i] != null) {
                adjscrs[i].removeAdjacentScreen(currentScreen);
                if (newScreen == null){
                    newScreen = adjscrs[i];
                }
            }
        }
        if (newScreen != null) {
            currentScreen = newScreen;
        } else {
            currentScreen = theLevel.getScreens().get(0);
        }
        if (mrObserver != null) { mrObserver.deleteCurrentScreen(oldScrStrID, currentScreen.getStrID());}
    }

    //Interesting Stuff
    //

    public void setMrObserver(LevelObserver anObserver){
        mrObserver = anObserver;
    }


    // Singleton model
    private DataManager() {
        theLevel = new Level();
        //Screens = new ArrayList<Screen>();
        currentScreen = new Screen(0,0,0, gridDimensions);
        theLevel.addScreen(currentScreen);
    }
    private static DataManager theThing = new DataManager();
    public static DataManager DaMan() { return theThing; } //abbr.

    public void resetDaMan() { //Subject to change
        theThing = new DataManager();
    }

    //Normal getters and setters
    public ArrayList<Screen> getScreens() {
        return theLevel.getScreens();
    }

/*     public void setScreens(ArrayList<Screen> newScreens) {
        Screens = newScreens;
    } */

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(Screen newScreen) {
        currentScreen = newScreen;
    }

    public void setPlayerScrID(String StrID) {
        theLevel.setScreenWithPlayer(StrID);
    }

    public String getPlayerScrID() {
        return theLevel.getScreenWithPlayer();
    }

    public Level getTheLevel() {
        return theLevel;
    }
    
    public void setTheLevel(Level theLevel) {
        this.theLevel = theLevel;
    }



    
    /// Save/Load methods
    //

}
