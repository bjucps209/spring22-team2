import java.util.ArrayList;

import Model.*;
import Model.Screen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/* 
GameWindow gameWindow = loader.getController();
int gridSize = (int)sldGridSize.getValue();
gameWindow.initialize(gridSize); */


//<Pane fx:id="gameBuilder" style="-fx-background-color: lightgray" />
public class MainWindow implements LevelObserver {
    //Stuff for window dimensions
    protected Stage theStage; //don't ask
    protected Vector windowSize;

    //Panes
    @FXML ArrayList<Pane> thePanes;

    //@FXML VBox vbWindow;
    @FXML HBox screenBox;
    @FXML Pane currentScreen;
    protected ScreenNavPanelState navPanelState = ScreenNavPanelState.MOVE;

    @FXML Button btnCreate; @FXML Button btnDelete;
    @FXML Button btnNorth; @FXML Button btnSouth;
    @FXML Button btnEast; @FXML Button btnWest;
    @FXML Button btnUp; @FXML Button btnDown;

    @FXML
    void initialize() {
        DataManager.DaMan().setMrObserver(this);
        createScreen("0,0,0"); /// Try to manage this with DaMan ----------------
        disableNavButtons(navPanelState);
    }

    @FXML
    void onCreateClicked(ActionEvent event) {
        switch (navPanelState) {
            case MOVE:
                navPanelState = ScreenNavPanelState.CREATE; //nav buttons now create
                btnCreate.setText("Cancel");
            case CREATE:
                navPanelState = ScreenNavPanelState.MOVE; //Create is cancelled
                btnCreate.setText("Create");
            case DELETE:
                navPanelState = ScreenNavPanelState.CREATE; //Delete is cancelled and create activated
                btnCreate.setText("Create");
                btnDelete.setText("Delete");
        }
        disableNavButtons(navPanelState);
    }

    @FXML
    void onDeleteClicked(ActionEvent event) {
        return;
    }

    @FXML
    void onNavButtonClicked(ActionEvent event) {
        return;
    }

    public void disableNavButtons(ScreenNavPanelState latestState) {
        Screen[] surroundingScreens = DataManager.DaMan().getCurrentScreen().getAdjacentScreens();
        Button[] bleck = new Button[] {btnNorth, btnSouth, btnEast, btnWest, btnUp, btnDown};
        switch (latestState) {
            case CREATE, DELETE:
                DNavBtnExtended1(true, surroundingScreens, bleck);
            case MOVE:
                DNavBtnExtended1(false, surroundingScreens, bleck);
        }
    }
    //Separated due to duplicate code
    private void DNavBtnExtended1(boolean see4urself, Screen[] surScreen, Button[] blecc) {
        for (int dir = 0; dir < blecc.length; dir++) {
            if (surScreen[dir] == null) {
                blecc[dir].setDisable(see4urself);
            } else {
                blecc[dir].setDisable(!see4urself);
            }
        }
    }

    //Observer functions
    @Override
    public void createScreen(String StrID) {
        currentScreen = new Pane();
        currentScreen.setPrefSize(500, 500); /// Needs attention -----------------
        currentScreen.setStyle("-fx-background-color: lightgray");
        currentScreen.setUserData(StrID);
        screenBox.getChildren().clear();
        screenBox.getChildren().add(currentScreen);
    }

    public enum ScreenNavPanelState {
        MOVE, CREATE, DELETE
    }

}
