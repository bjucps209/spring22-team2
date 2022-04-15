//-----------------------------------------------------------
//File:   Entity.java
//Desc:   An Object that can be placed freely on the map
//Note:   Does not actually inherit from Object.java
//----------------------------------------------------------- 
package Model;

public class Entity extends LvLObject {

    public Entity(String name, double height, double width, double x, double y) {
        super(name, height, width, x, y);
    }

    public String toFileFormat() {
        return "lol";
    }

    //Eventually will change newLoc depending on newLoc's location
    @Override
    public Vector moveLocation(Vector newLoc) {
        this.objectLoc = newLoc;
        return newLoc;
    }

    @Override
    public boolean isStackable() {
        return true;
    }
}
