package LvLBuilderStuff.LBModel;

public class Object {
    private String name;
    private Coordinates topLeftCoord;
    
    // Subject to change
    private boolean random; // Whether object should be randomly placed
    
    public Object(String name) {
        this.name = name;
        topLeftCoord = new Coordinates(0, 0);
    }
}
