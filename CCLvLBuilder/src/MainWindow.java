import Model.*;
import Model.Screen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.ArrayList;

/* 
GameWindow gameWindow = loader.getController();
int gridSize = (int)sldGridSize.getValue();
gameWindow.initialize(gridSize); */


//<Pane fx:id="gameBuilder" style="-fx-background-color: lightgray" />
public class MainWindow implements LevelObserver {
    //Stuff for window dimensions
    protected Stage theStage; //don't ask
    protected Vector windowSize;
    protected static final double screensizescalar = 65;
    protected static double screenwidth = screensizescalar * 16; protected static double screenheight = screensizescalar * 9;

    //Panes
    @FXML ArrayList<Pane> thePanes;

    //@FXML VBox vbWindow;
    @FXML HBox screenBox;
    @FXML Pane currentScreen;
    @FXML Label lblCurScreenID;
    protected ScreenNavPanelState navPanelState = ScreenNavPanelState.MOVE;

    //Nav Panel
    @FXML Button btnCreate; @FXML Button btnDelete;
    @FXML Button btnNorth; @FXML Button btnSouth;
    @FXML Button btnEast; @FXML Button btnWest;
    @FXML Button btnUp; @FXML Button btnDown;

    @FXML
    void initialize() {
        DataManager.DaMan().setMrObserver(this);        
        thePanes = new ArrayList<Pane>();
        createScreen("0,0,0"); /// Try to manage this with DaMan ---------------- no
        
        disableNavButtons();
        btnDelete.setDisable(true);
    }

    @FXML
    void onCreateClicked(ActionEvent event) {
        switch (navPanelState) {
            case MOVE:
                navPanelState = ScreenNavPanelState.CREATE; //nav buttons now create screens
                btnCreate.setText("Cancel");
                break;
            case CREATE:
                navPanelState = ScreenNavPanelState.MOVE; //Create is cancelled
                btnCreate.setText("Create");
                break;
            case DELETE: //Deletion occurs
                navPanelState = ScreenNavPanelState.MOVE;
                DataManager.DaMan().deleteScreen();
                btnCreate.setText("Create");
                btnDelete.setText("Delete");
                
        }
        disableNavButtons();
    }

    @FXML
    void onDeleteClicked(ActionEvent event) {
        switch (navPanelState) {
            case MOVE, CREATE: //Enables deletion
                navPanelState = ScreenNavPanelState.DELETE;            
                btnCreate.setText("Confirm");
                btnDelete.setText("Cancel");
                break;
            case DELETE: //Cancels Deletion
                navPanelState = ScreenNavPanelState.MOVE;
                btnCreate.setText("Create");
                btnDelete.setText("Delete");
                break;
        }
    }

    @FXML
    void onNavButtonClicked(ActionEvent event) {
        Direction[] dir = new Direction[] {Direction.North, Direction.South,Direction.East,Direction.West, Direction.Up, Direction.Down};
        Button[] buttonnames = new Button[] {btnNorth, btnSouth, btnEast, btnWest, btnUp, btnDown};
        
        var sourcebtn = (Button)event.getSource(); //Creates button reference        
        for (int i = 0; i < buttonnames.length; i++ ) {
            if (sourcebtn == buttonnames[i]) { //Chooses what to do depending on button
                switch (navPanelState){ //Chooses action
                    case MOVE:
                        DataManager.DaMan().attemptMoveToScreen(dir[i]);
                        break;
                    case CREATE:
                        DataManager.DaMan().attemptCreateScreen(dir[i]);
                        break;
                    case DELETE:
                        System.out.println("MWonNavButtonCLicked, DELETE section accessed (not good)");
                }
            }
            } 
    }


    //Disables appropriate navigation buttons (btnNorth, btnEast, btnDelete...)
    public void disableNavButtons() {
        Screen[] surroundingScreens = DataManager.DaMan().getCurrentScreen().getAdjacentScreens();
        Button[] bleck = new Button[] {btnNorth, btnSouth, btnEast, btnWest, btnUp, btnDown};
        
        for (int dir = 0; dir < bleck.length; dir++) {
            if (surroundingScreens[dir] == null) {
                bleck[dir].setDisable(navPanelState == ScreenNavPanelState.MOVE);
            } else {
                bleck[dir].setDisable(navPanelState == ScreenNavPanelState.CREATE);
            }
        }
        btnDelete.setDisable(thePanes.size() == 1); //Disables delete button if only one screen
    }

    //Observer functions
    @Override
    public void createScreen(String StrID) {
        currentScreen = new Pane();
        thePanes.add(currentScreen);
        currentScreen.setMinSize(16 * screensizescalar, 9 * screensizescalar); /// Needs attention -----------------
        currentScreen.setMaxSize(16 * screensizescalar, 9 * screensizescalar);
        currentScreen.setStyle("-fx-background-color: lightgray");
        currentScreen.setUserData(StrID);
        screenBox.getChildren().clear();
        screenBox.getChildren().add(currentScreen);
        lblCurScreenID.setText(StrID);

        //Switch the buttons back to normal depending on nav panel state
        if (navPanelState == ScreenNavPanelState.CREATE) {
            onCreateClicked(null);
        }
    }

    @Override
    public void movetoScreen(String StrID) { 
        for (Pane aPane : thePanes) {
            System.out.println(aPane.getUserData());
            if (aPane.getUserData().equals(StrID)) {
                currentScreen = aPane;
                lblCurScreenID.setText(StrID);

               
            }
        }
        disableNavButtons();
    }

    public enum ScreenNavPanelState {
        MOVE, CREATE, DELETE
    }

    @Override
    public void deleteCurrentScreen(String oldStrID, String newStrID) {
        Pane delPane = null;
        for (Pane paaa : thePanes) {
            if (paaa.getUserData().equals(oldStrID)) {
                delPane = paaa;
             } /*else if (paaa.getUserData().equals(newStrID)) {
                currentScreen = paaa;
            } */
        }
        movetoScreen(newStrID);
        thePanes.remove(delPane);

    }

}
