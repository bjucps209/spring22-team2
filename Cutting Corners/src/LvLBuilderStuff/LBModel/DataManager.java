//-----------------------------------------------------------
//File:   .java
//Desc:   The Brain (other option for temporary
//        data storage)
//----------------------------------------------------------- 
package LvLBuilderStuff.LBModel;

public class DataManager {
    private SomeObserver aCertainThingofSorts;
    private DataManager() {}
    private DataManager theThing = new DataManager();

    public DataManager gettheThing() {
        return theThing;
    }

    public void resetTheThing() { //Subject to change
        theThing = new DataManager();
    }

    public void save() { return; } //For show
    public void load() { return; }


    public void setThatThing(SomeObserver yesThisisAThing) {
        aCertainThingofSorts = yesThisisAThing;
    }
}
