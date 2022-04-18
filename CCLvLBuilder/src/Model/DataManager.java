//-----------------------------------------------------------
//File:   .java
//Desc:   The Brain (other option for temporary
//        data storage vs DataCache)
//----------------------------------------------------------- 
package Model;

import java.util.ArrayList;

public class DataManager {
    LevelObserver mrObserver; //LevelObserver

    private Screen currentScreen;
    private ArrayList<Screen> Screens;

    public void save(String pathway, String fileName) { return; } //For show
    public void load(String pathway, String fileName) { return; } //Or is it? :O
                                   //Nope

    //TODO:
    //public void placeObject(LvLObject blah) {} (LvLObject comes from a clone of prefabricated objects)
    //




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
    //TODO: not done
    public void attemptCreateScreen(Direction direction) {
        int[] tempID = getAdjacentID(direction, currentScreen.getIDSeries());
        System.out.println(Screen.ConvertToStrID(tempID));//________________

        //Check if Screen already exists (Won't need later) -----------------------------------------------------
        String moveID = Screen.ConvertToStrID(tempID[0], tempID[1], tempID[2]);
        for (Screen aScreen : Screens) {
            if (moveID.equals(aScreen.getStrID())) {
                return;
            }
        }

        //Fill adjacent ID's if adjacent screens exist
        Screen newScreen = new Screen(tempID[0], tempID[1], tempID[2]);
        Direction[] directions = Direction.values();
        Direction[] opposingDir = new Direction[] {Direction.South, Direction.North, Direction.West, Direction.East, Direction.Down, Direction.Up};
        for (int dir = 0; dir < directions.length; dir++) {
            var idCan =  Screen.ConvertToStrID(getAdjacentID(directions[dir], tempID));
            var screenCan = Screens.stream().filter((eh) -> eh.getStrID().equals(idCan)).findFirst();
            if (screenCan.isPresent()){
                newScreen.setAdjacentScreen(directions[dir], screenCan.get());
                screenCan.get().setAdjacentScreen(opposingDir[dir], newScreen);
            }
        }
        Screens.add(newScreen);
        currentScreen = newScreen;  

        if (mrObserver != null) { mrObserver.createScreen(moveID); }
    }

    //TODO: idk I forgot
    public void attemptMoveToScreen(Direction direction) {
        Screen nextScreen = currentScreen.getAdjacentScreen(direction);
        if (nextScreen != null) {
            currentScreen = nextScreen;
            mrObserver.movetoScreen(currentScreen.getStrID());
            return;
        }

    }

    public void deleteScreen() {
        var adjscrs = currentScreen.getAdjacentScreens();
        String oldScrStrID = currentScreen.getStrID();
        Screen newScreen = null;
        Screens.remove(currentScreen); //Removes screen from scrList

        for (int i = 0; i < adjscrs.length; i++) {
            if (adjscrs[i] != null) {
                adjscrs[i].removeAdjacentScreen(currentScreen);
                if (newScreen == null){
                    newScreen = adjscrs[i];
                }
            }
        }
        currentScreen = newScreen;
        if (mrObserver != null) { mrObserver.deleteCurrentScreen(oldScrStrID, currentScreen.getStrID());}
    }

    //Interesting Stuff
    //

    public void setMrObserver(LevelObserver anObserver){
        mrObserver = anObserver;
    }


    // Singleton model
    private DataManager() {
        Screens = new ArrayList<Screen>();
        currentScreen = new Screen(0,0,0);
        Screens.add(currentScreen);
    }
    private static DataManager theThing = new DataManager();
    public static DataManager DaMan() { return theThing; } //abbr.

    public void resetDaMan() { //Subject to change
        theThing = new DataManager();
    }

    //Normal getters and setters
    public ArrayList<Screen> getScreens() {
        return Screens;
    }

    public void setScreens(ArrayList<Screen> newScreens) {
        Screens = newScreens;
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(Screen newScreen) {
        currentScreen = newScreen;
    }
}
