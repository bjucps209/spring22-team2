//-----------------------------------------------------------
//File:   SomeObserver.java
//Desc:   Name pending
//        Observes things
//----------------------------------------------------------- 
package Model;

import java.util.ArrayList;

public interface LevelObserver {
    public void createScreen(String StrID);
    public void movetoScreen(String StrID);
    public void deleteCurrentScreen(String oldStrID, String newStrID);
    public void addLvLObject(LvLObject theObject);
    public void moveLvLObject(LvLObject theObject);
    public void deleteLvLObject(int id);
    public void updateActionStatement(String statementMsg);

    public void populateScreens(ArrayList<Screen> screens);
}
