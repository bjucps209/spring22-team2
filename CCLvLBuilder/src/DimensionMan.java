import Model.DataManager;
import Model.Vector;

//-----------------------------------------------------------
//File:   DimensionMan.java
//Desc:   Manages the Pane size and manages switching
//        between Coordinates and Cells
//----------------------------------------------------------- 

public class DimensionMan {
    //Screen Dimensions
    protected static final int widthConstant = 16;
    protected static final int heightConstant = 9;
    protected static final double screensizescalar = 65;
    protected static double screenwidth = screensizescalar * widthConstant; 
    protected static double screenheight = screensizescalar * heightConstant;
    
    //Screen Dimensions applied to cell grid
    protected static Vector gridDimensionRef;
    protected static double gridtoPaneRatio;
    protected SideWithMargin sideWithMargin; // Which side does the grid not fully fit

    private double cellLength;
    private double sideMargin;

    private enum SideWithMargin {
        WIDTH, HEIGHT
    }

    public Vector coordstoGrid(double y, double x) {
        int gridY = 0; int gridX = 0;
        int eh = 0;
        switch (sideWithMargin) {
            case WIDTH:
                gridX = (int)(Math.floor((x - sideMargin) / cellLength) + eh);
                gridY = (int)(Math.floor((y / cellLength)) + eh);
                break;
            case HEIGHT:
                gridY = (int)Math.floor(((y - sideMargin) / cellLength) + eh);
                gridX = (int)Math.floor((x / cellLength) + eh);
                break;
        }
        System.out.println( "coords to grid " + gridX + " " + gridY);
        return new Vector(gridY, gridX);
    }

    public double[] gridtoCoords(Vector topleftcorner) {
        double coordY = 0; double coordX = 0;
        switch (sideWithMargin) {
            case WIDTH:
                coordX = ((topleftcorner.getX()) * cellLength) + sideMargin;
                coordY = cellLength * topleftcorner.getY();
                break;
            case HEIGHT:
                coordY =  ((topleftcorner.getY()) * cellLength) + sideMargin;
                coordX = cellLength * topleftcorner.getX();
                break;
        }
        Vector test = coordstoGrid(coordY, coordX);
        System.out.println( "grid to coords "+ test.getX() + " " + test.getY());
        return new double[] {coordY, coordX};
    }

    //Singleton
    private DimensionMan() {
        gridDimensionRef = DataManager.DaMan().gridDimensions;
        gridtoPaneRatio = gridDimensionRef.getY() * widthConstant / gridDimensionRef.getX() / heightConstant;

        if (gridtoPaneRatio > 1) {
            sideWithMargin = SideWithMargin.WIDTH; 
        } else {
            sideWithMargin = SideWithMargin.HEIGHT;
        }

        cellLength = (sideWithMargin == SideWithMargin.HEIGHT) ? (screenwidth / gridDimensionRef.getX()) : (screenheight / gridDimensionRef.getY());
        sideMargin = (sideWithMargin == SideWithMargin.HEIGHT) ? (screenheight % cellLength) / 2 : (screenwidth % cellLength) / 2;
    }
    private static DimensionMan dManager;
    public static DimensionMan DiMan() {
        if (dManager == null) { dManager = new DimensionMan(); }
        return dManager;
    }

    //G&S
    public double getCellLength() {
        return cellLength;
    }
}
