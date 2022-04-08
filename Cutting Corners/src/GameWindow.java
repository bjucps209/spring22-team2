import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import model.*;


public class GameWindow {

    @FXML Pane GameWindow;

    @FXML
    void onGreetClicked(ActionEvent event) {
        var alert = new Alert(AlertType.INFORMATION, "Hello, world!");
        alert.setHeaderText(null);
        alert.show();

    }

    // @FXML
    // void updateVisual(){
    //     KeyFrame frames = new KeyFrame(Duration.millis(50), this::update);
    //     Timeline timer = new Timeline(frames);
    //     timer.setCycleCount(Timeline.INDEFINITE);
    // }

    @FXML
    public void Initialize() {
        System.out.println('x');
        Player player = new Player(100, 100);
        World.instance().getCurrentLevel().placeEntity(0, 0, player);
        ArrayList<Entity> entities = World.instance().displayCurrentEntities();
        System.out.println(entities.size());
        for (Entity entity: entities){
            ImageView entityImage = new ImageView(entity.getImage());
            entityImage.setX(entity.getX());
            entityImage.setY(entity.getY());
            entityImage.prefWidth(200);
            entityImage.setPreserveRatio(true);
            GameWindow.getChildren().add(entityImage);
        }
    }
}
