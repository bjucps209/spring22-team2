//-----------------------------------------------------------
//File:   Entity.java
//Desc:   An Object that can be placed freely on the map
//Note:   Does not actually inherit from Object.java
//----------------------------------------------------------- 
package LvLBuilderStuff.LBModel;

public class Entity {
    private String name;
    private Coordinates topLeftCoord;

    public Entity(String name) {
        this.name = name;
        topLeftCoord = new Coordinates(0, 0);
    }

    public String toFileFormat() {
        return "lol";
    }
}
