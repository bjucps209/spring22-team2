//-----------------------------------------------------------
//File:   Entity.java
//Desc:   An Object that can be placed freely on the map
//Note:   Does not actually inherit from Object.java
//----------------------------------------------------------- 
package Model;

public class Entity extends LvLObject {

    public Entity(String name, int id, Vector dimensions, Vector topleftcell) {
        super(name, id, dimensions, topleftcell);
    }

    public String[] toFileFormat() {
        return new String[] {"lol"};
    }

    //Eventually will change newLoc depending on newLoc's location
    @Override
    public Vector moveLocation(Vector newLoc) {
        this.topLeftCell = newLoc;
        return newLoc;
    }

}
