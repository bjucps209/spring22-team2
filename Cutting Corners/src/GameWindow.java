import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;


public class GameWindow {

    @FXML
    void onGreetClicked(ActionEvent event) {
        var alert = new Alert(AlertType.INFORMATION, "Hello, world!");
        alert.setHeaderText(null);
        alert.show();

    }

    @FXML
    void updateVisual(){
        KeyFrame frames = new KeyFrame(Duration.millis(50), this::update);
        Timeline timer = new Timeline(frames);
        timer.setCycleCount(Timeline.INDEFINITE);
    }

    @FXML
    void update(ActionEvent event){
        
    }

    /**
     * saves the state of the game when the save button is clicked
     * @param event
     */
    @FXML
    void onSaveClicked(ActionEvent event) {

    }

    
}
