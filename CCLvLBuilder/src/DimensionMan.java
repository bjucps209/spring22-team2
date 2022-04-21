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
    protected static Vector gridDimensionRef = DataManager.DaMan().gridDimensions;
    protected static double panetoGridRatio = (widthConstant / heightConstant) / (gridDimensionRef.getX()/ gridDimensionRef.getY());
    protected SideWithMargin sideWithMargin;

    private double cellLength;
    private double sideMargin;

    private enum SideWithMargin {
        WIDTH, HEIGHT
    }

    public Vector coordstoGrid(double y, double x) {
        int gridY = 0; int gridX = 0;
        switch (sideWithMargin) {
            case WIDTH:
                gridX = (int)((x - sideMargin) / cellLength) + 1;
                gridY = (int)(y / cellLength) + 1;
                break;
            case HEIGHT:
                gridY = (int)((y - sideMargin) / cellLength) + 1;
                gridX = (int)(x / cellLength) + 1;
                break;
        }
        return new Vector(gridY, gridX);
    }

    public double[] gridtoCoords(Vector topleftcorner) {
        double coordY = 0; double coordX = 0;
        switch (sideWithMargin) {
            case WIDTH:
                coordX = sideMargin + cellLength * topleftcorner.getX();
                coordY = cellLength * topleftcorner.getY();
                break;
            case HEIGHT:
                coordY = sideMargin + cellLength * topleftcorner.getY();
                coordX = cellLength * topleftcorner.getX();
                break;
        }
        return new double[] {coordY, coordX};
    }

    //Singleton
    private DimensionMan() {
        if (panetoGridRatio > 1) {
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
