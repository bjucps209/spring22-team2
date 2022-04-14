package Model;

public class Item extends LvLObject {

    
    public Item(String name, double height, double width, double x, double y) {
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
        return true;
    }

}
