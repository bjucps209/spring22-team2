import java.awt.event.KeyListener;
import java.util.*;

import javax.swing.JFrame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import model.*;


public class GameWindow {

    @FXML Pane gameWindow;
    ArrayList<Character> keysPressed = new ArrayList<Character>();

    @FXML
    void onGreetClicked(ActionEvent event) {
        var alert = new Alert(AlertType.INFORMATION, "Hello, world!");
        alert.setHeaderText(null);
        alert.show();

    }

    @FXML
    void updater(){
        KeyFrame frames = new KeyFrame(Duration.millis(20), this::updateView);
        Timeline timer = new Timeline(frames);
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    @FXML
    public void Initialize() {
        gameWindow.getChildren().clear();
        ArrayList<Entity> entities = World.instance().displayCurrentEntities();
        World.instance().getCurrentLevel().setObserver(this::Initialize);
        for (Entity entity: entities){
            ImageView entityImage = new ImageView(entity.getImage());
            entityImage.setX(entity.getX());
            entityImage.setY(entity.getY());
            entityImage.xProperty().bind(entity.getXProperty());
            entityImage.yProperty().bind(entity.getYProperty());
            entityImage.setUserData(entity);
            entityImage.prefWidth(200);
            entityImage.setPreserveRatio(true);
            gameWindow.getChildren().add(entityImage);
        }

        Screen currentScreen = World.instance().getCurrentLevel().getCurrentScreen();
        for (Obstacle obstacle: currentScreen.findObstacles()){
            ImageView obstacleImage = new ImageView(new Image("media/Player/Cirkyle v1.png"));
            obstacleImage.setX(obstacle.getX());
            obstacleImage.setY(obstacle.getY());
            obstacleImage.prefWidth(200);
            obstacleImage.setPreserveRatio(true);
            gameWindow.getChildren().add(obstacleImage);
        }
    }

    @FXML
    void updateView(ActionEvent event){
        try{for (Entity entity: World.instance().displayCurrentEntities()){
            entity.performMovement();
        }
        }catch(ConcurrentModificationException c){return;}
    }

    /**
     * saves the state of the game when the save button is clicked
     * @param event
     */
    @FXML
    void onSaveClicked(ActionEvent event) {

    }

    
}
