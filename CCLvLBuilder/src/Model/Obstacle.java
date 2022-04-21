//-----------------------------------------------------------
//File:   LvLObject.java.java
//Desc:   LvLObject that aligns to the grid
//----------------------------------------------------------- 
package Model;

public class Obstacle extends LvLObject {
    


    public Obstacle(String name, int id, Vector dimensions, Vector topleftcell) {
        super(name, id, dimensions, topleftcell);
    }

    //Eventually will change newLoc depending on newLoc's location
    @Override
    public Vector moveLocation(Vector newLoc) {
        this.topLeftCell = newLoc;
        return newLoc;
    }
    
}
