package model;

import java.util.ArrayList;

public class DummyScreen {
    String screenID;
    String backgroundImgPath;
    String[] adjacentStringIDs; //N S E W Up Down (Up Down Right Left [ERROR] [ERROR])

    ArrayList<DummyObject> dObjList;

    public DummyScreen(String StrID) {
        screenID = StrID;
        dObjList = new ArrayList<DummyObject>();
    }


    //G&S
    public void addDummyObject(DummyObject obj) {
        dObjList.add(obj);
    }

    public DummyObject getDumObj(int index) {
        return dObjList.get(index);
    }

    public ArrayList<DummyObject> getDumObjects() {
        return dObjList;
    }

    public String getBackgroundImgPath() {
        return backgroundImgPath;
    }

    public void setBackgroundImgPath(String backgroundImgPath) {
        this.backgroundImgPath = backgroundImgPath;
    }


    public String getAdjacentStringID(int idx) {
        return adjacentStringIDs[idx];
    }

    public void setAdjacentStringID(int idx, String adjacentStrID) {
        this.adjacentStringIDs[idx] = adjacentStrID;
    }

    

}
