import java.util.ArrayList;

import Model.Vector;
import Model.DataManager;
import Model.LevelObserver;
import javafx.fxml.FXML;
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

    @FXML
    void initialize() {
        DataManager.DaMan().setMrObserver(this);
        createScreen("0,0,0"); /// Try to manage this with DaMan ----------------
    }

    @FXML
    void onCreateClicked() {
        switch (navPanelState) {
            case MOVE:
                navPanelState = ScreenNavPanelState.CREATE; //TODO: left off here

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
        MOVE, CREATE
    }

}
