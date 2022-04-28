import Model.DataManager;
import Model.Vector;

//-----------------------------------------------------------
//File:   DimensionMan.java
//Desc:   Manages the Pane size and manages switching
//        between Coordinates and Cells
//----------------------------------------------------------- 

public class DimensionMan {
    //Screen Dimensions
    protected static final int widthConstant = 16; //For the pane ratio
    protected static final int heightConstant = 9; //
    protected static final double screensizescalar = 60; //
    protected static double screenwidth = screensizescalar * widthConstant; //
    protected static double screenheight = screensizescalar * heightConstant; //
    
    //Screen Dimensions applied to cell grid
    protected static Vector gridDimensionRef;
    protected static double gridtoPaneRatio;
    protected SideWithMargin sideWithMargin; // Which side does the grid not fully fit

    private double cellLength; //Length of one cell
    private double sideMargin; //Size of the side margin

    private enum SideWithMargin {
        WIDTH, HEIGHT
    }

    /**
     * Self explanatory
     */
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
        return new Vector(gridY, gridX);
    }

    /**
     * Self Explanatory
     */
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
        return new double[] {coordY, coordX};
    }

    public Vector objCenterCoordstoGrid(double y, double x) {
         return coordstoGrid((cellLength / 2) + y, (cellLength / 2) + x);
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
