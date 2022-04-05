import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/* 
GameWindow gameWindow = loader.getController();
int gridSize = (int)sldGridSize.getValue();
gameWindow.initialize(gridSize); */



public class MainWindow {
    //Stuff for window dimensions
    protected Stage theStage;
    protected Coordinates windowSize;

    @FXML VBox vbWindow;
    @FXML Pane gameBuilder; //Main Pane

    @FXML
    void initialize(Stage stage) {
        theStage = stage;
        gameBuilder.setMaxSize(500, 500);
        //theStage.setFullScreen(true);
    }

    @FXML
    void onGreetClicked(ActionEvent event) {

    }
}
