package Model;

public class Item extends LvLObject {

    
    public Item(String name, int id, Vector dimensions, Vector topleftcell) {
        super(name, id,dimensions, topleftcell);
    }

    //Eventually will change newLoc depending on newLoc's location
    @Override
    public Vector moveLocation(Vector newLoc) {
        this.topLeftCell = newLoc;
        return newLoc;
    }


}
