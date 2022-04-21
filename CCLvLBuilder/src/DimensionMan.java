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
    public DimensionMan getDimensionMan() {
        dManager = new DimensionMan();
        return dManager;
    }


    

    public double getCellLength() {
        return cellLength;
    }
}
