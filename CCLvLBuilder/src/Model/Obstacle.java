//-----------------------------------------------------------
//File:   LvLObject.java.java
//Desc:   LvLObject that aligns to the grid
//----------------------------------------------------------- 
package Model;

public class Obstacle extends LvLObject {
    
    // Subject to change
    private boolean random; // Whether object should be randomly placed
    
    public Obstacle(String name, double height, double width, double x, double y) {
        super(name, height, width, x, y);
    }

    //Eventually will change newLoc depending on newLoc's location
    @Override
    public Vector moveLocation(Vector newLoc) {
        this.objectLoc = newLoc;
        return newLoc;
    }

    @Override
    public boolean isStackable() {
        return false;
    }

    
}
